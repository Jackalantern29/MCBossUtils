package com.Jackalantern29.MCBossUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Wither;

public class MCBWither {
	Wither wither;
	File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
	YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public MCBWither(Wither wither) {
		this.wither = wither;
	}
	public boolean doesDragonExistinConfig() {
		for(String strings : config.getKeys(false)) {
			if(strings.contains("(Wither)"))
				if(wither.getUniqueId().equals(UUID.fromString(strings.replace(" (Wither)", ""))))
					return true;
		}
		return false;
	}
	public void setup() {
		config.set(wither.getUniqueId().toString() + " (Wither).Name", ((String) MCBConfig.getDefaultWither("Name")).replace("&", "§"));
		wither.setCustomName(((String)MCBConfig.getDefaultWither("Name")).equals(wither.getName()) ? null : ((String)MCBConfig.getDefaultWither("Name")));
		config.set(wither.getUniqueId().toString() + " (Wither).AI", MCBConfig.getDefaultWither("AI"));
		config.set(wither.getUniqueId().toString() + " (Wither).Glow", MCBConfig.getDefaultWither("Glow"));
		config.set(wither.getUniqueId().toString() + " (Wither).Health", MCBConfig.getDefaultWither("Health"));
		this.setMaxHealth(Double.valueOf(MCBConfig.getDefaultWither("Health") + ""));
		wither.setHealth(Double.valueOf(MCBConfig.getDefaultWither("Health") + ""));
		config.set(wither.getUniqueId().toString() + " (Wither).XPDropRadius", MCBConfig.getDefaultWither("XPDropRadius"));
		config.set(wither.getUniqueId().toString() + " (Wither).XPDrops", MCBConfig.getDefaultWither("XPDrops"));
		config.set(wither.getUniqueId().toString() + " (Wither).Bar.Title", ((String) MCBConfig.getDefaultWither("Bar.Title")).replace("&", "§"));
		config.set(wither.getUniqueId().toString() + " (Wither).Bar.Color", MCBConfig.getDefaultWither("Bar.Color"));
		config.set(wither.getUniqueId().toString() + " (Wither).Bar.Style", MCBConfig.getDefaultWither("Bar.Style"));
		config.set(wither.getUniqueId().toString() + " (Wither).Bar.Visible", MCBConfig.getDefaultWither("Bar.Visible"));
		config.set(wither.getUniqueId().toString() + " (Wither).Bar.Flag.CREATE_FOG", MCBConfig.getDefaultWither("Bar.Flag.CREATE_FOG"));
		config.set(wither.getUniqueId().toString() + " (Wither).Bar.Flag.DARKEN_SKY", MCBConfig.getDefaultWither("Bar.Flag.DARKEN_SKY"));
		config.set(wither.getUniqueId().toString() + " (Wither).Bar.Flag.PLAY_BOSS_MUSIC", MCBConfig.getDefaultWither("Bar.Flag.PLAY_BOSS_MUSIC"));
		
		config.set(wither.getUniqueId().toString() + " (Wither).Location.World", wither.getWorld().getName());
		config.set(wither.getUniqueId().toString() + " (Wither).Location.X", wither.getLocation().getX());
		config.set(wither.getUniqueId().toString() + " (Wither).Location.Y", wither.getLocation().getY());
		config.set(wither.getUniqueId().toString() + " (Wither).Location.Z", wither.getLocation().getZ());

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getCustomName() {
		return wither.getCustomName() != null ? wither.getCustomName() : wither.getName();
	}
	public void setCustomName(String name) {
		wither.setCustomName(name.replace("&", "§"));
	}
	public UUID getUniqueId() {
		return wither.getUniqueId();
	}
	public double getMaxHealth() {
		return wither.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	}
	public void setMaxHealth(double value) {
		wither.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
	}
	public double getHealth() {
		return wither.getHealth();
	}
	public ChatColor getGlowColor() {
		return ChatColor.valueOf(config.getString(wither.getUniqueId().toString() + " (Wither).Glow"));
	}
	public boolean isGlowing() {
		return wither.isGlowing();
	}
	public void setGlowing(boolean flag) {
		wither.setGlowing(flag);
	}
	public void setGlowColor(ChatColor color) {
		config.set(wither.getUniqueId().toString() + " (Wither).Glow", color.name());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BarColor getBarColor() {
		return BarColor.valueOf(getBossConfig().getString("Bar.Color"));
	}
	public BarStyle getBarStyle() {
		return BarStyle.valueOf(getBossConfig().getString("Bar.Style"));
	}
	public String getBarTitle() {
		return getBossConfig().getString("Bar.Title");
	}
	public boolean isBarVisible() {
		return getBossConfig().getBoolean("Bar.Visible");
	}
	public boolean getBarFlag(BarFlag flag) {
		return getBossConfig().getBoolean("Bar.Flag." + flag.name());
	}
	public void setBarTitle(String title) {
		wither.getBossBar().setTitle(title.replace("&", "§"));
		getBossConfig().set("Bar.Title", title);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarColor(BarColor color) {
		wither.getBossBar().setColor(color);
		getBossConfig().set("Bar.Color", color.name());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarStyle(BarStyle style) {
		wither.getBossBar().setStyle(style);
		getBossConfig().set("Bar.Style", style.name());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarFlag(BarFlag flag, boolean value) {
		if(!wither.getBossBar().hasFlag(BarFlag.CREATE_FOG) && value) wither.getBossBar().addFlag(BarFlag.CREATE_FOG);
		if(!wither.getBossBar().hasFlag(BarFlag.DARKEN_SKY) && value) wither.getBossBar().addFlag(BarFlag.DARKEN_SKY);
		if(!wither.getBossBar().hasFlag(BarFlag.PLAY_BOSS_MUSIC) && value) wither.getBossBar().addFlag(BarFlag.PLAY_BOSS_MUSIC);
		if(wither.getBossBar().hasFlag(BarFlag.CREATE_FOG) && !value) wither.getBossBar().removeFlag(BarFlag.CREATE_FOG);
		if(wither.getBossBar().hasFlag(BarFlag.DARKEN_SKY) && !value) wither.getBossBar().removeFlag(BarFlag.DARKEN_SKY);
		if(wither.getBossBar().hasFlag(BarFlag.PLAY_BOSS_MUSIC) && !value) wither.getBossBar().removeFlag(BarFlag.PLAY_BOSS_MUSIC);
		getBossConfig().set("Bar.Flag." + flag.name(), value);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarVisible(boolean value) {
		wither.getBossBar().setVisible(value);
		getBossConfig().set("Bar.Visible", value);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean hasAI() {
		return config.getBoolean(wither.getUniqueId().toString() + " (Wither).AI");
	}

	public void setAI(boolean ai) {
		wither.setAI(ai);
		config.set(wither.getUniqueId().toString() + " (Wither).AI", ai);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BossBar getBossBar() {
		return wither.getBossBar();
	}
	public ConfigurationSection getBossConfig() {
		return config.getConfigurationSection(wither.getUniqueId().toString() + " (Wither)");
	}
	public static List<UUID> listBosses() { 
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<UUID> list = new ArrayList<>();
		for(String strings : config.getKeys(false)) {
			if(strings.contains("(Wither)"))
				list.add(UUID.fromString(strings.replace(" (Wither)", "")));
		}
		return list;
	}
	public Wither getDragon() {
		return wither;
	}
	public Location getLocation() {
		return wither.getLocation();
	}
	public List<Integer> getXPDrops() {
		return config.getIntegerList(wither.getUniqueId().toString() + " (Wither).XPDrops");
	}
	public int getXPDropRadius() {
		return config.getInt(wither.getUniqueId().toString() + " (Wither).XPDropRadius");
	}
	public void setXPDrops(List<Integer> xps) {
		config.set(wither.getUniqueId().toString() + " (Wither).XPDrops", xps);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Location getDragonSavedLocation(UUID uuid) {
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return new Location(Bukkit.getWorld(config.getString(uuid + " (Wither).Location.World")), config.getDouble(uuid + " (Wither).Location.X"), config.getDouble(uuid + " (Wither).Location.Y"), config.getDouble(uuid + " (Wither).Location.Z"));
	}
	public static void saveDragonCurrentLocation(UUID uuid, Location loc) {
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set(uuid + " (Wither).Location.World", loc.getWorld().getName());
		config.set(uuid + " (Wither).Location.X", loc.getX());
		config.set(uuid + " (Wither).Location.Y", loc.getY());
		config.set(uuid + " (Wither).Location.Z", loc.getZ());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void removeBoss(UUID uuid) {
		Bukkit.removeBossBar(NamespacedKey.minecraft(uuid.toString()));
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set(uuid + " (Wither)", null);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
