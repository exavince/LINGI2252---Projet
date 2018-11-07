package main.item.device;

import main.ConnectedHouse;
import main.item.Item;

public class CoffeeMachine implements Item {
    @Override
    public void onEvent(String message, ConnectedHouse house) {
        if (message.equals("make_coffee")) {
            makeCoffee();
        }
    }

    private void makeCoffee() {
        System.out.println("Coffee machine : Starting to make coffee..");
    }
}
