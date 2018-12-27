package framework;

public interface ConcreteFeature<T> extends Feature {
    void activate(T target);

    void deactivate(T target);
}
