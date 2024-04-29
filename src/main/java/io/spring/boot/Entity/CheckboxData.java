package io.spring.boot.Entity;

import java.util.List;

public class CheckboxData {
    private List<String> options;

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
    public boolean isOptionSelected(String option) {
        return options != null && options.contains(option);
    }
}