package framework;

public class AbstractFeature<T> implements Feature<T> {
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

    @Override
    public void activate(T target) {
    }

    @Override
    public void deactivate(T target) {
    }
}
