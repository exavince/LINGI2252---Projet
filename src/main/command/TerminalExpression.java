package main.command;

import main.ConnectedHouse;

public class TerminalExpression<T> implements ValueExpression<T> {
    final T value;

    public TerminalExpression(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(ConnectedHouse house) {
        return value;
    }
}
