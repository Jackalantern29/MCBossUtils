package com.Jackalantern29.MCBossUtils;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragon.Phase;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandEDragonUtils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("edragonutils")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					TextComponent component = new TextComponent("§6/" + label + " wand");
					component.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Use this wand to select an Ender Dragon.").create()));
					component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/edu wand"));
					player.spigot().sendMessage(component);
				} else {
					sender.sendMessage("/" + label + " wand");
					sender.sendMessage("/" + label + " spawn");
					sender.sendMessage("/" + label + " removeallnear");
				}
				return true;				
			} else if(args.length == 1) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					if(args[0].equalsIgnoreCase("wand")) {
						player.getInventory().addItem(new ItemOptions(Material.STICK, 1).setItemName("§5Ender Dragon Wand").setLore("§eCurrently Selected: §f§onone\n§dLeft click an Ender Dragon to select it.").addEnchant(Enchantment.ARROW_DAMAGE, 99, true).addItemFlags(ItemFlag.HIDE_ENCHANTS).create());
						player.sendMessage("§aYou have been given an Ender Dragon Wand.");
						return true;
					} else if(args[0].equalsIgnoreCase("spawn")) {
						EnderDragon dragon = (EnderDragon) player.getWorld().spawnEntity(player.getLocation(), EntityType.ENDER_DRAGON);
						MCBEnderDragon mcbDragon = new MCBEnderDragon(dragon);
						mcbDragon.setup();
						
						player.getInventory().addItem(new ItemOptions(Material.STICK, 1).setItemName("§5Ender Dragon Wand").setLore("§eCurrently Selected: §f" + (dragon.getCustomName() != null ? dragon.getCustomName() : dragon.getName()) + "\n§eUUID: §f" + dragon.getUniqueId() + "\n§dLeft click an Ender Dragon to select it.\n§dRight click the wand to open menu.").addEnchant(Enchantment.ARROW_DAMAGE, 99, true).addItemFlags(ItemFlag.HIDE_ENCHANTS).create());
						player.sendMessage("§aYou have been given an Ender Dragon Wand.");
						return true;
					} else if(args[0].equalsIgnoreCase("removeallnear")) {
						for(EnderDragon dragons : player.getWorld().getEntitiesByClass(EnderDragon.class)) {
							dragons.setPhase(Phase.DYING);
						}
						return true;
					}
				}
			}
		}
		return false;
	}

}
