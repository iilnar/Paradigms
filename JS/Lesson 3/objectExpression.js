"use strict";
var variables = {
    "x": 0,
    "y": 1,
    "z": 2
};

var unaryOperations = {
    "negate": function(value) {return -value;},
    "sin": Math.sin,
    "cos": Math.cos,
    "exp": Math.exp,
    "atan": Math.atan
};

var binaryOperations = {
    "+": function(left, right) {return left + right;},
    "-": function(left, right) {return left - right;},
    "*": function(left, right) {return left * right;},
    "/": function(left, right) {return left / right;}
};

function Const(value) {
    this.value = value;
    Object.freeze(this);
}

Const.prototype.evaluate = function() {
    return this.value;
};

Const.prototype.toString = function() {
    return String(this.value);
};

Const.prototype.diff = function(variable) {
    return new Const(0);
};

Const.prototype.simplify = function() {
    return this;
};

Const.prototype.prefix = function() {
    return String(this.value);
};

function Variable(name) {
    this.name = name;
    Object.freeze(this);
}

Variable.prototype.evaluate = function() {
    return arguments[variables[this.name]];
};

Variable.prototype.toString = function() {
    return this.name;
};

Variable.prototype.diff = function(variable) {
    return new Const((variable == this.name) ? 1 : 0);
};

Variable.prototype.simplify = function() {
    return new Variable(this.name);
};

Variable.prototype.prefix = function() {
    return this.name;
};

function UnaryOperation(operation, child) {
    this.operation = operation;
    this.child = child;
}

UnaryOperation.prototype.evaluate = function() {
    return unaryOperations[this.operation](this.child.evaluate.apply(this.child, arguments));
};

UnaryOperation.prototype.toString = function() {
    return this.child.toString() + " " + this.operation;
};

UnaryOperation.prototype.simplify = function() {
    return this.extraSimplify(this.child.simplify());
};

UnaryOperation.prototype.prefix = function() {
    return "(" + this.operation +  " " + this.child.prefix() + ")";
};

function unaryBuilder(name, diff, simplify) {
    var a = function(child) {
        UnaryOperation.call(this, name, child);
    };
    a.prototype = Object.create(UnaryOperation.prototype);
    a.prototype.diff = diff;
    a.prototype.extraSimplify = simplify;
    return a;
}

function BinaryOperation(operation, left, right) {
    this.operation = operation;
    this.left = left;
    this.right = right;
    Object.freeze(this);
}

BinaryOperation.prototype.evaluate = function () {
    return binaryOperations[this.operation](this.left.evaluate.apply(this.left, arguments), this.right.evaluate.apply(this.right, arguments));
};

BinaryOperation.prototype.toString = function () {
    return this.left + " " + this.right + " " + this.operation;
};

BinaryOperation.prototype.prefix = function() {
    return "(" + this.operation + " " + this.left.prefix() + " " + this.right.prefix() + ")";
};

BinaryOperation.prototype.simplify = function() {
    var a = this.left.simplify();
    var b = this.right.simplify();
    if (a instanceof Const && b instanceof Const) {
        return new Const(binaryOperations[this.operation](a.evaluate(), b.evaluate()));
    }
    return this.extraSimplify(a, b);
};

function binaryBuilder(name, diff, simplify) {
    var a = function(left, right) {
        BinaryOperation.call(this, name, left, right);
    };
    a.prototype = Object.create(BinaryOperation.prototype);
    a.prototype.diff = diff;
    a.prototype.extraSimplify = simplify;
    return a;
}

var Negate = unaryBuilder("negate",
    function(variable) {
        return new Negate(this.child.diff(variable));
    },
    function(a) {
        if (a instanceof Const) {
            return new Const(-a.evaluate());
        }
        if (a instanceof Negate) {
            return a.child;
        }
        return new Negate(a);
    });


var Sin = unaryBuilder("sin",
    function(variable) {
        return new Multiply(new Cos(this.child), this.child.diff(variable));
    },
    function (a) {
        return new Sin(a);
    });

var Cos = unaryBuilder("cos",
    function(variable) {
        return new Negate(new Multiply(new Sin(this.child), this.child.diff(variable)));
    },
    function(a) {
        return new Cos(a);
    }
);

