import java.util.function.DoubleUnaryOperator;

public class ExpressionEvaluator {
    private String expr;
    private int pos;
    private double x;

    public double evaluate(String expression, double xValue) {
        this.expr = expression.replaceAll("\\s+", "").toLowerCase();
        this.pos = 0;
        this.x = xValue;
        double result = parseExpression();
        if (pos < expr.length()) {
            throw new RuntimeException("Unexpected character at end: " + expr.charAt(pos));
        }
        return result;
    }

    private double parseExpression() {
        double value = parseTerm();
        while (pos < expr.length()) {
            char op = expr.charAt(pos);
            if (op == '+') {
                pos++;
                value += parseTerm();
            } else if (op == '-') {
                pos++;
                value -= parseTerm();
            } else {
                break;
            }
        }
        return value;
    }

    private double parseTerm() {
        double value = parsePower();
        while (pos < expr.length()) {
            char op = expr.charAt(pos);
            if (op == '*') {
                pos++;
                value *= parsePower();
            } else if (op == '/') {
                pos++;
                value /= parsePower();
            } else {
                break;
            }
        }
        return value;
    }

    private double parsePower() {
        double value = parseFactor();
        while (pos < expr.length() && expr.charAt(pos) == '^') {
            pos++;
            double exponent = parsePower(); // right-associative
            value = Math.pow(value, exponent);
        }
        return value;
    }

    private double parseFactor() {
        if (pos >= expr.length()) throw new RuntimeException("Unexpected end");
        char ch = expr.charAt(pos);
        if (ch == '+' || ch == '-') {
            pos++;
            double val = parseFactor();
            return (ch == '-') ? -val : val;
        }
        if (ch == '(') {
            pos++;
            double val = parseExpression();
            if (pos >= expr.length() || expr.charAt(pos) != ')')
                throw new RuntimeException("Missing closing ')'");
            pos++;
            return val;
        }
        if (Character.isDigit(ch) || ch == '.') {
            return parseNumber();
        }
        if (Character.isLetter(ch)) {
            return parseFunctionOrVariable();
        }
        throw new RuntimeException("Unexpected character: " + ch);
    }

    private double parseNumber() {
        int start = pos;
        while (pos < expr.length() && (Character.isDigit(expr.charAt(pos)) || expr.charAt(pos) == '.'))
            pos++;
        String numStr = expr.substring(start, pos);
        try {
            return Double.parseDouble(numStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number: " + numStr);
        }
    }

    private double parseFunctionOrVariable() {
        int start = pos;
        while (pos < expr.length() && Character.isLetter(expr.charAt(pos)))
            pos++;
        String name = expr.substring(start, pos);
        // Constants and variable
        if (name.equals("x")) return x;
        if (name.equals("pi")) return Math.PI;
        if (name.equals("e")) return Math.E;
        // Function call
        if (pos < expr.length() && expr.charAt(pos) == '(') {
            pos++;
            double arg = parseExpression();
            if (pos >= expr.length() || expr.charAt(pos) != ')')
                throw new RuntimeException("Missing ')' after " + name);
            pos++;
            switch (name) {
                case "sqrt": return Math.sqrt(arg);
                case "cos":  return Math.cos(arg);
                case "sin":  return Math.sin(arg);
                case "tan":  return Math.tan(arg);
                case "exp":  return Math.exp(arg);
                case "ln":   return Math.log(arg);
                case "log10":return Math.log10(arg);
                default: throw new RuntimeException("Unknown function: " + name);
            }
        }
        throw new RuntimeException("Unknown variable or function: " + name);
    }
}