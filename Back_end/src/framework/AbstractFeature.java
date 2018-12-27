package framework;

public class AbstractFeature implements Feature {
    private final String name;

    public AbstractFeature(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }
}
