package cn.enaium.foxbase.client;

import cn.enaium.cf4m.CF4M;

public enum FoxBase {

    instance;

    public String name = "FoxBase";
    public String author = "Enaium";
    public String version = "1.0";
    public String game = "1.16.5";

    public void run() {
        CF4M.run(this);
    }
}
