package com.Jackalantern29.MCBossUtils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemOptions {
	ItemStack item;
	public ItemOptions(Material type, int amount) {
		item = new ItemStack(type, amount);
	}
	public ItemOptions setItemName(String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return this;
	}
	public ItemOptions setLore(String lore) {
		ItemMeta meta = item.getItemMeta();
		List<String> sLore = new ArrayList<>();
		for(String strings : lore.split("\n")) {
			sLore.add(strings);
		}
		meta.setLore(sLore);
		item.setItemMeta(meta);
		return this;
	}
	public ItemOptions addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(ench, level, ignoreLevelRestriction);
		item.setItemMeta(meta);
		return this;
	}
	public ItemOptions addItemFlags(ItemFlag... itemFlags) {
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(itemFlags);
		item.setItemMeta(meta);
		return this;
	}
	public ItemStack create() {
		return item;
	}
}
