package main.item;

import main.ConnectedHouse;

public interface Item {
    void onEvent(String message, ConnectedHouse house);
}
