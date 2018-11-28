package main.parametrization;

import main.Room;

public class Not implements Expression {
    private final Expression content;

    public Not(Expression content) {
        this.content = content;
    }

    @Override
    public boolean interpret(Room context) {
        return !content.interpret(context);
    }

    @Override
    public String toString() {
        return "Not(" + content.toString() + ")";
    }
}
