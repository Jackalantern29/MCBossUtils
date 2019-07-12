package com.Jackalantern29.MCBossUtils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class MCBConfig {
	static File file = new File(Main.getInstance().getDataFolder() + "/config.yml");
	static String prefix = "§8[§6MCBossUtils§8] §r";
	public static YamlConfiguration getConfig() {
		return YamlConfiguration.loadConfiguration(file);
	}
	public File getFile() {
		return file;
	}
	public static String getMessage(String key) {
		return getPrefix() + getConfig().getString("Message." + key).replace("&", "§");
	}
	public static Object getDefaultEnderDragon(String object) {
		return getConfig().get("DefaultEnderDragon." + object);
	}
	public static Object getDefaultWither(String object) {
		return getConfig().get("DefaultWither." + object);
	}
	public static String getPrefix() {
		return prefix;
	}
}
