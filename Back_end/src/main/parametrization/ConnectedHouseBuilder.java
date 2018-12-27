package main.parametrization;

import framework.FeatureModel;
import framework.InvalidModelConfigurationException;
import framework.editing.ActivateFeature;
import framework.editing.FeatureAction;
import framework.editing.FeatureEditingStrategy;
import framework.editing.TryOnCopyStrategy;
import main.ConnectedHouse;
import main.ConnectedHouseFeatureModel;
import main.Room;
import main.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hides complexity behind building a ConnectedHouse
 */
public class ConnectedHouseBuilder {
    private final ConnectedHouse house = new ConnectedHouse();
    private final FeatureModel model = ConnectedHouseFeatureModel.getInstance();
    private final FeatureEditingStrategy strategy = new TryOnCopyStrategy(ConnectedHouseFeatureModel.getInstance());

    public ConnectedHouse getHouse() {
        return house;
    }

    void addRoom(RoomType roomType, List<String> featureNames) throws InvalidModelConfigurationException {
        Room room = new Room(roomType);
        house.attach(room);
        List<FeatureAction> actions = new ArrayList<>();
        actions.add(new ActivateFeature<>(model.getFeature("Central"), room));
        actions.addAll(featureNames.stream().map(name -> new ActivateFeature<>(model.getFeature(name), room)).collect(Collectors.toList()));
        strategy.apply(room.getModelState(), actions);
    }
}
