package main.parametrization;

import main.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrExpression implements Expression {
    private final List<Expression> expressions = new ArrayList<>();

    OrExpression(Expression minimumExpression, Expression... expressionsIn) {
        expressions.add(minimumExpression);
        expressions.addAll(Arrays.asList(expressionsIn));
    }

    @Override
    public boolean interpret(Room context) {
        for (Expression expr : expressions) {
            if (expr.interpret(context)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return expressions.stream().map(Object::toString).collect(Collectors.joining(" OR "));
    }
}
