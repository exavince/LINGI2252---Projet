package main.item;

public interface ItemSubject {
    void sendToItems(Object message);

    void attach(Item itemIn);
}
