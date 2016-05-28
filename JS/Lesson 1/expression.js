var binaryOperation = function (operation) {
    return function (left, right) {
        return function() {
            return operation(left.apply(null, arguments), right.apply(null, arguments));
        }
    }
};
var unaryOperation = function (operation) {
    return function (child) {
        return function () {
            return operation(child.apply(null, arguments));
        }
    }
};

var cnst = function (value) {
    return function() {
        return value;
    }
};

var variable = function(name) {
    return function () {
        return arguments[variables[name]];
    }
};

var negate = unaryOperation(function(value) {return -value;});
var abs    = unaryOperation(Math.abs);
var log    = unaryOperation(Math.log);

var add      = binaryOperation(function(left, right) {return left + right;});
var subtract = binaryOperation(function(left, right) {return left - right;});
var multiply = binaryOperation(function(left, right) {return left * right;});
var divide   = binaryOperation(function(left, right) {return left / right;});
var mod      = binaryOperation(function(left, right) {return left % right;});

var power = binaryOperation(Math.pow);

var variables = {
    "x": 0,
    "y": 1,
    "z": 2
};

var unaryOperations = {
    "negate": negate,
    "abs": abs,
    "log": log
};

 var binaryOperations = {
     "+": add,
     "-": subtract,
     "*": multiply,
     "/": divide,
     "%": mod,
     "**": power
 };

var parse  = function (s) {
    var stack = [];
    s = s.match(/\S+/g);
    for (var i = 0; i < s.length; i++) {
        if (s[i] in variables) {
            stack.push(variable(s[i]));
        } else if (s[i] in unaryOperations) {
            var a = stack.pop();
            stack.push(unaryOperations[s[i]](a));
        } else if (s[i] in binaryOperations) {
            var b = stack.pop();
            var a = stack.pop();
            stack.push(binaryOperations[s[i]](a, b));
        } else {
            stack.push(cnst(parseFloat(s[i])));
        }
    }
    return stack.pop();
};

var test = function () {
    var expr = add (
        variable("x"),
        variable("y")
    );
    for (var i = 0; i <= 10; i++) {
        println("f(" + i + ") = " + expr(i, i));
    }
};

var test3 = function(str) {
    var expr = parse(str);
    for (var x = 0; x <= 10; x++) {
        for (var y = 0; y <= 10; y++) {
            for (var z = 0; z <= 10; z++) {
                println("f(" + x + ", " + y + ", " + z + ") = " + expr(x, y, z));
            }
        }
    }
};
//test();
//test();

//println(parse("x y z + +")(1, 2, 3));
//test3("x y z * *");
//parse("484387758 y 404459994 * **")(0.413927, 0.833131, 0);
