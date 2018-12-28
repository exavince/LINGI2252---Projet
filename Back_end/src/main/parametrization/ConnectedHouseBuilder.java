package main.parametrization;

import framework.InvalidModelConfigurationException;
import main.ConnectedHouse;
import main.RoomType;

import java.util.List;

public interface ConnectedHouseBuilder {
    ConnectedHouse getHouse();

    void addRoom(RoomType roomType, List<String> featureNames) throws InvalidModelConfigurationException;
}
