package main.item.device;

import main.item.Item;

public class CoffeeMachine extends Item {
    private CoffeeMachine() {
    }

    public static CoffeeMachine createCoffeeMachine() {
        return new CoffeeMachine();
    }

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
