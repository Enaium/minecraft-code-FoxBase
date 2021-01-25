package cn.enaium.foxbase.client.config;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.config.Config;
import cn.enaium.cf4m.config.ConfigAT;
import cn.enaium.cf4m.module.Module;
import cn.enaium.cf4m.CF4M;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@ConfigAT
public class ModuleConfig extends Config {
    public ModuleConfig() {
        super("Modules");
    }

    @Override
    public void load() {
        for (Module module : CF4M.getInstance().moduleManager.modules) {
            JsonArray jsonArray = new JsonArray();
            try {
                jsonArray = new Gson().fromJson(read(getPath()), JsonArray.class);
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (module.getName().equals(new Gson().fromJson(jsonObject, JsonObject.class).get("name").getAsString())) {
                    if (jsonObject.get("enable").getAsBoolean())
                        module.enable();
                    module.setKeyCode(jsonObject.get("keyCode").getAsInt());
                }
            }
        }
        super.load();
    }

    @Override
    public void save() {
        JsonArray jsonArray = new JsonArray();
        for (Module module : CF4M.getInstance().moduleManager.modules) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", module.getName());
            jsonObject.addProperty("enable", module.isEnable());
            jsonObject.addProperty("keyCode", module.getKeyCode());
            jsonArray.add(jsonObject);
        }
        try {
            write(getPath(), new Gson().toJson(jsonArray));
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        super.save();
    }

    private String read(String path) throws IOException {
        return FileUtils.readFileToString(new File(path));
    }

    private void write(String path, String string) throws IOException {
        FileUtils.writeStringToFile(new File(path), string, "UTF-8");
    }
}
