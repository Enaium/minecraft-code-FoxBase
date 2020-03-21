package cn.enaium.foxbase.module;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class Module {
    private boolean toggle;
    private String name;
    private int keyCode;
    private Category category;

    private String displayText;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Module(String name, int keyCode, Category category) {
        this.toggle = false;
        this.name = name;
        this.keyCode = keyCode;
        this.category = category;
    }

    protected void addSetting(Setting setting) {
        FoxBase.instance.settingManager.addSetting(setting);
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDisplayName() {
        if (displayText == null) {
            return name;
        } else {
            return name  + " : " + displayText;
        }
    }

    protected void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public void toggle()
    {
        this.toggle = !this.toggle;
        if(this.toggle) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {
        FoxBase.instance.eventManager.register(this);
    }

    public void onDisable() {
        FoxBase.instance.eventManager.unregister(this);
    }
}
