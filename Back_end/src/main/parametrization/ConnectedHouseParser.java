package main.parametrization;

import main.ConnectedHouse;

import java.io.FileNotFoundException;

public interface ConnectedHouseParser {
    ConnectedHouse parse(String inputConfigFile, String inputStateFile) throws FileNotFoundException;
}
