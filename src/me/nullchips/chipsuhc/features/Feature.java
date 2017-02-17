package me.nullchips.chipsuhc.features;

import me.nullchips.chipsuhc.utils.SettingsManager;

/**
 * Created by Tommy on 17/02/2017.
 */
public abstract class Feature {

    private boolean enabled;
    private final String displayName;
    private final String configId;

    private SettingsManager sm = SettingsManager.getInstance();
    private FeatureManager fm = FeatureManager.getInstance();

    public Feature(String displayName, String configId, boolean enabled) {
        this.displayName = displayName;
        this.configId = configId;
        //this.enabled = fm.getStateFromConfig(configId);
        this.enabled = true;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        enabled = enabled;
        if(enabled) { onEnable(); } else { onDisable(); }
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getConfigId() {
        return configId;
    }

}
