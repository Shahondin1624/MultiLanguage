package impls;

import listener.MonitoredLabel;

public class TestLabel implements MonitoredLabel {

    private final String key;
    private String value;

    public TestLabel(String key) {
        this.key = key;
    }

    @Override
    public void update(String value) {
        this.value = value;
    }

    @Override
    public String getMlsKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
