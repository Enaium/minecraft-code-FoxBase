package cn.enaium.foxbase.config.config;

import cn.enaium.foxbase.FoxBase;
import cn.enaium.foxbase.module.Module;
import cn.enaium.foxbase.config.Config;
import cn.enaium.foxbase.setting.Setting;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ModulesConfig extends Config {

    private final File directory = new File(MinecraftClient.getInstance().runDirectory.toString() + "/" + FoxBase.instance.name + "/" + "Modules");

    public ModulesConfig(Gson gson, File file) {
        super(gson, file);
    }

    @Override
    public void loadFile() throws IOException {

        makeDirecotry();


        for (Module module : FoxBase.instance.moduleManager.getModules()) {

            makeModuleFile(module);

            FileReader fr = new FileReader(getFile(module));

            JsonObject jsonObject = getGson().fromJson(fr, JsonObject.class);

            if (jsonObject == null) {
                fr.close();
                return;
            }


            if (jsonObject.has("toggle")) {
                if (Boolean.parseBoolean(jsonObject.get("toggle").getAsString())) {
                    module.toggle();
                }
            }


            if (jsonObject.has("key")) {
                module.setKeyCode(Integer.parseInt(jsonObject.get("key").getAsString()));
            }


            ArrayList<Setting> settings = FoxBase.instance.settingManager.getSettingsForModule(module);

            if (settings != null && jsonObject.has("settings")) {

                JsonArray jsonArray = (JsonArray) jsonObject.get("settings");

                jsonArray.forEach(jsonElement -> settings.stream().filter(setting -> jsonElement.getAsJsonObject().has(setting.getName()))
                        .forEach(setting -> {
                            if (setting.isBoolean()) {
                                setting.setToggle(jsonElement.getAsJsonObject().get(setting.getName()).getAsBoolean());
                            } else if (setting.isValueInt()) {
                                setting.setCurrentValueInt(jsonElement.getAsJsonObject().get(setting.getName()).getAsInt());
                            } else if (setting.isValueDouble()) {
                                setting.setCurrentValueDouble(jsonElement.getAsJsonObject().get(setting.getName()).getAsDouble());
                            } else if (setting.isValueFloat()) {
                                setting.setCurrentValueFloat(jsonElement.getAsJsonObject().get(setting.getName()).getAsFloat());
                            } else {
                                setting.setCurrentMode(jsonElement.getAsJsonObject().get(setting.getName()).getAsString());
                            }
                        }));
            }

            fr.close();

        }


    }

    @Override
    public void saveFile() throws IOException {
        makeDirecotry();

        for (Module module : FoxBase.instance.moduleManager.getModules()) {

            makeModuleFile(module);

            FileWriter fw = new FileWriter(getFile(module));


            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("toggle", module.isToggle());
            jsonObject.addProperty("key", module.getKeyCode());


            ArrayList<Setting> settings = FoxBase.instance.settingManager.getSettingsForModule(module);

            if (settings != null) {

                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObject1 = new JsonObject();

                settings.forEach(setting -> {
                    if (setting.isBoolean()) {
                        jsonObject1.addProperty(setting.getName(), setting.isToggle());
                    } else if (setting.isValueInt()) {
                        jsonObject1.addProperty(setting.getName(), setting.getCurrentValueInt());
                    } else if (setting.isValueDouble()) {
                        jsonObject1.addProperty(setting.getName(), setting.getCurrentValueDouble());
                    } else if (setting.isValueFloat()) {
                        jsonObject1.addProperty(setting.getName(), setting.getCurrentValueFloat());
                    } else {
                        jsonObject1.addProperty(setting.getName(), setting.getCurrentMode());
                    }
                });

                jsonArray.add(jsonObject1);
                jsonObject.add("settings", jsonArray);

            }

            fw.write(getGson().toJson(jsonObject));
            fw.close();

        }


    }

    private void makeDirecotry() {
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private void makeModuleFile(Module module) throws IOException {
        if (!getFile(module).exists()) {
            getFile(module).createNewFile();
        }
    }

    private File getFile(Module module) {
        return new File(directory, module.getName() + ".json");
    }

}
