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
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragon.Phase;

public class MCBEnderDragon {
	EnderDragon dragon;
	File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
	YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public MCBEnderDragon(EnderDragon dragon) {
		this.dragon = dragon;
	}
	public boolean doesDragonExistinConfig() {
		for(String strings : config.getKeys(false)) {
			if(strings.contains("(EnderDragon)"))
				if(dragon.getUniqueId().equals(UUID.fromString(strings.replace(" (EnderDragon)", ""))))
					return true;
		}
		return false;
	}
	public void setup() {
		dragon.setPhase(Phase.CIRCLING);
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Name", ((String) MCBConfig.getDefaultEnderDragon("Name")).replace("&", "§"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).AI", MCBConfig.getDefaultEnderDragon("AI"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Glow", MCBConfig.getDefaultEnderDragon("Glow"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Health", MCBConfig.getDefaultEnderDragon("Health"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).XPDropRadius", MCBConfig.getDefaultEnderDragon("XPDropRadius"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).XPDrops", MCBConfig.getDefaultEnderDragon("XPDrops"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Title", ((String) MCBConfig.getDefaultEnderDragon("Bar.Title")).replace("&", "§"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Color", MCBConfig.getDefaultEnderDragon("Bar.Color"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Style", MCBConfig.getDefaultEnderDragon("Bar.Style"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Visible", MCBConfig.getDefaultEnderDragon("Bar.Visible"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Flag.CREATE_FOG", MCBConfig.getDefaultEnderDragon("Bar.Flag.CREATE_FOG"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Flag.DARKEN_SKY", MCBConfig.getDefaultEnderDragon("Bar.Flag.DARKEN_SKY"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Flag.PLAY_BOSS_MUSIC", MCBConfig.getDefaultEnderDragon("Bar.Flag.PLAY_BOSS_MUSIC"));
		
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Location.World", dragon.getWorld().getName());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Location.X", dragon.getLocation().getX());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Location.Y", dragon.getLocation().getY());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Location.Z", dragon.getLocation().getZ());

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getCustomName() {
		return dragon.getCustomName() != null ? dragon.getCustomName() : dragon.getName();
	}
	public void setCustomName(String name) {
		dragon.setCustomName(name.replace("&", "§"));
	}
	public UUID getUniqueId() {
		return dragon.getUniqueId();
	}
	public double getMaxHealth() {
		return dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
	}
	public void setMaxHealth(double value) {
		dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
	}
	public double getHealth() {
		return dragon.getHealth();
	}
	public ChatColor getGlowColor() {
		return ChatColor.valueOf(config.getString(dragon.getUniqueId().toString() + " (EnderDragon).Glow"));
	}
	public boolean isGlowing() {
		return dragon.isGlowing();
	}
	public void setGlowing(boolean flag) {
		dragon.setGlowing(flag);
	}
	public void setGlowColor(ChatColor color) {
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Glow", color.name());
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
		getBossConfig().set("Bar.Title", title);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarColor(BarColor color) {
		getBossConfig().set("Bar.Color", color.name());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarStyle(BarStyle style) {
		getBossConfig().set("Bar.Style", style.name());
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarFlag(BarFlag flag, boolean value) {
		getBossConfig().set("Bar.Flag." + flag.name(), value);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setBarVisible(boolean value) {
		getBossConfig().set("Bar.Visible", value);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean hasAI() {
		return config.getBoolean(dragon.getUniqueId().toString() + " (EnderDragon).AI");
	}

	public void setAI(boolean ai) {
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).AI", ai);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean dragonHasAI() {
		return dragon.hasAI();
	}
	public void dragonSetAI(boolean ai) {
		dragon.setAI(ai);
	}
	public BossBar getBossBar() {
		return dragon.getBossBar();
	}
	public ConfigurationSection getBossConfig() {
		return config.getConfigurationSection(dragon.getUniqueId().toString() + " (EnderDragon)");
	}
	public static List<UUID> listBosses() { 
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<UUID> list = new ArrayList<>();
		for(String strings : config.getKeys(false)) {
			if(strings.contains("(EnderDragon)"))
				list.add(UUID.fromString(strings.replace(" (EnderDragon)", "")));
		}
		return list;
	}
	public EnderDragon getDragon() {
		return dragon;
	}
	public Location getLocation() {
		return dragon.getLocation();
	}
	public List<Integer> getXPDrops() {
		return config.getIntegerList(dragon.getUniqueId().toString() + " (EnderDragon).XPDrops");
	}
	public int getXPDropRadius() {
		return config.getInt(dragon.getUniqueId().toString() + " (EnderDragon).XPDropRadius");
	}
	public void setXPDrops(List<Integer> xps) {
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).XPDrops", xps);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Location getDragonSavedLocation(UUID uuid) {
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return new Location(Bukkit.getWorld(config.getString(uuid + " (EnderDragon).Location.World")), config.getDouble(uuid + " (EnderDragon).Location.X"), config.getDouble(uuid + " (EnderDragon).Location.Y"), config.getDouble(uuid + " (EnderDragon).Location.Z"));
	}
	public static void saveDragonCurrentLocation(UUID uuid, Location loc) {
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set(uuid + " (EnderDragon).Location.World", loc.getWorld().getName());
		config.set(uuid + " (EnderDragon).Location.X", loc.getX());
		config.set(uuid + " (EnderDragon).Location.Y", loc.getY());
		config.set(uuid + " (EnderDragon).Location.Z", loc.getZ());
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
		config.set(uuid + " (EnderDragon)", null);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
