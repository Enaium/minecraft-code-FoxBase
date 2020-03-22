package cn.enaium.foxbase.event.events;

import cn.enaium.foxbase.event.Event;

public class EventKeyboard extends Event {

    private int keyCode;

    public EventKeyboard(int keyCode) {
        super(Type.PRE);
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

}
