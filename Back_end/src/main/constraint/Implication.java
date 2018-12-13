package main.constraint;

import main.Room;

public class Implication implements Constraint {
    private final Constraint left;
    private final Constraint right;

    public Implication(Constraint left, Constraint right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Room context) {
        return new LogicalOr(new Not(left), right).interpret(context);
    }

    @Override
    public String toString() {
        return left.toString() + " => " + right.toString();
    }
}
