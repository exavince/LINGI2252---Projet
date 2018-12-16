package framework;

public class InvalidModelConfigurationException extends Exception {
    public InvalidModelConfigurationException(FeatureModelConfiguration modelState, FeatureModel model) {
        super("The model state " + modelState + " is not valid with respect to " + model);
    }
}
