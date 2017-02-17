package me.nullchips.chipsuhc.features;

import me.nullchips.chipsuhc.utils.SettingsManager;

import java.util.ArrayList;

/**
 * Created by Tommy on 17/02/2017.
 */
public class FeatureManager {

    private FeatureManager() { }

    private static FeatureManager instance;

    public static FeatureManager getInstance() {
        if(instance == null) {
            instance = new FeatureManager();
        }
        return instance;
    }

    private SettingsManager sm = SettingsManager.getInstance();

    private ArrayList<Feature> allFeatures = new ArrayList<Feature>();

    public void addFeature(Feature f) {
        allFeatures.add(f);
    }

    public void registerFeatures() {
        allFeatures.add(new HealthList());

        for(Feature f : this.allFeatures) {
            f.onEnable();
        }

    }

    public boolean getStateFromConfig(String configId) {

        if(!(sm.getConfig().contains("features." + configId))) {
            return false;
        }

        String s = sm.getConfig().getString("features." + configId);

        if(s.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }

    }

}
