package main.command;

import main.ConnectedHouse;

public class TerminalExpression<T> implements ValueExpression<T> {
    private final T value;

    TerminalExpression(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(ConnectedHouse house) {
        return value;
    }
}
