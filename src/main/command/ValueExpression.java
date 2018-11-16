package main.command;

import main.ConnectedHouse;

@FunctionalInterface
public interface ValueExpression {
    // TODO at the moment given the variety of values, we use strings and put the responsability of parsing the string on SetExpression.
    // TODO In the future, perhaps find a better way
    String evaluate(ConnectedHouse house);
}
