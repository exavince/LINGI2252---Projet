package main.command;

import main.RoomType;

import java.util.Scanner;

public class CommandFactory {
    public Command create(Scanner userInput) {
        String token = userInput.next();
        switch (token) {
            case "MOVE":
                return new Move(RoomType.valueOf(userInput.next()));
            case "DESIRED_TEMPERATURE":
                return new SetDesiredTemperature(RoomType.valueOf(userInput.next()), userInput.nextInt());

            case "EXIT":
                throw new RuntimeException(token);
            default:
                throw new RuntimeException("Unknown token " + token);
        }
    }
}
