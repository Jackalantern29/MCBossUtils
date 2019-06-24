package com.Jackalantern29.MCBossUtils.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import com.Jackalantern29.MCBossUtils.EInventories;
import com.Jackalantern29.MCBossUtils.ItemOptions;
import com.Jackalantern29.MCBossUtils.MCBEnderDragon;

public class PlayerUseWandListener implements Listener {
	
	@EventHandler
	public void onUseWandRightClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		EnderDragon dragon = null;
		if(event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item.getType() == Material.STICK && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("�5Ender Dragon Wand") && item.containsEnchantment(Enchantment.ARROW_DAMAGE) && item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == 99) {
				for(World worlds : Bukkit.getServer().getWorlds()) {
					for(Entity entities : worlds.getEntities()) {
						if(entities instanceof EnderDragon) {
							if(entities.getUniqueId().toString().equals(item.getItemMeta().getLore().get(1).replace("�eUUID: �f", "")))
								dragon = (EnderDragon) entities;
						}
					}
				}
				if(dragon != null) {
					new EInventories(player, new MCBEnderDragon(dragon)).openWandMenu();
				} else
					player.sendMessage("�cCannot find Entity. Entity may no longer exist.");
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
					if(item.getType() == Material.STICK && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("�5Ender Dragon Wand") && item.containsEnchantment(Enchantment.ARROW_DAMAGE) && item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) == 99) {
						event.setCancelled(true);
						MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon)entity);
						player.getInventory().setItemInMainHand(new ItemOptions(Material.STICK, 1).setItemName("�5Ender Dragon Wand").setLore("�eCurrently Selected: �f" + dragon.getCustomName() + "\n�eUUID: �f" + dragon.getUniqueId() + "\n�dLeft click an Ender Dragon to select it.\n�dRight click the wand to open menu.").addEnchant(Enchantment.ARROW_DAMAGE, 99, true).addItemFlags(ItemFlag.HIDE_ENCHANTS).create());
						if(!dragon.doesDragonExistinConfig())
							dragon.setup();
						dragon.getBossBar().setVisible(false);
						player.sendMessage("�aYou have updated the Ender Dragon Wand.");
					}
				}
			}
		}
	}
}