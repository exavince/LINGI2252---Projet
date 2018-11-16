package main.command;

import main.RoomType;

import java.util.Scanner;

/**
 * Grammar:
 * <COMMAND> ::= <SET> | <GET> | <MOVETO> | EXIT
 * <SET> ::= SET <ROOM> <ATTRIBUTE> <VALUE>
 * <VALUE> ::= <GET>|<INT>|<DOUBLE>|
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
            return house -> token;
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
