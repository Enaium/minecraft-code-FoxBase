package cn.enaium.foxbase.client.config;

import cn.enaium.cf4m.CF4M;
import cn.enaium.cf4m.annotation.config.Config;
import cn.enaium.cf4m.annotation.config.Load;
import cn.enaium.cf4m.annotation.config.Save;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Config("Modules")
public class ModuleConfig {
    @Load
    public void load() {
        for (Object module : CF4M.getInstance().module.getModules()) {
            JsonArray jsonArray = new JsonArray();
            try {
                jsonArray = new Gson().fromJson(read(CF4M.getInstance().config.getPath(this)), JsonArray.class);
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (CF4M.getInstance().module.getName(module).equals(new Gson().fromJson(jsonObject, JsonObject.class).get("name").getAsString())) {
                    if (jsonObject.get("enable").getAsBoolean()) {
                        CF4M.getInstance().module.enable(module);
                    }
                    CF4M.getInstance().module.setKey(module, jsonObject.get("key").getAsInt());
                }
            }
        }
    }

    @Save
    public void save() {
        JsonArray jsonArray = new JsonArray();
        for (Object module : CF4M.getInstance().module.getModules()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", CF4M.getInstance().module.getName(module));
            jsonObject.addProperty("enable", CF4M.getInstance().module.isEnable(module));
            jsonObject.addProperty("key", CF4M.getInstance().module.getKey(module));
            jsonArray.add(jsonObject);
        }
        try {
            write(CF4M.getInstance().config.getPath(this), new Gson().toJson(jsonArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read(String path) throws IOException {
        return FileUtils.readFileToString(new File(path));
    }

    private void write(String path, String string) throws IOException {
        FileUtils.writeStringToFile(new File(path), string, "UTF-8");
    }
}
