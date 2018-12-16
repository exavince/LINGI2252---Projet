package main.command;

import main.ConnectedHouse;

import java.util.logging.Level;

public class SayExpression implements Command {
    private final TerminalExpression<String> content;

    SayExpression(TerminalExpression<String> content) {
        this.content = content;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        CommandParser.LOGGER.log(Level.INFO, "## [User] say: " + content.evaluate(house));
        house.findRoom(house.getPosition()).sendToItems(content.evaluate(house));
    }
}
