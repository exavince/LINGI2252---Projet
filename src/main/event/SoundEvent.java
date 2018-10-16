package main.event;

public class SoundEvent implements SimulationEvent{
    private final String content;

    public SoundEvent(String contentIn) {
        this.content = contentIn;
    }

    public String getContent() {
        return content;
    }
}
