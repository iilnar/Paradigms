var binaryOperation = function (operation, left, right) {
    return function (x, y, z) {
        return operation(left(x, y, z), right(x, y, z));
    }
};

var cnst = function (value) {
    return function() {
        return value;
    }
};

var variable = function(name) {
    return function (x, y, z) {
        return (name === "x") ? (x) : ((name === "y") ? (y) : (z));
    }
};

var add = function(left, right) {
    return binaryOperation(function(left, right) {return left + right;}, left, right);
};

var subtract = function(left, right) {
    return binaryOperation(function(left, right) {return left - right;}, left, right);
};

var multiply = function(left, right) {
    return binaryOperation(function(left, right) {return left * right;}, left, right);
};

var divide = function (left, right) {
    return binaryOperation(function(left, right) {return left / right;}, left, right);
};

var getFunction = function (label) {
    switch (label) {
        case '+':
            return add;
        case '-':
            return subtract;
        case '*':
            return multiply;
        case  '/':
            return divide;
    }
};

var parse  = function (s) {
    var stack = [];
    s = s.match(/\S+/g);
    for (var i = 0; i < s.length; i++) {
        switch (s[i]) {
            case 'x':
            case 'y':
            case 'z':
                stack.push(variable(s[i]));
                break;
            case '+':
            case '-':
            case '*':
            case '/':
                var b = stack.pop();
                var a = stack.pop();
                stack.push(getFunction(s[i])(a, b));
                break;
            default :
                stack.push(cnst(parseFloat(s[i])));
        }
    }
    return stack.pop();
};

var test = function () {
    var expr = subtract(
        multiply(
            cnst(2),
            variable("x")
        ),
        cnst(3)
    );
    for (var i = 0; i <= 10; i++) {
        println("f(" + i + ") = " + expr(i));
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
//println(parse("x y z + +")(1, 2, 3));
//test3("x y z * *");
