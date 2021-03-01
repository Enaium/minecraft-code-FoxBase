package cn.enaium.foxbase.client.modules.render;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.FoxBase;
import cn.enaium.foxbase.client.events.Render2DEvent;
import cn.enaium.foxbase.client.settings.*;
import cn.enaium.foxbase.client.utils.ColorUtils;
import cn.enaium.foxbase.client.utils.FontUtils;
import cn.enaium.foxbase.client.utils.Render2D;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module(value = "HUD", key = GLFW.GLFW_KEY_O, category = Category.RENDER)
public class HUD {

    private final ArrayList<Category> categoryValues;
    private int currentCategoryIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;

    @Setting("TabGUI")
    private EnableSetting tabGUI = new EnableSetting(true);

    @Setting("ToggleList")
    private EnableSetting toggleList = new EnableSetting(true);

    public HUD() {
        this.categoryValues = new ArrayList<>();
        this.currentCategoryIndex = 0;
        this.currentModIndex = 0;
        this.currentSettingIndex = 0;
        this.editMode = false;
        this.screen = 0;
        this.categoryValues.addAll(Arrays.asList(Category.values()));
    }

    @Event
    public void toggleList(Render2DEvent e) {
        if (!this.toggleList.getEnable()) {
            return;
        }

        int yStart = 1;

        ArrayList<Object> modules = new ArrayList();
        for (Object module : CF4M.INSTANCE.module.getModules()) {
            if (CF4M.INSTANCE.module.getEnable(module)) {
                modules.add(module);
            }
        }

        List<Object> mods = modules;
        mods.sort((o1, o2) -> FontUtils.getStringWidth(CF4M.INSTANCE.module.getName(o2)) - FontUtils.getStringWidth(CF4M.INSTANCE.module.getName(o1)));

        for (Object module : mods) {

            int startX = Render2D.getScaledWidth() - FontUtils.getStringWidth(CF4M.INSTANCE.module.getName(module)) - 6;

            Render2D.drawRect(startX, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.BG);
            Render2D.drawRect(Render2D.getScaledWidth() - 2, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            Render2D.drawVerticalLine(startX - 1, yStart - 2, yStart + 12, ColorUtils.SELECT);
            Render2D.drawHorizontalLine(startX - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            FontUtils.drawStringWithShadow(CF4M.INSTANCE.module.getName(module), startX + 3, yStart, ColorUtils.SELECT);

            yStart += 13;
        }
    }

    @Event
    public void onTabGUI(Render2DEvent e) {
        if (!this.tabGUI.getEnable()) {
            return;
        }

        FontUtils.drawStringWithShadow(FoxBase.instance.name + " B"
                + FoxBase.instance.version, 5, 5, new Color(67, 0, 99).getRGB());
        int startX = 5;
        int startY = (5 + 9) + 2;
        Render2D.drawRect(startX, startY, startX + this.getWidestCategory() + 5,
                startY + this.categoryValues.size() * (9 + 2), ColorUtils.BG);
        for (Category c : this.categoryValues) {
            if (this.getCurrentCategory().equals(c)) {
                Render2D.drawRect(startX + 1, startY, startX + this.getWidestCategory() + 5 - 1, startY + 9 + 2,
                        ColorUtils.SELECT);
            }

            String name = c.name();
            FontUtils.drawStringWithShadow(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(),
                    startX + 2 + (this.getCurrentCategory().equals(c) ? 2 : 0), startY + 2, -1);
            startY += 9 + 2;
        }

        if (screen == 1 || screen == 2) {
            int startModsX = startX + this.getWidestCategory() + 6;
            int startModsY = ((5 + 9) + 2) + currentCategoryIndex * (9 + 2);
            Render2D.drawRect(startModsX, startModsY, startModsX + this.getWidestMod() + 5,
                    startModsY + this.getModsForCurrentCategory().size() * (9 + 2), ColorUtils.BG);
            for (Object module : getModsForCurrentCategory()) {
                if (this.getCurrentModule().equals(module)) {
                    Render2D.drawRect(startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1,
                            startModsY + 9 + 2, ColorUtils.SELECT);
                }
                FontUtils.drawStringWithShadow(CF4M.INSTANCE.module.getName(module) + (CF4M.INSTANCE.setting.getSettings(module) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(module) ? 2 : 0),
                        startModsY + 2, CF4M.INSTANCE.module.getEnable(module) ? -1 : Color.GRAY.getRGB());
                startModsY += 9 + 2;
            }
        }
        if (screen == 2) {
            int startSettingX = (startX + this.getWidestCategory() + 6) + this.getWidestCategory() + 8;
            int startSettingY = ((5 + 9) + 2) + (currentCategoryIndex * (9 + 2)) + currentModIndex * (9 + 2);

            Render2D.drawRect(startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
                    startSettingY + this.getSettingForCurrentMod().size() * (9 + 2), ColorUtils.BG);
            for (Object s : this.getSettingForCurrentMod()) {

                if (this.getCurrentSetting().equals(s)) {
                    Render2D.drawRect(startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1,
                            startSettingY + 9 + 2, ColorUtils.SELECT);
                }
                if (s instanceof EnableSetting) {
                    FontUtils.drawStringWithShadow(CF4M.INSTANCE.setting.getName(this.getCurrentModule(), s) + ": " + ((EnableSetting) s).getEnable(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof IntegerSetting) {
                    FontUtils.drawStringWithShadow(CF4M.INSTANCE.setting.getName(this.getCurrentModule(), s) + ": " + ((IntegerSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof DoubleSetting) {
                    FontUtils.drawStringWithShadow(CF4M.INSTANCE.setting.getName(this.getCurrentModule(), s) + ": " + ((DoubleSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof FloatSetting) {
                    FontUtils.drawStringWithShadow(CF4M.INSTANCE.setting.getName(this.getCurrentModule(), s) + ": " + ((FloatSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof LongSetting) {
                    FontUtils.drawStringWithShadow(CF4M.INSTANCE.setting.getName(this.getCurrentModule(), s) + ": " + ((LongSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof ModeSetting) {
                    FontUtils.drawStringWithShadow(CF4M.INSTANCE.setting.getName(this.getCurrentModule(), s) + ": " + ((ModeSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                }
                startSettingY += 9 + 2;
            }
        }
    }

    private void up() {
        if (this.currentCategoryIndex > 0 && this.screen == 0) {
            this.currentCategoryIndex--;
        } else if (this.currentCategoryIndex == 0 && this.screen == 0) {
            this.currentCategoryIndex = this.categoryValues.size() - 1;
        } else if (this.currentModIndex > 0 && this.screen == 1) {
            this.currentModIndex--;
        } else if (this.currentModIndex == 0 && this.screen == 1) {
            this.currentModIndex = this.getModsForCurrentCategory().size() - 1;
        } else if (this.currentSettingIndex > 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex--;
        } else if (this.currentSettingIndex == 0 && this.screen == 2 && !this.editMode) {
            this.currentSettingIndex = this.getSettingForCurrentMod().size() - 1;
        }

        if (editMode) {
            Object setting = this.getCurrentSetting();
            if (setting instanceof EnableSetting) {
                ((EnableSetting) setting).setEnable(!((EnableSetting) setting).getEnable());
            } else if (setting instanceof IntegerSetting) {
                ((IntegerSetting) setting).setCurrent(((IntegerSetting) setting).getCurrent() + 1);
            } else if (setting instanceof DoubleSetting) {
                ((DoubleSetting) setting).setCurrent(((DoubleSetting) setting).getCurrent() + 0.1D);
            } else if (setting instanceof FloatSetting) {
                ((FloatSetting) setting).setCurrent(((FloatSetting) setting).getCurrent() + 0.1F);
            } else if (setting instanceof LongSetting) {
                ((LongSetting) setting).setCurrent(((LongSetting) setting).getCurrent() + 1L);
            } else if (setting instanceof ModeSetting) {
                try {
                    ((ModeSetting) setting).setCurrent(((ModeSetting) setting).getModes().get(((ModeSetting) setting).getCurrentModeIndex() - 1));
                } catch (Exception e) {
                    ((ModeSetting) setting).setCurrent(((ModeSetting) setting).getModes().get(((ModeSetting) setting).getModes().size() - 1));
                }

            }
        }
    }

    private void down() {
        if (this.currentCategoryIndex < this.categoryValues.size() - 1 && this.screen == 0) {
            this.currentCategoryIndex++;
        } else if (this.currentCategoryIndex == this.categoryValues.size() - 1 && this.screen == 0) {
            this.currentCategoryIndex = 0;
        } else if (this.currentModIndex < this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
            this.currentModIndex++;
        } else if (this.currentModIndex == this.getModsForCurrentCategory().size() - 1 && this.screen == 1) {
            this.currentModIndex = 0;
        } else if (this.currentSettingIndex < this.getSettingForCurrentMod().size() - 1 && this.screen == 2
                && !this.editMode) {
            this.currentSettingIndex++;
        } else if (this.currentSettingIndex == this.getSettingForCurrentMod().size() - 1 && this.screen == 2
                && !this.editMode) {
            this.currentSettingIndex = 0;
        }

        if (editMode) {
            Object setting = this.getCurrentSetting();
            if (setting instanceof EnableSetting) {
                ((EnableSetting) setting).setEnable(!((EnableSetting) setting).getEnable());
            } else if (setting instanceof IntegerSetting) {
                ((IntegerSetting) setting).setCurrent(((IntegerSetting) setting).getCurrent() - 1);
            } else if (setting instanceof DoubleSetting) {
                ((DoubleSetting) setting).setCurrent(((DoubleSetting) setting).getCurrent() - 0.1D);
            } else if (setting instanceof FloatSetting) {
                ((FloatSetting) setting).setCurrent(((FloatSetting) setting).getCurrent() - 0.1F);
            } else if (setting instanceof LongSetting) {
                ((LongSetting) setting).setCurrent(((LongSetting) setting).getCurrent() - 1L);
            } else if (setting instanceof ModeSetting) {
                try {
                    ((ModeSetting) setting).setCurrent(((ModeSetting) setting).getModes().get(((ModeSetting) setting).getCurrentModeIndex() + 1));
                } catch (Exception e) {
                    ((ModeSetting) setting).setCurrent(((ModeSetting) setting).getModes().get(0));
                }

            }
        }
    }


    private void right(int key) {
        if (this.screen == 0) {
            this.screen = 1;
        } else if (this.screen == 1 && this.getCurrentModule() != null && this.getSettingForCurrentMod() == null) {
            CF4M.INSTANCE.module.enable(this.getCurrentModule());
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == GLFW.GLFW_KEY_ENTER) {
            CF4M.INSTANCE.module.enable(this.getCurrentModule());
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

    private Object getCurrentSetting() {
        return getSettingForCurrentMod().get(currentSettingIndex);
    }

    private ArrayList<Object> getSettingForCurrentMod() {
        return CF4M.INSTANCE.setting.getSettings(getCurrentModule());
    }

    private Category getCurrentCategory() {
        return this.categoryValues.get(this.currentCategoryIndex);
    }

    private Object getCurrentModule() {
        return getModsForCurrentCategory().get(currentModIndex);
    }

    private ArrayList<Object> getModsForCurrentCategory() {
        return CF4M.INSTANCE.module.getModules(getCurrentCategory());
    }

    private int getWidestSetting() {
        int width = 0;
        for (Object setting : getSettingForCurrentMod()) {
            String name;
            if (setting instanceof EnableSetting) {
                name = CF4M.INSTANCE.setting.getName(this.getCurrentModule(), setting) + ": " + ((EnableSetting) setting).getEnable();
            } else if (setting instanceof IntegerSetting) {
                name = CF4M.INSTANCE.setting.getName(this.getCurrentModule(), setting) + ": " + ((IntegerSetting) setting).getCurrent();
            } else if (setting instanceof DoubleSetting) {
                name = CF4M.INSTANCE.setting.getName(this.getCurrentModule(), setting) + ": " + ((DoubleSetting) setting).getCurrent();
            } else if (setting instanceof FloatSetting) {
                name = CF4M.INSTANCE.setting.getName(this.getCurrentModule(), setting) + ": " + ((FloatSetting) setting).getCurrent();
            } else if (setting instanceof LongSetting) {
                name = CF4M.INSTANCE.setting.getName(this.getCurrentModule(), setting) + ": " + ((LongSetting) setting).getCurrent();
            } else if (setting instanceof ModeSetting) {
                name = CF4M.INSTANCE.setting.getName(this.getCurrentModule(), setting) + ": " + ((ModeSetting) setting).getCurrent();
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
        for (Object module : CF4M.INSTANCE.module.getModules()) {
            int cWidth = FontUtils.getStringWidth(CF4M.INSTANCE.module.getName(module));
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }

    private int getWidestCategory() {
        int width = 0;
        for (Category c : this.categoryValues) {
            String name = c.name();
            int cWidth = FontUtils.getStringWidth(
                    name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }


}
