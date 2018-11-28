package main.parametrization;

import main.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureModel {
    private final List<Expression> rules = new ArrayList<>();

    public FeatureModel(Expression minimumRule, Expression... rulesIn) {
        rules.add(minimumRule);
        rules.addAll(Arrays.asList(rulesIn));
    }

    /**
     * @param context the configuration
     * @return Whether the configuration is valid or not
     */
    public boolean interpret(Room context) {
        for (Expression rule : rules) {
            if (!rule.interpret(context)) {
                System.err.println("Rule " + rule + " is not valid in Room " + context);
                return false;
            }
        }
        return true;
    }
}
