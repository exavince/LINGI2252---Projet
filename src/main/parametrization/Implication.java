package main.parametrization;

import main.Room;

public class Implication implements Expression {
    final Expression left;
    final Expression right;

    Implication(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Room context) {
        return new OrExpression(new Not(left), right).interpret(context);
    }

    @Override
    public String toString() {
        return left.toString() + " => " + right.toString();
    }
}
