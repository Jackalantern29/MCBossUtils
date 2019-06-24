package com.Jackalantern29.MCBossUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
		dragon.getBossBar().setVisible(false);
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).World", dragon.getWorld().getName());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Name", dragon.getCustomName() != null ? dragon.getCustomName() : dragon.getName());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Glow", ChatColor.RESET.name());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Health", dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Title", getCustomName().replace("§", "&"));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Color", dragon.getBossBar().getColor().name());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Style", dragon.getBossBar().getStyle().name());
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Visible", true);
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Flag.CREATE_FOG", dragon.getBossBar().hasFlag(BarFlag.CREATE_FOG));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Flag.DARKEN_SKY", dragon.getBossBar().hasFlag(BarFlag.DARKEN_SKY));
		config.set(dragon.getUniqueId().toString() + " (EnderDragon).Bar.Flag.PLAY_BOSS_MUSIC", dragon.getBossBar().hasFlag(BarFlag.PLAY_BOSS_MUSIC));
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
		return dragon.getCustomName() != null ? dragon.getCustomName() : "§d" + dragon.getName();
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
		return dragon.hasAI();
	}

	public void setAI(boolean ai) {
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
	public static Location getDragonSavedLocation(UUID uuid) {
		File file = new File(Main.getInstance().getDataFolder() + "/bosses.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return new Location(Bukkit.getWorld(config.getString(uuid + " (EnderDragon).Location.World")), config.getInt(uuid + " (EnderDragon).Location.X"), config.getInt(uuid + " (EnderDragon).Location.Y"), config.getInt(uuid + " (EnderDragon).Location.Z"));
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
}
