package main.command;

import main.ConnectedHouse;

@FunctionalInterface
public interface ValueExpression<T> {
    // TODO at the moment given the variety of values, we use object and put the responsibility of casting the result on SetExpression.
    // TODO In the future, perhaps find a better way
    T evaluate(ConnectedHouse house);
}
