package cn.enaium.foxbase.client.module.render;

import cn.enaium.cf4m.annotation.Autowired;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.module.Setting;
import cn.enaium.cf4m.container.ModuleContainer;
import cn.enaium.cf4m.provider.ModuleProvider;
import cn.enaium.cf4m.provider.SettingProvider;
import cn.enaium.foxbase.client.FoxBase;
import cn.enaium.foxbase.client.event.Events.*;
import cn.enaium.foxbase.client.setting.*;
import cn.enaium.foxbase.client.utils.ColorUtils;
import cn.enaium.foxbase.client.utils.FontUtils;
import cn.enaium.foxbase.client.utils.Render2D;
import com.google.common.collect.Lists;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static cn.enaium.foxbase.client.module.Type.RENDER;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Autowired
@Module(value = "HUD", key = GLFW.GLFW_KEY_O, type = RENDER)
public class HUD {

    private ModuleContainer module;

    private int currentTypeIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;

    @Setting("TabGUI")
    private EnableSetting tabGUI = new EnableSetting(true);

    @Setting("ToggleList")
    private EnableSetting toggleList = new EnableSetting(true);

    public HUD() {
        this.currentTypeIndex = 0;
        this.currentModIndex = 0;
        this.currentSettingIndex = 0;
        this.editMode = false;
        this.screen = 0;
    }

