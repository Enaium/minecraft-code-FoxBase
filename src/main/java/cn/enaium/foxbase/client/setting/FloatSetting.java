package cn.enaium.foxbase.client.setting;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class FloatSetting {

    private Float current;
    private Float min;
    private Float max;

    public FloatSetting(Float current, Float min, Float max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Float getCurrent() {
        return current;
    }

    public void setCurrent(Float current) {
        this.current = current;
    }

    public Float getMin() {
        return min;
    }

    public void setMin(Float min) {
        this.min = min;
    }

    public Float getMax() {
        return max;
    }

    public void setMax(Float max) {
        this.max = max;
    }
}
