package cn.enaium.foxbase.client.modules.render;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.Event;
import cn.enaium.cf4m.annotation.Setting;
import cn.enaium.cf4m.annotation.module.Module;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.setting.SettingBase;
import cn.enaium.cf4m.setting.settings.*;
import cn.enaium.foxbase.client.FoxBase;
import cn.enaium.foxbase.client.events.EventRender2D;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2DUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@Module(value = "HUD", key = Keyboard.KEY_P, category = Category.RENDER)
public class HUD {

    private ArrayList<Category> categoryValues;
    private int currentCategoryIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;

    @Setting
    private EnableSetting tabGUI = new EnableSetting(this, "TabGUI", "", true);
    @Setting
    private EnableSetting toggleList = new EnableSetting(this, "ToggleList", "", true);

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
    public void toggleList(EventRender2D e) {

        if (!this.toggleList.isEnable()) {
            return;
        }

        int yStart = 1;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        ArrayList<Object> modules = new ArrayList();
        for (Object m : CF4M.getInstance().module.getModules()) {
            if (CF4M.getInstance().module.isEnable(m)) {
                modules.add(m);
            }
        }

        List<Object> mods = modules;
        mods.sort((o1, o2) -> FontUtils.getStringWidth(getDisplayName(o2)) - FontUtils.getStringWidth(getDisplayName(o1)));

        for (Object module : mods) {

            int startX = sr.getScaledWidth() - FontUtils.getStringWidth(getDisplayName(module)) - 6;

            Render2DUtils.drawRect(startX, yStart - 1, sr.getScaledWidth(), yStart + 12, ColorUtils.BG);
            Render2DUtils.drawRect(sr.getScaledWidth() - 2, yStart - 1, sr.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            Render2DUtils.drawVerticalLine(startX - 1, yStart - 2, yStart + 12, ColorUtils.SELECT);
            Render2DUtils.drawHorizontalLine(startX - 1, sr.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            FontUtils.drawStringWithShadow(getDisplayName(module), startX + 3, yStart, ColorUtils.SELECT);

            yStart += 13;
        }
    }

    private String getDisplayName(Object module) {
        String name = CF4M.getInstance().module.getName(module);
        String tag = CF4M.getInstance().module.getValue(module, "tag");
        String displayName;

        if (tag != null) {
            displayName = name + " " + tag;
        } else {
            displayName = name;
        }
        return displayName;
    }

    @Event
    public void onTabGUI(EventRender2D e) {
        if (!this.tabGUI.isEnable()) {
            return;
        }

        this.renderTopString(5, 5);
        int startX = 5;
        int startY = (5 + 9) + 2;
        Render2DUtils.drawRect(startX, startY, startX + this.getWidestCategory() + 5,
                startY + this.categoryValues.size() * (9 + 2), ColorUtils.BG);
        for (Category c : this.categoryValues) {
            if (this.getCurrentCategory().equals(c)) {
                Render2DUtils.drawRect(startX + 1, startY, startX + this.getWidestCategory() + 5 - 1, startY + 9 + 2,
                        ColorUtils.SELECT);
            }

            String name = c.name();
            FontUtils.drawStringWithShadow(name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase(),
                    startX + 2 + (this.getCurrentCategory().equals(c) ? 2 : 0), startY + 2, -1);
            startY += 9 + 2;
        }

        if (screen == 1 || screen == 2) {
            int startModsX = startX + this.getWidestCategory() + 6;
            int startModsY = ((5 + 9) + 2) + currentCategoryIndex * (9 + 2);
            Render2DUtils.drawRect(startModsX, startModsY, startModsX + this.getWidestMod() + 5,
                    startModsY + this.getModsForCurrentCategory().size() * (9 + 2), ColorUtils.BG);
            for (Object m : getModsForCurrentCategory()) {
                if (this.getCurrentModule().equals(m)) {
                    Render2DUtils.drawRect(startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1,
                            startModsY + 9 + 2, ColorUtils.SELECT);
                }
                FontUtils.drawStringWithShadow(CF4M.getInstance().module.getName(m) + (CF4M.getInstance().module.getSettings(m) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(m) ? 2 : 0),
                        startModsY + 2, CF4M.getInstance().module.isEnable(m) ? -1 : Color.GRAY.getRGB());
                startModsY += 9 + 2;
            }
        }
        if (screen == 2) {
            int startSettingX = (startX + this.getWidestCategory() + 6) + this.getWidestCategory() + 8;
            int startSettingY = ((5 + 9) + 2) + (currentCategoryIndex * (9 + 2)) + currentModIndex * (9 + 2);

            Render2DUtils.drawRect(startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
                    startSettingY + this.getSettingForCurrentMod().size() * (9 + 2), ColorUtils.BG);
            for (SettingBase s : this.getSettingForCurrentMod()) {

                if (this.getCurrentSetting().equals(s)) {
                    Render2DUtils.drawRect(startSettingX + 1, startSettingY, startSettingX + this.getWidestSetting() + 5 - 1,
                            startSettingY + 9 + 2, ColorUtils.SELECT);
                }
                if (s instanceof EnableSetting) {
                    FontUtils.drawStringWithShadow(s.getName() + ": " + ((EnableSetting) s).isEnable(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof IntegerSetting) {
                    FontUtils.drawStringWithShadow(s.getName() + ": " + ((IntegerSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof DoubleSetting) {
                    FontUtils.drawStringWithShadow(s.getName() + ": " + ((DoubleSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof FloatSetting) {
                    FontUtils.drawStringWithShadow(s.getName() + ": " + ((FloatSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof LongSetting) {
                    FontUtils.drawStringWithShadow(s.getName() + ": " + ((LongSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s instanceof ModeSetting) {
                    FontUtils.drawStringWithShadow(s.getName() + ": " + ((ModeSetting) s).getCurrent(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                }
                startSettingY += 9 + 2;
            }
        }
    }

    private void renderTopString(int x, int y) {
        FontUtils.drawStringWithShadow(FoxBase.instance.name + " B"
                + FoxBase.instance.version, x, y, new Color(67, 0, 99).getRGB());
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
            SettingBase s = this.getCurrentSetting();
            if (s instanceof EnableSetting) {
                ((EnableSetting) s).setEnable(!((EnableSetting) s).isEnable());
            } else if (s instanceof IntegerSetting) {
                ((IntegerSetting) s).setCurrent(((IntegerSetting) s).getCurrent() + 1);
            } else if (s instanceof DoubleSetting) {
                ((DoubleSetting) s).setCurrent(((DoubleSetting) s).getCurrent() + 0.1D);
            } else if (s instanceof FloatSetting) {
                ((FloatSetting) s).setCurrent(((FloatSetting) s).getCurrent() + 0.1F);
            } else if (s instanceof LongSetting) {
                ((LongSetting) s).setCurrent(((LongSetting) s).getCurrent() + 1L);
            } else if (s instanceof ModeSetting) {
                try {
                    ((ModeSetting) s).setCurrent(((ModeSetting) s).getModes().get(getCurrentModeIndex((ModeSetting) s) - 1));
                } catch (Exception e) {
                    ((ModeSetting) s).setCurrent(((ModeSetting) s).getModes().get(((ModeSetting) s).getModes().size() - 1));
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
            SettingBase s = this.getCurrentSetting();
            if (s instanceof EnableSetting) {
                ((EnableSetting) s).setEnable(!((EnableSetting) s).isEnable());
            } else if (s instanceof IntegerSetting) {
                ((IntegerSetting) s).setCurrent(((IntegerSetting) s).getCurrent() - 1);
            } else if (s instanceof DoubleSetting) {
                ((DoubleSetting) s).setCurrent(((DoubleSetting) s).getCurrent() - 0.1D);
            } else if (s instanceof FloatSetting) {
                ((FloatSetting) s).setCurrent(((FloatSetting) s).getCurrent() - 0.1F);
            } else if (s instanceof LongSetting) {
                ((LongSetting) s).setCurrent(((LongSetting) s).getCurrent() - 1L);
            } else if (s instanceof ModeSetting) {
                try {
                    ((ModeSetting) s).setCurrent(((ModeSetting) s).getModes().get(getCurrentModeIndex((ModeSetting) s) + 1));
                } catch (Exception e) {
                    ((ModeSetting) s).setCurrent(((ModeSetting) s).getModes().get(0));
                }
            }
        }
    }

    private int getCurrentModeIndex(ModeSetting setting) {
        int index = 0;
        for (String s : setting.getModes()) {
            index++;
            if (setting.getCurrent().equalsIgnoreCase(s)) {
                return index;
            }
        }
        return index;
    }


    private void right(int key) {
        if (this.screen == 0) {
            this.screen = 1;
        } else if (this.screen == 1 && this.getCurrentModule() != null && this.getSettingForCurrentMod() == null) {
            CF4M.getInstance().module.enable(this.getCurrentModule());
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == Keyboard.KEY_RETURN) {
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
            case Keyboard.KEY_UP:
                this.up();
                break;
            case Keyboard.KEY_DOWN:
                this.down();
                break;
            case Keyboard.KEY_RIGHT:
                this.right(Keyboard.KEY_RIGHT);
                break;
            case Keyboard.KEY_LEFT:
                this.left();
                break;
            case Keyboard.KEY_RETURN:
                this.right(Keyboard.KEY_RETURN);
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

    private Category getCurrentCategorry() {
        return this.categoryValues.get(this.currentCategoryIndex);
    }

    private Object getCurrentModule() {
        return getModsForCurrentCategory().get(currentModIndex);
    }

    private ArrayList<Object> getModsForCurrentCategory() {
        ArrayList<Object> mods = new ArrayList<>();
        Category c = getCurrentCategorry();
        for (Object m : CF4M.getInstance().module.getModules()) {
            if (CF4M.getInstance().module.getCategory(m).equals(c)) {
                mods.add(m);
            }
        }
        return mods;
    }

    private int getWidestSetting() {
        int width = 0;
        for (SettingBase s : getSettingForCurrentMod()) {
            String name;
            if (s instanceof EnableSetting) {
                name = s.getName() + ": " + ((EnableSetting) s).isEnable();
            } else if (s instanceof IntegerSetting) {
                name = s.getName() + ": " + ((IntegerSetting) s).getCurrent();
            } else if (s instanceof DoubleSetting) {
                name = s.getName() + ": " + ((DoubleSetting) s).getCurrent();
            } else if (s instanceof FloatSetting) {
                name = s.getName() + ": " + ((FloatSetting) s).getCurrent();
            } else if (s instanceof LongSetting) {
                name = s.getName() + ": " + ((LongSetting) s).getCurrent();
            } else if (s instanceof ModeSetting) {
                name = s.getName() + ": " + ((ModeSetting) s).getCurrent();
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
        for (Object m : CF4M.getInstance().module.getModules()) {
            int cWidth = FontUtils.getStringWidth(CF4M.getInstance().module.getName(m));
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
                    name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }


}
