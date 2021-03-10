package cn.enaium.foxbase;

import net.minecraft.client.Minecraft;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
public class Main {

    public static void main(String[] args) {
        String group = Main.class.getPackage().getImplementationVendor();
        String version = Main.class.getPackage().getImplementationVersion();
        String name = Main.class.getPackage().getImplementationTitle();
        String title = name + "-" + version;
        JFrame jFrame = new JFrame(title);
        jFrame.setBounds(100, 100, 450, 300);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        jFrame.setResizable(false);
        JLabel descriptionLabel = new JLabel(title);
        jFrame.add(descriptionLabel, BorderLayout.NORTH);
        JTextField pathTextField = new JTextField(getMinecraftFolder().getPath());
        jFrame.add(pathTextField, BorderLayout.SOUTH);
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/Enaium/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
        jFrame.add(homeButton, BorderLayout.WEST);
        JButton installButton = new JButton("Install " + title);
        installButton.addActionListener(event -> {
            File versionFolder = new File(pathTextField.getText(), "versions/" + title + "/");
            StringBuilder librariesPath = new StringBuilder(new File(pathTextField.getText(), "libraries/").getPath());
            for (String s : group.split("\\.")) {
                librariesPath.append("/").append(s);
                new File(librariesPath.toString()).mkdir();
            }
            librariesPath.append("/").append(name);
            new File(librariesPath.toString()).mkdir();
            librariesPath.append("/").append(version);
            new File(librariesPath.toString()).mkdir();
            versionFolder.mkdirs();
            File json = new File(versionFolder, title + ".json");
            File jar = new File(librariesPath.toString(), name + "-" + version + ".jar");
            try {
                Files.copy(Main.class.getResourceAsStream("/profile.json"), json.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toPath(), jar.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return;
            }
            JOptionPane.showMessageDialog(null, "SUCCESS", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        });
        jFrame.add(installButton, BorderLayout.CENTER);
        JButton aboutButton = new JButton("About");
        aboutButton.addActionListener(event -> JOptionPane.showMessageDialog(null, Main.class.getPackage().getImplementationTitle() + "-By:" + Main.class.getPackage().getSpecificationVendor()));
        jFrame.add(aboutButton, BorderLayout.EAST);
        jFrame.setVisible(true);
    }

    private static File getMinecraftFolder() {
        File minecraftFolder;
        if (getOsName().contains("win")) {
            minecraftFolder = new File(System.getenv("APPDATA") + "/.minecraft");
        } else if (getOsName().contains("mac")) {
            minecraftFolder = new File(System.getProperty("user.home") + "/Library/Application Support/minecraft");
        } else {
            minecraftFolder = new File(System.getProperty("user.home") + "/.minecraft");
        }
        return minecraftFolder;
    }

    private static String getOsName() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT);
    }
}