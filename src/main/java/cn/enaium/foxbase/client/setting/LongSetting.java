package cn.enaium.foxbase.client.setting;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class LongSetting {

    private Long current;
    private Long min;
    private Long max;

    public LongSetting(Long current, Long min, Long max) {
        this.current = current;
        this.min = min;
        this.max = max;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
