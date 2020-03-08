package cn.enaium.foxbase.event;

import java.lang.reflect.Method;

public class Data {

    private Object source;
    private Method target;
    private EventPriority priority;

    public Data(Object source, Method target, EventPriority priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }

    public Object getSource() {
        return source;
    }

    public Method getTarget() {
        return target;
    }

    public EventPriority getPriority() {
        return priority;
    }
}
