package main.command;

import main.ConnectedHouse;

import static main.ConnectedHouseSimulator.dataOUT;

public class SayExpression implements Command {
    private final TerminalExpression<String> content;

    SayExpression(TerminalExpression<String> content) {
        this.content = content;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        System.out.println("## [User] say: " + content.evaluate(house));
        dataOUT.add("## [User] say: " + content.evaluate(house));
        house.findRoom(house.getPosition()).sendToItems(content.evaluate(house));
    }
}
