package cn.enaium.foxbase.module.modules.render;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.event.EventTarget;
import cn.enaium.foxbase.event.events.EventKeyboard;
import cn.enaium.foxbase.event.events.EventRender2D;
import cn.enaium.foxbase.module.Category;
import cn.enaium.foxbase.module.Module;
import cn.enaium.foxbase.setting.Setting;
import cn.enaium.foxbase.utils.ColorUtils;
import cn.enaium.foxbase.utils.FontUtils;
import cn.enaium.foxbase.utils.Render2D;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HUD extends Module {

    private ArrayList<Category> categoryValues;
    private int currentCategoryIndex, currentModIndex, currentSettingIndex;
    private boolean editMode;

    private int screen;

    private Setting tabGUI = new Setting(this, "TabGUI", true);
    private Setting toggleList = new Setting(this, "ToggleList", true);

    public HUD() {
        super("HUD", GLFW.GLFW_KEY_P, Category.RENDER);
        this.categoryValues = new ArrayList<Category>();
        this.currentCategoryIndex = 0;
        this.currentModIndex = 0;
        this.currentSettingIndex = 0;
        this.editMode = false;
        this.screen = 0;
        this.categoryValues.addAll(Arrays.asList(Category.values()));
        addSetting(tabGUI);
        addSetting(toggleList);
    }

    @EventTarget
    public void toggleList(EventRender2D e) {
        if (!this.toggleList.isToggle()) {
            return;
        }

        int yStart = 1;

        ArrayList<Module> modules = new ArrayList();
        for (Module m : FoxBase.instance.moduleManager.getModules()) {
            if (m.isToggle()) {
                modules.add(m);
            }
        }

        List<Module> mods = modules;
        mods.sort((o1, o2) -> FontUtils.getStringWidth(o2.getDisplayName()) - FontUtils.getStringWidth(o1.getDisplayName()));

        for (Module module : mods) {

            int startX = Render2D.getScaledWidth() - FontUtils.getStringWidth(module.getDisplayName()) - 6;

            Render2D.drawRect(e.getMatrixStack(), startX, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.BG);
            Render2D.drawRect(e.getMatrixStack(), Render2D.getScaledWidth() - 2, yStart - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            Render2D.drawVerticalLine(e.getMatrixStack(), startX - 1, yStart - 2, yStart + 12, ColorUtils.SELECT);
            Render2D.drawHorizontalLine(e.getMatrixStack(), startX - 1, Render2D.getScaledWidth(), yStart + 12, ColorUtils.SELECT);

            FontUtils.drawStringWithShadow(e.getMatrixStack(), module.getDisplayName(), startX + 3, yStart, ColorUtils.SELECT);

            yStart += 13;
        }
    }

    @EventTarget
    public void onTabGUI(EventRender2D e) {
        if (!this.tabGUI.isToggle()) {
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
                FontUtils.drawStringWithShadow(e.getMatrixStack(), m.getName() + (FoxBase.instance.settingManager.getSettingsForModule(m) != null ? ">" : ""), startModsX + 2 + (this.getCurrentModule().equals(m) ? 2 : 0),
                        startModsY + 2, m.isToggle() ? -1 : Color.GRAY.getRGB());
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
                if (s.isBoolean()) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + s.isToggle(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s.isValueInt()) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + s.getCurrentValueInt(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s.isValueDouble()) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + s.getCurrentValueDouble(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s.isValueFloat()) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + s.getCurrentValueFloat(),
                            startSettingX + 2 + (this.getCurrentSetting().equals(s) ? 2 : 0), startSettingY + 2,
                            editMode && this.getCurrentSetting().equals(s) ? -1 : Color.GRAY.getRGB());
                } else if (s.isMode()) {
                    FontUtils.drawStringWithShadow(e.getMatrixStack(), s.getName() + ": " + s.getCurrentMode(),
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
            if (s.isBoolean()) {
                s.setToggle(!s.isToggle());
            } else if (s.isValueInt()) {
                s.setCurrentValueInt(s.getCurrentValueInt() + 1);
            } else if (s.isValueDouble()) {
                s.setCurrentValueDouble(s.getCurrentValueDouble() + 0.1D);
            } else if (s.isValueFloat()) {
                s.setCurrentValueFloat(s.getCurrentValueFloat() + 0.1F);
            } else {
                try {
                    s.setCurrentMode(s.getModes().get(s.getCurrentModeIndex() - 1));
                } catch (Exception e) {
                    s.setCurrentMode(s.getModes().get(s.getModes().size() - 1));
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
            if (s.isBoolean()) {
                s.setToggle(!s.isToggle());
            } else if (s.isValueInt()) {
                s.setCurrentValueInt(s.getCurrentValueInt() - 1);
            } else if (s.isValueDouble()) {
                s.setCurrentValueDouble(s.getCurrentValueDouble() - 0.1D);
            } else if (s.isValueFloat()) {
                s.setCurrentValueFloat(s.getCurrentValueFloat() - 0.1F);
            } else {
                try {
                    s.setCurrentMode(s.getModes().get(s.getCurrentModeIndex() + 1));
                } catch (Exception e) {
                    s.setCurrentMode(s.getModes().get(0));
                }

            }
        }
    }


    private void right(int key) {
        if (this.screen == 0) {
            this.screen = 1;
        } else if (this.screen == 1 && this.getCurrentModule() != null && this.getSettingForCurrentMod() == null) {
            this.getCurrentModule().toggle();
        } else if (this.screen == 1 && this.getSettingForCurrentMod() != null && this.getCurrentModule() != null && key == GLFW.GLFW_KEY_ENTER) {
            this.getCurrentModule().toggle();
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

    @EventTarget
    public void onKey(EventKeyboard e) {

        if (e.getAction() != GLFW.GLFW_PRESS) {
            return;
        }

        Screen screen = mc.currentScreen;
        if (screen != null) {
            return;
        }

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
        return FoxBase.instance.settingManager.getSettingsForModule(getCurrentModule());
    }

    private Category getCurrentCategory() {
        return this.categoryValues.get(this.currentCategoryIndex);
    }

    private Module getCurrentModule() {
        return getModsForCurrentCategory().get(currentModIndex);
    }

    private ArrayList<Module> getModsForCurrentCategory() {
        return FoxBase.instance.moduleManager.getModulesForCategory(getCurrentCategory());
    }

    private int getWidestSetting() {
        int width = 0;
        for (Setting s : getSettingForCurrentMod()) {
            String name;
            if (s.isBoolean()) {
                name = s.getName() + ": " + s.isToggle();
            } else if (s.isValueInt()) {
                name = s.getName() + ": " + s.getCurrentValueInt();
            } else if (s.isValueDouble()) {
                name = s.getName() + ": " + s.getCurrentValueDouble();
            } else if (s.isValueFloat()) {
                name = s.getName() + ": " + s.getCurrentValueFloat();
            } else {
                name = s.getName() + ": " + s.getCurrentMode();
            }
            if (FontUtils.getStringWidth(name) > width) {
                width = FontUtils.getStringWidth(name);
            }
        }
        return width;
    }

    private int getWidestMod() {
        int width = 0;
        for (Module m : FoxBase.instance.moduleManager.getModules()) {
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
                    name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase());
            if (cWidth > width) {
                width = cWidth;
            }
        }
        return width;
    }


}
