package cn.enaium.foxbase.client.modules.render;

import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.setting.SettingBase;
import cn.enaium.cf4m.setting.settings.*;
import cn.enaium.cf4m.CF4M;
import cn.enaium.foxbase.client.FoxBase;
import cn.enaium.foxbase.client.events.Render2DEvent;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2D;
import cn.enaium.foxbase.utils.Utils;
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

    private ArrayList<Category> categoryValues;
    private int currentCategoryIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;

    @Setting
    private EnableSetting tabGUI = new EnableSetting(this, "TabGUI", "TabGUI", true);

    @Setting
    private EnableSetting toggleList = new EnableSetting(this, "ToggleList", "ToggleList", true);

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
        if (!this.toggleList.isEnable()) {
            return;
        }

        int yStart = 1;

        ArrayList<Object> modules = new ArrayList();
        for (Object module : CF4M.getInstance().module.getModules()) {
            if (CF4M.getInstance().module.isEnable(module)) {
                modules.add(module);
            }
        }

        List<Object> mods = modules;
        mods.sort((o1, o2) -> FontUtils.getStringWidth(CF4M.getInstance().module.getName(o2)) - FontUtils.getStringWidth(CF4M.getInstance().module.getName(o1)));

        for (Object module : mods) {

            int startX = Render2D.getScaledWidth() - FontUtils.getStringWidth(CF4M.getInstance().module.getName(module)) - 6;

            Render2D.drawRect(e.getMatrixStack(), startX, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.BG);
            Render2D.drawRect(e.getMatrixStack(), Render2D.getScaledWidth() - 2, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            Render2D.drawVerticalLine(e.getMatrixStack(), startX - 1, yStart - 2, yStart + 12, ColorUtils.SELECT);
            Render2D.drawHorizontalLine(e.getMatrixStack(), startX - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            FontUtils.drawStringWithShadow(e.getMatrixStack(), CF4M.getInstance().module.getName(module), startX + 3, yStart, ColorUtils.SELECT);

            yStart += 13;
        }
    }

    @Event
    public void onTabGUI(Render2DEvent e) {
        if (!this.tabGUI.isEnable()) {
            return;
        }

        FontUtils.drawStringWithShadow(e.getMatrixStack(), FoxBase.instance.name + " B"
                + FoxBase.instance.version, 5, 5, new Color(67, 0, 99).getRGB());
        int startX = 5;
        int startY = (5 + 9) + 2;
        Render2D.drawRect(e.getMatrixStack(), startX, startY, startX + this.getWidestCategory() + 5,
                startY + this.categoryValues.size() * (9 + 2), ColorUtils.BG);
        for (Category c : this.categoryValues) {
            if (this.getCurrentCategory().equals(c)) {
                Render2D.drawRect(e.getMatrixStack(), startX + 1, startY, startX + this.getWidestCategory() + 5 - 1, startY + 9 + 2,
                        ColorUtils.SELECT);
            }

            String name = c.name();
            FontUtils.drawStringWithShadow(e.getMatrixStack(), name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(),
                    startX + 2 + (this.getCurrentCategory().equals(c) ? 2 : 0), startY + 2, -1);
            startY += 9 + 2;
        }

        if (screen == 1 || screen == 2) {
            int startModsX = startX + this.getWidestCategory() + 6;
            int startModsY = ((5 + 9) + 2) + currentCategoryIndex * (9 + 2);
            Render2D.drawRect(e.getMatrixStack(), startModsX, startModsY, startModsX + this.getWidestMod() + 5,
                    startModsY + this.getModsForCurrentCategory().size() * (9 + 2), ColorUtils.BG);
            for (Object module : getModsForCurrentCategory()) {
                if (this.getCurrentModule().equals(module)) {
                    Render2D.drawRect(e.getMatrixStack(), startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1,
                            startModsY + 9 + 2, ColorUtils.SELECT);
                }
                FontUtils.drawStringWithShadow(e.getMatrixStack(), CF4M.getInstance().module.getName(module) + (CF4M.getInstance().module.getSettings(module) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(module) ? 2 : 0),
                        startModsY + 2, CF4M.getInstance().module.isEnable(module) ? -1 : Color.GRAY.getRGB());
                startModsY += 9 + 2;
            }
        }
        if (screen == 2) {
            int startSettingX = (startX + this.getWidestCategory() + 6) + this.getWidestCategory() + 8;
            int startSettingY = ((5 + 9) + 2) + (currentCategoryIndex * (9 + 2)) + currentModIndex * (9 + 2);

            Render2D.drawRect(e.getMatrixStack(), startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
                    startSettingY + this.getSettingForCurrentMod().size() * (9 + 2), ColorUtils.BG);
            for (SettingBase s : this.getSettingForCurrentMod()) {

                if (this.getCurrentSetting().equals(s)) {
                    Render2D.drawRect(e.getMatrixStack(), startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1,
                            startSettingY + 9 + 2, ColorUtils.SELECT);
                }
                if (s instanceof EnableSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + ((EnableSetting) s).isEnable(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof IntegerSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + ((IntegerSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof DoubleSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + ((DoubleSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof FloatSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + ((FloatSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof LongSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + ((LongSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof ModeSetting) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + ((ModeSetting) s).getCurrent(),
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
            SettingBase setting = this.getCurrentSetting();
            if (setting instanceof EnableSetting) {
                ((EnableSetting) setting).setEnable(!((EnableSetting) setting).isEnable());
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
                    ((ModeSetting) setting).setCurrent(((ModeSetting) setting).getModes().get(Utils.getCurrentModeIndex((ModeSetting) setting) - 1));
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
            SettingBase setting = this.getCurrentSetting();
            if (setting instanceof EnableSetting) {
                ((EnableSetting) setting).setEnable(!((EnableSetting) setting).isEnable());
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
                    ((ModeSetting) setting).setCurrent(((ModeSetting) setting).getModes().get(Utils.getCurrentModeIndex((ModeSetting) setting) + 1));
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
            CF4M.getInstance().module.enable(this.getCurrentModule());
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == GLFW.GLFW_KEY_ENTER) {
            CF4M.getInstance().module.enable(this.getCurrentModule());
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

    private SettingBase getCurrentSetting() {
        return getSettingForCurrentMod().get(currentSettingIndex);
    }

    private ArrayList<SettingBase> getSettingForCurrentMod() {
        return CF4M.getInstance().module.getSettings(getCurrentModule());
    }

    private Category getCurrentCategory() {
        return this.categoryValues.get(this.currentCategoryIndex);
    }

    private Object getCurrentModule() {
        return getModsForCurrentCategory().get(currentModIndex);
    }

    private ArrayList<Object> getModsForCurrentCategory() {
        return Utils.getModulesForCategory(getCurrentCategory());
    }

    private int getWidestSetting() {
        int width = 0;
        for (SettingBase setting : getSettingForCurrentMod()) {
            String name;
            if (setting instanceof EnableSetting) {
                name = setting.getName() + ": " + ((EnableSetting) setting).isEnable();
            } else if (setting instanceof IntegerSetting) {
                name = setting.getName() + ": " + ((IntegerSetting) setting).getCurrent();
            } else if (setting instanceof DoubleSetting) {
                name = setting.getName() + ": " + ((DoubleSetting) setting).getCurrent();
            } else if (setting instanceof FloatSetting) {
                name = setting.getName() + ": " + ((FloatSetting) setting).getCurrent();
            } else if (setting instanceof LongSetting) {
                name = setting.getName() + ": " + ((LongSetting) setting).getCurrent();
            } else if (setting instanceof ModeSetting) {
                name = setting.getName() + ": " + ((ModeSetting) setting).getCurrent();
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
        for (Object module : CF4M.getInstance().module.getModules()) {
            int cWidth = FontUtils.getStringWidth(CF4M.getInstance().module.getName(module));
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
