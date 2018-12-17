package main.command;

import main.RoomType;
import main.WeatherStatus;
import main.routine.HomeMood;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static final Logger LOGGER = Logger.getLogger(CommandParser.class.getName());
    private final Iterator<String> input;

    public CommandParser(String expression) {
        LOGGER.info("Parsing command " + expression);
        this.input = Arrays.asList(expression.toUpperCase().split(" ")).iterator();
    }

    public Command parse() {
        String token = input.next();
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
            case "EDIT":
                return edit();
            default:
                LOGGER.warning("Unknown command: " + token);
                throw new UnsupportedOperationException("Unknown command: " + token);
        }
    }

    private EditExpression edit() {
        RoomType room = room();
        List<String> remainingTokens = new ArrayList<>();
        while (input.hasNext()) {
            remainingTokens.add(input.next());
        }
        return new EditExpression(room, remainingTokens);
    }

    private GetExpression get() {
        return new GetExpression(room(), attribute());
    }

    private MoveExpression move() {
        return new MoveExpression(room());
    }

    private SayExpression say() {
        //TODO parse quotes ?
        return new SayExpression(new TerminalExpression<>(input.next()));
    }

    private String attribute() {
        return input.next();
    }

    private ValueExpression value() {
        try {
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
                                LOGGER.warning("Unknown value token " + token);
                                throw new RuntimeException("Unknown value token " + token);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, "A value expression was expected");
            return null;
        }
    }

    private SetExpression set() {
        return new SetExpression(room(), attribute(), value());
    }

    private RoomType room() {
        String token = input.next();
        try {
            return RoomType.valueOf(token);
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Unknown room type " + token);
            throw new UnsupportedOperationException(e);
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.WARNING, "A room expression was expected");
            throw new UnsupportedOperationException(e);
        }
    }
}