    @Event
    public void toggleList(Render2DEvent e) {
        if (!this.toggleList.getEnable()) {
            return;
        }

        int yStart = 1;


        for (ModuleProvider module : module.getAll().stream().filter(ModuleProvider::getEnable).sorted((o1, o2) -> FontUtils.getStringWidth(o2.getName()) - FontUtils.getStringWidth(o1.getName())).collect(Collectors.toCollection(Lists::newArrayList))) {

            int startX = Render2D.getScaledWidth() - FontUtils.getStringWidth(module.getName()) - 6;

            Render2D.drawRect(e.getMatrixStack(), startX, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.BG);
            Render2D.drawRect(e.getMatrixStack(), Render2D.getScaledWidth() - 2, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            Render2D.drawVerticalLine(e.getMatrixStack(), startX - 1, yStart - 2, yStart + 12, ColorUtils.SELECT);
            Render2D.drawHorizontalLine(e.getMatrixStack(), startX - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            FontUtils.drawStringWithShadow(e.getMatrixStack(), module.getName(), startX + 3, yStart, ColorUtils.SELECT);

            yStart += 13;
        }
    }

    @Event
    public void onTabGUI(Render2DEvent e) {
        if (!this.tabGUI.getEnable()) {
            return;
        }

        FontUtils.drawStringWithShadow(e.getMatrixStack(), FoxBase.instance.name + " B"
                + FoxBase.instance.version, 5, 5, new Color(67, 0, 99).getRGB());
        int startX = 5;
        int startY = (5 + 9) + 2;
        Render2D.drawRect(e.getMatrixStack(), startX, startY, startX + this.getWidestType() + 5,
                startY + this.module.getAllType().size() * (9 + 2), ColorUtils.BG);
        for (String name : this.module.getAllType()) {
            if (this.getCurrentType().equals(name)) {
                Render2D.drawRect(e.getMatrixStack(), startX + 1, startY, startX + this.getWidestType() + 5 - 1, startY + 9 + 2,
                        ColorUtils.SELECT);
            }

            FontUtils.drawStringWithShadow(e.getMatrixStack(), name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(),
                    startX + 2 + (this.getCurrentType().equals(name) ? 2 : 0), startY + 2, -1);
            startY += 9 + 2;
        }

        if (screen == 1 || screen == 2) {
            int startModsX = startX + this.getWidestType() + 6;
            int startModsY = ((5 + 9) + 2) + currentTypeIndex * (9 + 2);
            Render2D.drawRect(e.getMatrixStack(), startModsX, startModsY, startModsX + this.getWidestMod() + 5,
                    startModsY + this.getModsForCurrentType().size() * (9 + 2), ColorUtils.BG);
            for (ModuleProvider module : getModsForCurrentType()) {
                if (this.getCurrentModule().equals(module)) {
                    Render2D.drawRect(e.getMatrixStack(), startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1,
                            startModsY + 9 + 2, ColorUtils.SELECT);
                }
                FontUtils.drawStringWithShadow(e.getMatrixStack(), module.getName() + (module.getSetting().getAll().size() != 0 ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(module) ? 2 : 0),
                        startModsY + 2, module.getEnable() ? -1 : Color.GRAY.getRGB());
                startModsY += 9 + 2;
            }
        }
        if (screen == 2) {
            int startSettingX = (startX + this.getWidestType() + 6) + this.getWidestType() + 8;
            int startSettingY = ((5 + 9) + 2) + (currentTypeIndex * (9 + 2)) + currentModIndex * (9 + 2);

            Render2D.drawRect(e.getMatrixStack(), startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
                    startSettingY + this.getSettingForCurrentMod().size() * (9 + 2), ColorUtils.BG);
            for (SettingProvider setting : this.getSettingForCurrentMod()) {

                if (this.getCurrentSetting().equals(setting)) {
                    Render2D.drawRect(e.getMatrixStack(), startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1,
                            startSettingY + 9 + 2, ColorUtils.SELECT);
                }
                if (setting.getSetting() instanceof EnableSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), setting.getName() + ": " + ((EnableSetting) setting.getSetting()).getEnable(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(setting) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(setting) ? -1 : Color.GRAY.getRGB());
                } else if (setting.getSetting() instanceof IntegerSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), setting.getName() + ": " + setting.<IntegerSetting>getSetting().getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(setting) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(setting) ? -1 : Color.GRAY.getRGB());
                } else if (setting.getSetting() instanceof DoubleSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), setting.getName() + ": " + setting.<DoubleSetting>getSetting().getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(setting) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(setting) ? -1 : Color.GRAY.getRGB());
                } else if (setting.getSetting() instanceof FloatSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), setting.getName() + ": " + setting.<FloatSetting>getSetting().getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(setting) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(setting) ? -1 : Color.GRAY.getRGB());
                } else if (setting.getSetting() instanceof LongSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), setting.getName() + ": " + setting.<LongSetting>getSetting().getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(setting) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(setting) ? -1 : Color.GRAY.getRGB());
                } else if (setting.getSetting() instanceof ModeSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), setting.getName() + ": " + setting.<ModeSetting>getSetting().getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(setting) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(setting) ? -1 : Color.GRAY.getRGB());
                }
                startSettingY += 9 + 2;
            }
        }
    }

    private void up() {
        if (this.currentTypeIndex > 0 && this.screen == 0) {
            this.currentTypeIndex--;
        } else if (this.currentTypeIndex == 0 && this.screen == 0) {
            this.currentTypeIndex = this.module.getAllType().size() - 1;
        } else if (this.currentModIndex > 0 && this.screen == 1) {
            this.currentModIndex--;
        } else if (this.currentModIndex == 0 && this.screen == 1) {
            this.currentModIndex = this.getModsForCurrentType().size() - 1;
        } else if (this.currentSettingIndex > 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex--;
        } else if (this.currentSettingIndex == 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex = this.getSettingForCurrentMod().size() - 1;
        }

        if (editMode) {
            SettingProvider setting = this.getCurrentSetting();
            if (setting.getSetting() instanceof EnableSetting) {
                setting.<EnableSetting>getSetting().setEnable(!setting.<EnableSetting>getSetting().getEnable());
            } else if (setting.getSetting() instanceof IntegerSetting) {
                setting.<IntegerSetting>getSetting().setCurrent(setting.<IntegerSetting>getSetting().getCurrent() + 1);
            } else if (setting.getSetting() instanceof DoubleSetting) {
                setting.<DoubleSetting>getSetting().setCurrent(setting.<DoubleSetting>getSetting().getCurrent() + 0.1D);
            } else if (setting.getSetting() instanceof FloatSetting) {
                setting.<FloatSetting>getSetting().setCurrent(setting.<FloatSetting>getSetting().getCurrent() + 0.1F);
            } else if (setting.getSetting() instanceof LongSetting) {
                setting.<LongSetting>getSetting().setCurrent(setting.<LongSetting>getSetting().getCurrent() + 1L);
            } else if (setting.getSetting() instanceof ModeSetting) {
                try {
                    setting.<ModeSetting>getSetting().setCurrent(setting.<ModeSetting>getSetting().getModes().get(setting.<ModeSetting>getSetting().getCurrentModeIndex() - 1));
                } catch (Exception e) {
                    setting.<ModeSetting>getSetting().setCurrent(setting.<ModeSetting>getSetting().getModes().get(setting.<ModeSetting>getSetting().getModes().size() - 1));
                }

            }
        }
    }

    private void down() {
        if (this.currentTypeIndex < this.module.getAllType().size() - 1 && this.screen == 0) {
            this.currentTypeIndex++;
        } else if (this.currentTypeIndex == this.module.getAllType().size() - 1 && this.screen == 0) {
            this.currentTypeIndex = 0;
        } else if (this.currentModIndex < this.getModsForCurrentType().size() - 1 && this.screen == 1) {
            this.currentModIndex++;
        } else if (this.currentModIndex == this.getModsForCurrentType().size() - 1 && this.screen == 1) {
            this.currentModIndex = 0;
        } else if (this.currentSettingIndex < this.getSettingForCurrentMod().size() - 1 && this.screen == 2
                && !this.editMode) {
            this.currentSettingIndex++;
        } else if (this.currentSettingIndex == this.getSettingForCurrentMod().size() - 1 && this.screen == 2
                && !this.editMode) {
            this.currentSettingIndex = 0;
        }

        if (editMode) {
            SettingProvider setting = this.getCurrentSetting();
            if (setting.getSetting() instanceof EnableSetting) {
                setting.<EnableSetting>getSetting().setEnable(!setting.<EnableSetting>getSetting().getEnable());
            } else if (setting.getSetting() instanceof IntegerSetting) {
                setting.<IntegerSetting>getSetting().setCurrent(setting.<IntegerSetting>getSetting().getCurrent() - 1);
            } else if (setting.getSetting() instanceof DoubleSetting) {
                setting.<DoubleSetting>getSetting().setCurrent(setting.<DoubleSetting>getSetting().getCurrent() - 0.1D);
            } else if (setting.getSetting() instanceof FloatSetting) {
                setting.<FloatSetting>getSetting().setCurrent(setting.<FloatSetting>getSetting().getCurrent() - 0.1F);
            } else if (setting.getSetting() instanceof LongSetting) {
                setting.<LongSetting>getSetting().setCurrent(setting.<LongSetting>getSetting().getCurrent() - 1L);
            } else if (setting instanceof ModeSetting) {
                try {
                    setting.<ModeSetting>getSetting().setCurrent(setting.<ModeSetting>getSetting().getModes().get(setting.<ModeSetting>getSetting().getCurrentModeIndex() + 1));
                } catch (Exception e) {
                    setting.<ModeSetting>getSetting().setCurrent(setting.<ModeSetting>getSetting().getModes().get(0));
                }

            }
        }
    }


    private void right(int key) {
        if (this.screen == 0) {
            this.screen = 1;
        } else if (this.screen == 1 && this.getCurrentModule() != null && this.getSettingForCurrentMod() == null) {
            this.getCurrentModule().enable();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == GLFW.GLFW_KEY_ENTER) {
            this.getCurrentModule().enable();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null) {
            this.screen = 2;
        } else if (this.screen == 2) {
            this.editMode = !this.editMode;
        }

    }

    private void left() {
        if (this.screen == 1) {
            this.screen = 0;
            this.currentModIndex = 0;
        } else if (this.screen == 2) {
            this.screen = 1;
            this.currentSettingIndex = 0;
        }

    }

    @Event
    public void onKey(KeyboardEvent e) {
        switch (e.getKey()) {
            case GLFW.GLFW_KEY_UP:
                this.up();
                break;
            case GLFW.GLFW_KEY_DOWN:
                this.down();
                break;
            case GLFW.GLFW_KEY_RIGHT:
                this.right(GLFW.GLFW_KEY_RIGHT);
                break;
            case GLFW.GLFW_KEY_LEFT:
                this.left();
                break;
            case GLFW.GLFW_KEY_ENTER:
                this.right(GLFW.GLFW_KEY_ENTER);
                break;
        }
    }

    private SettingProvider getCurrentSetting() {
        return getSettingForCurrentMod().get(currentSettingIndex);
    }

    private ArrayList<SettingProvider> getSettingForCurrentMod() {
        return getCurrentModule().getSetting().getAll();
    }

    private String getCurrentType() {
        return this.module.getAllType().get(this.currentTypeIndex);
    }

    private ModuleProvider getCurrentModule() {
        return getModsForCurrentType().get(currentModIndex);
    }

    private ArrayList<ModuleProvider> getModsForCurrentType() {
        return module.getAllByType(getCurrentType());
    }

    private int getWidestSetting() {
        int width = 0;
        for (SettingProvider setting : getSettingForCurrentMod()) {
            String name;
            if (setting.getSetting() instanceof EnableSetting) {
                name = setting.getName() + ": " + (setting.<EnableSetting>getSetting()).getEnable();
            } else if (setting.getSetting() instanceof IntegerSetting) {
                name = setting.getName() + ": " + setting.<IntegerSetting>getSetting().getCurrent();
            } else if (setting.getSetting() instanceof DoubleSetting) {
                name = setting.getName() + ": " + setting.<DoubleSetting>getSetting().getCurrent();
            } else if (setting.getSetting() instanceof FloatSetting) {
                name = setting.getName() + ": " + setting.<FloatSetting>getSetting().getCurrent();
            } else if (setting.getSetting() instanceof LongSetting) {
                name = setting.getName() + ": " + setting.<LongSetting>getSetting().getCurrent();
            } else if (setting.getSetting() instanceof ModeSetting) {
                name = setting.getName() + ": " + setting.<ModeSetting>getSetting().getCurrent();
            } else {
                name = "NULL";
            }
            if (FontUtils.getStringWidth(name) > width) {
                width = FontUtils.getStringWidth(name);
            }
        }
        return width;
    }

    private int getWidestMod() {
        int width = 0;
        for (ModuleProvider module : module.getAll()) {
            int cWidth = FontUtils.getStringWidth(module.getName());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

    private int getWidestType() {
        int width = 0;
        for (String name : this.module.getAllType()) {
            int cWidth = FontUtils.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }


}
