package main.parametrization;

import main.Room;

public class DoubleImplication implements Expression {
    final Expression left;
    final Expression right;

    DoubleImplication(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Room context) {
        return new AndExpression(new Implication(left, right), new Implication(right, left)).interpret(context);
    }

    @Override
    public String toString() {
        return left.toString() + " <=> " + right.toString();
    }
}
