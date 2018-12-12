package main.parametrization;

import main.ConnectedHouse;

import java.io.FileNotFoundException;
import java.util.concurrent.BlockingQueue;

public interface ConnectedHouseParser {
    ConnectedHouse parse(String inputConfigFile, String inputStateFile) throws FileNotFoundException;
}
