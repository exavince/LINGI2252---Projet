package main.command;

import main.RoomType;
import main.WeatherStatus;
import main.routine.HomeMood;

import java.util.Queue;
import java.util.Scanner;

/**
 * Grammar:
 * <COMMAND> ::= <SET> | <GET> | <MOVETO> | <SAY> | DONE | EXIT
 * <SET> ::= SET <ROOM> <ATTRIBUTE> <VALUE>
 * <VALUE> ::= <GET>|<INT>|<DOUBLE>|<WEATHER_STATUS>
 * <GET> ::= GET <ROOM> <ATTRIBUTE>
 * <MOVETO> ::= MOVETO <ROOM>
 * <SAY> ::= SAY <STRING>
 * <ATTRIBUTE> ::= TEMPERATURE, DESIRED_TEMPERATURE, ...
 * <ROOM> ::= KITCHEN, BEDROOM, ...
 */
public class CommandParser {
    private final Queue<String> input;
    private final Queue<String> output;

    public CommandParser(Queue<String> dataIN, Queue<String> dataOUT) {
        this.input = dataIN;
        this.output = dataOUT;
    }

    public Command parse() {
        String token = input.poll();
        switch (token) {
            case "EXIT":
                return Command.EXIT;
            case "DONE":
                return Command.DONE;
            case "SET":
                return set();
            case "GET":
                return get();
            case "MOVETO":
                return move();
            case "SAY":
                return say();
                /*
            case "EDIT":
                return edit();
                */
            default:
                throw new UnsupportedOperationException("Unknown command: " + token);
        }
    }

    /*
    private EditExpression edit() {
        return new EditExpression();
    }
    */

    private GetExpression get() {
        return new GetExpression(room(), attribute());
    }

    private MoveExpression move() {
        return new MoveExpression(room());
    }

    private SayExpression say() {
        //TODO parse quotes ?
        return new SayExpression(new TerminalExpression<>(input.poll()));
    }

    private String attribute() {
        return input.poll();
    }

    private ValueExpression value() {
        String token = input.poll();
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
        String token = input.poll();
        return RoomType.valueOf(token);
    }
}
