"use strict";
var variables = {
    "x": 0,
    "y": 1,
    "z": 2
};

var unaryOperations = {
    "negate": function(value) {return -value;},
    "sin": Math.sin,
    "cos": Math.cos
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
    "sin": Sin
};

var binaryClasses = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide
};

var parse  = function (s) {
    var stack = [];
    s = s.match(/\S+/g);
    for (var i = 0; i < s.length; i++) {
        var token = s[i];
        if (token in variables) {
            stack.push(new Variable(token));
        } else if (token in unaryClasses) {
            var a = stack.pop();
            stack.push(new unaryClasses[token](a));
        } else if (token in binaryClasses) {
            var b = stack.pop();
            var a = stack.pop();
            stack.push(new binaryClasses[token](a, b));
        } else {
            stack.push(new Const(parseFloat(token)));
        }
    }
    return stack.pop();
};



var expr = parse("10");
println(expr.evaluate(1));
//expr = expr.diff('y');
//expr = expr.simplify();
