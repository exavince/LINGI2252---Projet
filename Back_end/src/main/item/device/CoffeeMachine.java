package main.item.device;

import main.item.Item;

public class CoffeeMachine extends Item {

    @Override
    public void onEvent(Object message) {
        if (message.equals("make_coffee")) {
            makeCoffee();
        }
    }

    private void makeCoffee() {
        println("Starting to make coffee..");
    }
}
