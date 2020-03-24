package cn.enaium.foxbase.setting;

import cn.enaium.foxbase.module.Module;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Setting {

    private Module module;
    private String name;
    private boolean toggle;
    private double currentValueDouble, minValueDouble, maxValueDouble;
    private int currentValueInt, minValueInt, maxValueInt;
    private float currentValueFloat, minValueFloat, maxValueFloat;
    private ArrayList<String> modes;
    private String currentMode;
    private Category category;

    public enum Category {
        BOOLEAN,
        VALUE_INT,
        VALUE_DOUBLE,
        VALUE_FLOAT,
        MODE;
    }

    public Setting(Module module, String name, boolean toggle) {
        this.module = module;
        this.name = name;
        this.toggle = toggle;
        this.category = Category.BOOLEAN;
    }

    public Setting(Module module, String name, int currentValueInt, int minValueInt, int maxValueInt) {
        this.module = module;
        this.name = name;
        this.currentValueInt = currentValueInt;
        this.minValueInt = minValueInt;
        this.maxValueInt = maxValueInt;
        this.category = Category.VALUE_INT;
    }

    public Setting(Module module, String name, double currentValueDouble, double minValueDouble, double maxValueDouble) {
        this.module = module;
        this.name = name;
        this.currentValueDouble = currentValueDouble;
        this.minValueDouble = minValueDouble;
        this.maxValueDouble = maxValueDouble;
        this.category = Category.VALUE_DOUBLE;
    }

    public Setting(Module module, String name, float currentValueFloat, float minValueFloat, float maxValueFloat) {
        this.module = module;
        this.name = name;
        this.currentValueFloat = currentValueFloat;
        this.minValueFloat = minValueFloat;
        this.maxValueFloat = maxValueFloat;
        this.category = Category.VALUE_FLOAT;
    }

    public Setting(Module module, String name, String currentMode, ArrayList<String> options) {
        this.module = module;
        this.name = name;
        this.currentMode = currentMode;
        this.modes = options;
        this.category = Category.MODE;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public double getCurrentValueDouble() {
        return currentValueDouble;
    }

    public void setCurrentValueDouble(double currentValueDouble) {
        this.currentValueDouble = valueFix(currentValueDouble);
    }

    public double getMinValueDouble() {
        return minValueDouble;
    }

    public void setMinValueDouble(double minValueDouble) {
        this.minValueDouble = minValueDouble;
    }

    public double getMaxValueDouble() {
        return maxValueDouble;
    }

    public void setMaxValueDouble(double maxValueDouble) {
        this.maxValueDouble = maxValueDouble;
    }

    public int getCurrentValueInt() {
        return currentValueInt;
    }

    public void setCurrentValueInt(int currentValueInt) {
        this.currentValueInt = currentValueInt;
    }

    public int getMinValueInt() {
        return minValueInt;
    }

    public void setMinValueInt(int minValueInt) {
        this.minValueInt = minValueInt;
    }

    public int getMaxValueInt() {
        return maxValueInt;
    }

    public void setMaxValueInt(int maxValueInt) {
        this.maxValueInt = maxValueInt;
    }

    public float getCurrentValueFloat() {
        return currentValueFloat;
    }

    public void setCurrentValueFloat(float currentValueFloat) {
        this.currentValueFloat = valueFix(currentValueFloat);
    }

    public float getMinValueFloat() {
        return minValueFloat;
    }

    public void setMinValueFloat(float minValueFloat) {
        this.minValueFloat = minValueFloat;
    }

    public float getMaxValueFloat() {
        return maxValueFloat;
    }

    public void setMaxValueFloat(float maxValueFloat) {
        this.maxValueFloat = maxValueFloat;
    }

    public ArrayList<String> getModes() {
        return modes;
    }

    public void setModes(ArrayList<String> modes) {
        this.modes = modes;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

    public int getCurrentModeIndex() {
        int index = 0;
        for (String s : modes) {
            if (s.equalsIgnoreCase(currentMode)) {
                return index;
            }
            index++;
        }
        return index;
    }

    public boolean isBoolean() {
        return this.category.equals(Category.BOOLEAN);
    }

    public boolean isValueInt() {
        return this.category.equals(Category.VALUE_INT);
    }

    public boolean isValueDouble() {
        return this.category.equals(Category.VALUE_DOUBLE);
    }

    public boolean isValueFloat() {
        return this.category.equals(Category.VALUE_FLOAT);
    }

    public boolean isValue() {
        return this.category.equals(Category.VALUE_INT) || this.category.equals(Category.VALUE_DOUBLE) || this.category.equals(Category.VALUE_FLOAT);
    }

    public boolean isMode() {
        return this.category.equals(Category.MODE);
    }

    public String getCategory() {
        if (isBoolean()) {
            return "Boolean";
        }
        if (isValueInt()) {
            return "Int";
        }
        if (isValueDouble()) {
            return "Double";
        }
        if (isValueFloat()) {
            return "Float";
        }
        if (isMode()) {
            return "String";
        }
        return "";
    }

    private float valueFix(float value) {
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        return Float.parseFloat(decimalFormat.format(value));
    }

    private double valueFix(double value) {
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        return Double.parseDouble(decimalFormat.format(value));
    }
}
