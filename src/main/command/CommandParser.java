package main.command;

import main.RoomType;
import main.WeatherStatus;
import main.routine.HomeMood;

import java.util.Scanner;

/**
 * Grammar:
 * <COMMAND> ::= <SET> | <GET> | <MOVETO> | EXIT
 * <SET> ::= SET <ROOM> <ATTRIBUTE> <VALUE>
 * <VALUE> ::= <GET>|<INT>|<DOUBLE>|<WEATHER_STATUS>
 * <GET> ::= GET <ROOM> <ATTRIBUTE>
 * <ATTRIBUTE> ::= TEMPERATURE, DESIRED_TEMPERATURE, ...
 * <ROOM> ::= KITCHEN, BEDROOM, ...
 */
public class CommandParser {
    private final Scanner input;

    public CommandParser(Scanner input) {
        this.input = input;
    }

    public Command parse() {
        String token = input.next();
        switch (token) {
            case "EXIT":
                return Command.EXIT;
            case "SET":
                return set();
            case "GET":
                return get();
            case "MOVETO":
                return move();
            default:
                throw new UnsupportedOperationException("Unknown command: " + token);
        }
    }

    private GetExpression get() {
        return new GetExpression(room(), attribute());
    }

    private MoveExpression move() {
        return new MoveExpression(room());
    }

    private String attribute() {
        return input.next();
    }

    private ValueExpression value() {
        String token = input.next();
        if (token.equals("GET")) {
            return get();
        } else {
            // TODO guess type based on what should be expected with attribute ?
            try {
                return new TerminalExpression<>(WeatherStatus.valueOf(token));
            } catch (IllegalArgumentException e1) {
                try {
                    return new TerminalExpression<>(HomeMood.valueOf(token));
                } catch (IllegalArgumentException e) {
                    try {
                        return new TerminalExpression<>(Integer.parseInt(token));
                    } catch (NumberFormatException e2) {
                        try {
                            return new TerminalExpression<>(Double.parseDouble(token));
                        } catch (NumberFormatException e3) {
                            throw new RuntimeException("Unknown value token " + token);
                        }
                    }
                }
            }
        }
    }

    private SetExpression set() {
        return new SetExpression(room(), attribute(), value());
    }

    private RoomType room() {
        String token = input.next();
        return RoomType.valueOf(token);
    }
}
