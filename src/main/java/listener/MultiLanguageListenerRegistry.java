package listener;

import java.util.HashMap;
import java.util.Map;

public class MultiLanguageListenerRegistry {
    private final Map<String, MonitoredLabel> monitoredLabelMap = new HashMap<>();

    public void registerLabel(String key, MonitoredLabel label) {
        monitoredLabelMap.put(key, label);
    }

    public Map<String, MonitoredLabel> getMonitored() {
        return monitoredLabelMap;
    }

    public void removeLabel(String key) {
        monitoredLabelMap.remove(key);
    }
}
