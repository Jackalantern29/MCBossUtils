package com.Jackalantern29.MCBossUtils.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.Jackalantern29.MCBossUtils.EnderDragonInventories;
import com.Jackalantern29.MCBossUtils.ItemOptions;
import com.Jackalantern29.MCBossUtils.MCBConfig;
import com.Jackalantern29.MCBossUtils.MCBEnderDragon;
import com.Jackalantern29.MCBossUtils.MCBWither;
import com.Jackalantern29.MCBossUtils.WitherInventories;

public class PlayerUseWandListener implements Listener {
	
	@EventHandler
	public void onUseWandRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		EnderDragon dragon = null;
		Wither wither = null;
		if(event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item.getType() == Material.STICK && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§5Ender Dragon Wand") && item.containsEnchantment(Enchantment.ARROW_DAMAGE) && item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == 99) {
				for(World worlds : Bukkit.getServer().getWorlds()) {
					for(Entity entities : worlds.getEntities()) {
						if(entities instanceof EnderDragon) {
							if(entities.getUniqueId().toString().equals(item.getItemMeta().getLore().get(1).replace("§eUUID: §f", "")))
								dragon = (EnderDragon) entities;
						}
					}
				}
				if(dragon != null) {
					new EnderDragonInventories(player, new MCBEnderDragon(dragon)).openWandMenu();
				} else
					player.sendMessage(MCBConfig.getMessage("wandCannotFindDragon"));
			}
			if(item.getType() == Material.BLAZE_ROD && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§8Wither Wand") && item.containsEnchantment(Enchantment.ARROW_DAMAGE) && item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == 99) {
				for(World worlds : Bukkit.getServer().getWorlds()) {
					for(Entity entities : worlds.getEntities()) {
						if(entities instanceof Wither) {
							if(entities.getUniqueId().toString().equals(item.getItemMeta().getLore().get(1).replace("§eUUID: §f", "")))
								wither = (Wither) entities;
						}
					}
				}
				if(wither != null) {
					new WitherInventories(player, new MCBWither(wither)).openWandMenu();
				} else
					player.sendMessage(MCBConfig.getMessage("wandCannotFindWither"));
			}
		}
	}
	
	@EventHandler
	public void onEntityLeftClick(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			Entity entity = event.getEntity();
			if(event.getCause() == DamageCause.ENTITY_ATTACK) {
				if(entity instanceof EnderDragon) {
					ItemStack item = player.getInventory().getItemInMainHand();
					if(item.getType() == Material.STICK && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§5Ender Dragon Wand") && item.containsEnchantment(Enchantment.ARROW_DAMAGE) && item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == 99) {
						event.setCancelled(true);
						MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon)entity);
						player.getInventory().setItemInMainHand(new ItemOptions(Material.STICK, 1).setItemName("§5Ender Dragon Wand").setLore("§eCurrently Selected: §f" + dragon.getCustomName() + "\n§eUUID: §f" + dragon.getUniqueId() + "\n§dLeft click an Ender Dragon to select it.\n§dRight click the wand to open menu.").addEnchant(Enchantment.ARROW_DAMAGE, 99, true).addItemFlags(ItemFlag.HIDE_ENCHANTS).create());
						if(!dragon.doesDragonExistinConfig())
							dragon.setup();
						dragon.getBossBar().setVisible(false);
						player.sendMessage(MCBConfig.getMessage("wandDragonUpdated"));
					}
					if(item.getType() == Material.BLAZE_ROD && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§8Wither Wand") && item.containsEnchantment(Enchantment.ARROW_DAMAGE) && item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == 99) {
						event.setCancelled(true);
						MCBWither wither = new MCBWither((Wither)entity);
						player.getInventory().setItemInMainHand(new ItemOptions(Material.BLAZE_ROD, 1).setItemName("§8Wither Wand").setLore("§eCurrently Selected: §f" + wither.getCustomName() + "\n§eUUID: §f" + wither.getUniqueId() + "\n§dLeft click an Wither to select it.\n§dRight click the wand to open menu.").addEnchant(Enchantment.ARROW_DAMAGE, 99, true).addItemFlags(ItemFlag.HIDE_ENCHANTS).create());
						if(!wither.doesDragonExistinConfig())
							wither.setup();
						player.sendMessage(MCBConfig.getMessage("wandWitherUpdated"));
					}
				}
			}
		}
	}
}