var Exp = unaryBuilder("exp",
    function(variable) {
        return new Multiply(this, this.child.diff(variable));
    },
    function(a) {
        return new Exp(a);
    }
);

var ArcTan = unaryBuilder("atan",
    function (variable) {
        return new Multiply(new Divide(new Const(1), new Add(new Const(1), new Multiply(this.child, this.child))), this.child.diff(variable));
    },
    function(a) {
        return new ArcTan(a);
    }
);

function isConstEvaluate(a, val) {
    return (a instanceof Const && a.evaluate() == val);
}

var Add = binaryBuilder("+",
    function (variable) {
        return new Add(this.left.diff(variable), this.right.diff(variable));
    },
    function(a, b) {
        if (isConstEvaluate(a, 0)) {
            return b;
        }
        if (isConstEvaluate(b, 0)) {
            return a;
        }
        return new Add(a, b);
    });

var Subtract = binaryBuilder("-",
    function (variable) {
        return new Subtract(this.left.diff(variable), this.right.diff(variable));
    },
    function(a, b) {
        if (isConstEvaluate(a, 0)) {
            return new Negate(b);
        }
        if (isConstEvaluate(b, 0)) {
            return a;
        }
        return new Subtract(a, b);
    });


var Multiply = binaryBuilder("*",
    function (variable) {
        var a = new Multiply(this.left.diff(variable), this.right);
        var b = new Multiply(this.left, this.right.diff(variable));
        return new Add(a, b);
    },
    function(a, b) {
        if ((isConstEvaluate(a, 0)) || (isConstEvaluate(b, 0))) {
            return new Const(0);
        }
        if (isConstEvaluate(a, 1)) {
            return b;
        }
        if (isConstEvaluate(b, 1)) {
            return a;
        }
        return new Multiply(a, b);
    });

var Divide = binaryBuilder("/",
    function (variable) {
        var a = new Multiply(this.left.diff(variable), this.right);
        var b = new Multiply(this.left, this.right.diff(variable));
        return new Divide(new Subtract(a, b), new Multiply(this.right, this.right));
    },
    function(a, b) {
        if (isConstEvaluate(a, 0)) {
            return new Const(0);
        }
        if (isConstEvaluate(b, 1)) {
            return a;
        }
        return new Divide(a, b);
    });

var unaryClasses = {
    "negate": Negate,
    "cos": Cos,
    "sin": Sin,
    "exp": Exp,
    "atan": ArcTan
};

var binaryClasses = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide
};

function parsePrefix (s) {
    function throwError(text,pos) {
        var str = "";
        for (var i = 0; i < s.length; i++) {
            str += s[i];
            if (i == pos) {
                str += "<--HERE-->";
            }
        }
        throw Error(text + " expression: " + str);
    }
    s = s.replace(/\(/g, " ( ");
    s = s.replace(/\)/g, " ) ");
    s = s.match(/\S+/g);
    var st = Array();
    for (var i = 0; i < s.length; i++) {
        var token = s[i];
        if (token in variables) {
            st.push(new Variable(token));
            continue;
        }
        if (token == "(" || token in unaryOperations || token in binaryOperations) {
            st.push(token);
            continue;
        }
        if (token == ")") {
            var arr = Array();
            while (st.length > 0 && st[st.length - 1] != "(") {
                arr.push(st.pop());
            }
            if (st[st.length - 1] != "(") {
                throwError("We need more brackets.", i);
            }
            st.pop();
            var operation = arr.pop();
            arr.reverse();
            if (operation in unaryOperations) {
                if (arr.length != 1) {
                    throwError("Unary operation requires 1 argument, found " + arr.length + ".", i);
                }
                st.push(new unaryClasses[operation](arr[0]));
            } else if (operation in binaryOperations) {
                if (arr.length != 2) {
                    throwError("Binary operation requires 2 arguments, found " + arr.length + ".", i);
                }
                st.push(new binaryClasses[operation](arr[0], arr[1]));
            } else {
                throwError("Invalid operation.", i);
            }
            continue;
        }
        token = parseFloat(+token);
        if (isNaN(token) || s[i][0] == "+") {
            throwError("Invalid token", i);
        }
        st.push(new Const(token));
    }
    if (st.length == 1) {
        return st[0];
    }
    throw Error("Missing )");
}

var expr = parsePrefix("(atan (- x y))");
println(expr);