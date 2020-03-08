package cn.enaium.foxbase.event.events;

import cn.enaium.foxbase.event.Event;

public class EventKeyboard extends Event {

    private int keyCode;
    private int scanCode;
    private int action;
    private int modifiers;

    public EventKeyboard(int keyCode, int scanCode, int action, int modifiers) {
        super(Type.PRE);
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.action = action;
        this.modifiers = modifiers;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getScanCode() {
        return scanCode;
    }

    public int getAction() {
        return action;
    }

    public int getModifiers() {
        return modifiers;
    }
}
