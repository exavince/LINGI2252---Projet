package main.command;

import main.ConnectedHouse;

public class SayExpression implements Command {
    private final TerminalExpression<String> content;

    SayExpression(TerminalExpression<String> content) {
        this.content = content;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        house.log("## [User] say: " + content.evaluate(house));
        house.findRoom(house.getPosition()).sendToItems(content.evaluate(house));
    }
}
