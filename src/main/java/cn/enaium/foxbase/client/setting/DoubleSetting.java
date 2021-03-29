package cn.enaium.foxbase.client.setting;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class DoubleSetting {

    private Double current;
    private Double min;
    private Double max;

    public DoubleSetting(Double current, Double min, Double max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
