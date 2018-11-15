package main;

import java.io.FileNotFoundException;

public interface ConnectedHouseParser {
    ConnectedHouse parse(String inputConfigFile, String inputStateFile) throws FileNotFoundException;
}
