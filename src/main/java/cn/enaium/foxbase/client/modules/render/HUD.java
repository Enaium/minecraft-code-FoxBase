package cn.enaium.foxbase.client.modules.render;

import cn.enaium.cf4m.event.EventAT;
import cn.enaium.cf4m.event.events.KeyboardEvent;
import cn.enaium.cf4m.module.Category;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.module.ModuleAT;
import cn.enaium.cf4m.setting.Setting;
import cn.enaium.cf4m.setting.SettingAT;
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
public class HUD extends Module {

    private ArrayList<Category> categoryValues;
    private int currentCategoryIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;

    @SettingAT
    private EnableSetting tabGUI = new EnableSetting(this, "TabGUI", "TabGUI", true);

    @SettingAT
    private EnableSetting toggleList = new EnableSetting(this, "ToggleList", "ToggleList", true);

    public HUD() {
        super("HUD", "HUD", GLFW.GLFW_KEY_P, Category.RENDER);
        this.categoryValues = new ArrayList<>();
        this.currentCategoryIndex = 0;
        this.currentModIndex = 0;
        this.currentSettingIndex = 0;
        this.editMode = false;
        this.screen = 0;
        this.categoryValues.addAll(Arrays.asList(Category.values()));
    }

    @EventAT
    public void toggleList(Render2DEvent e) {
        if (!this.toggleList.isEnable()) {
            return;
        }

        int yStart = 1;

        ArrayList<Module> modules = new ArrayList();
        for (Module m : CF4M.getInstance().moduleManager.modules) {
            if (m.isEnable()) {
                modules.add(m);
            }
        }

        List<Module> mods = modules;
        mods.sort((o1, o2) -> FontUtils.getStringWidth(o2.getName()) - FontUtils.getStringWidth(o1.getName()));

        for (Module module : mods) {

            int startX = Render2D.getScaledWidth() - FontUtils.getStringWidth(module.getName()) - 6;

            Render2D.drawRect(e.getMatrixStack(), startX, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.BG);
            Render2D.drawRect(e.getMatrixStack(), Render2D.getScaledWidth() - 2, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            Render2D.drawVerticalLine(e.getMatrixStack(), startX - 1, yStart - 2, yStart + 12, ColorUtils.SELECT);
            Render2D.drawHorizontalLine(e.getMatrixStack(), startX - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            FontUtils.drawStringWithShadow(e.getMatrixStack(), module.getName(), startX + 3, yStart, ColorUtils.SELECT);

            yStart += 13;
        }
    }

    @EventAT
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
            FontUtils.drawStringWithShadow(e.getMatrixStack(), name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase(),
                    startX + 2 + (this.getCurrentCategory().equals(c) ? 2 : 0), startY + 2, -1);
            startY += 9 + 2;
        }

        if (screen == 1 || screen == 2) {
            int startModsX = startX + this.getWidestCategory() + 6;
            int startModsY = ((5 + 9) + 2) + currentCategoryIndex * (9 + 2);
            Render2D.drawRect(e.getMatrixStack(), startModsX, startModsY, startModsX + this.getWidestMod() + 5,
                    startModsY + this.getModsForCurrentCategory().size() * (9 + 2), ColorUtils.BG);
            for (Module m : getModsForCurrentCategory()) {
                if (this.getCurrentModule().equals(m)) {
                    Render2D.drawRect(e.getMatrixStack(), startModsX + 1, startModsY, startModsX + this.getWidestMod() + 5 - 1,
                            startModsY + 9 + 2, ColorUtils.SELECT);
                }
                FontUtils.drawStringWithShadow(e.getMatrixStack(), m.getName() + (Utils.getSettingsForModule(m) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(m) ? 2 : 0),
                        startModsY + 2, m.isEnable() ? -1 : Color.GRAY.getRGB());
                startModsY += 9 + 2;
            }
        }
        if (screen == 2) {
            int startSettingX = (startX + this.getWidestCategory() + 6) + this.getWidestCategory() + 8;
            int startSettingY = ((5 + 9) + 2) + (currentCategoryIndex * (9 + 2)) + currentModIndex * (9 + 2);

            Render2D.drawRect(e.getMatrixStack(), startSettingX, startSettingY, startSettingX + this.getWidestSetting() + 5,
                    startSettingY + this.getSettingForCurrentMod().size() * (9 + 2), ColorUtils.BG);
            for (Setting s : this.getSettingForCurrentMod()) {

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
            Setting s = this.getCurrentSetting();
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
                    ((ModeSetting) s).setCurrent(((ModeSetting) s).getModes().get(Utils.getCurrentModeIndex((ModeSetting) s) - 1));
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
            Setting s = this.getCurrentSetting();
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
                    ((ModeSetting) s).setCurrent(((ModeSetting) s).getModes().get(Utils.getCurrentModeIndex((ModeSetting) s) + 1));
                } catch (Exception e) {
                    ((ModeSetting) s).setCurrent(((ModeSetting) s).getModes().get(0));
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

    @EventAT
    public void onKey(KeyboardEvent e) {
        switch (e.getKeyCode()) {
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

    private Setting getCurrentSetting() {
        return getSettingForCurrentMod().get(currentSettingIndex);
    }

    private ArrayList<Setting> getSettingForCurrentMod() {
        return Utils.getSettingsForModule(getCurrentModule());
    }

    private Category getCurrentCategory() {
        return this.categoryValues.get(this.currentCategoryIndex);
    }

    private Module getCurrentModule() {
        return getModsForCurrentCategory().get(currentModIndex);
    }

    private ArrayList<Module> getModsForCurrentCategory() {
        return Utils.getModulesForCategory(getCurrentCategory());
    }

    private int getWidestSetting() {
        int width = 0;
        for (Setting s : getSettingForCurrentMod()) {
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
        for (Module m : CF4M.getInstance().moduleManager.modules) {
            int cWidth = FontUtils.getStringWidth(m.getName());
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
