package com.Jackalantern29.MCBossUtils;

import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.inventory.ItemFlag;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandWitherUtils implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("witherutils")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					BaseComponent[] builder;
					TextComponent bossUtils = new TextComponent("§6MCBossUtils");
					bossUtils.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/mcbossutils.68681/"));
					bossUtils.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dVersion: §6" + Main.getInstance().getDescription().getVersion() + "\n§dAuthor: §6" + Main.getInstance().getDescription().getAuthors().get(0) + "\n§dAPI Version: §6" + Main.getInstance().getDescription().getAPIVersion()).create()));
					builder = new ComponentBuilder("§8[").append(bossUtils).append("§8]").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
					player.spigot().sendMessage(builder);
					
					TextComponent wand = new TextComponent("§d/" + label + " wand");
					wand.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dUse this wand to select an Wither created with this plugin.").create()));
					wand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wu wand"));
					builder = new ComponentBuilder(wand).append(" §8- §7Get a wand to attatch to a Wither.").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
					player.spigot().sendMessage(builder);
					
					TextComponent spawn = new TextComponent("§d/" + label + " spawn");
					spawn.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dSpawn an Wither. Use the wand to configure the Wither.").create()));
					spawn.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wu spawn"));
					builder = new ComponentBuilder(spawn).append(" §8- §7Spawn a Wither.").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
					player.spigot().sendMessage(builder);
					
					TextComponent removeallnear = new TextComponent("§d/" + label + " removeallnear");
					removeallnear.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dRemove all Withers currently around the player that executed this command.").create()));
					removeallnear.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wu removeallnear"));
					builder = new ComponentBuilder(removeallnear).append(" §8- §7Remove all nearby Withers.").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
					player.spigot().sendMessage(builder);
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
						player.getInventory().addItem(new ItemOptions(Material.BLAZE_ROD, 1).setItemName("§8Wither Wand").setLore("§eCurrently Selected: §f§onone\n§dLeft click an Wither to select it.").addEnchant(Enchantment.ARROW_DAMAGE, 99, true).addItemFlags(ItemFlag.HIDE_ENCHANTS).create());
						player.sendMessage("§aYou have been given a Wither Wand.");
						return true;
					} else if(args[0].equalsIgnoreCase("spawn")) {
						if(player.getWorld().getDifficulty() != Difficulty.PEACEFUL) {
							Wither wither = (Wither) player.getWorld().spawnEntity(player.getLocation(), EntityType.WITHER);
							MCBWither mcbWither = new MCBWither(wither);
							mcbWither.setup();
							
							player.getInventory().addItem(new ItemOptions(Material.BLAZE_ROD, 1).setItemName("§8Wither Wand").setLore("§eCurrently Selected: §f" + (wither.getCustomName() != null ? wither.getCustomName() : wither.getName()) + "\n§eUUID: §f" + wither.getUniqueId() + "\n§dLeft click an Wither to select it.\n§dRight click the wand to open menu.").addEnchant(Enchantment.ARROW_DAMAGE, 99, true).addItemFlags(ItemFlag.HIDE_ENCHANTS).create());
							player.sendMessage("§aYou have been given a Wither Wand.");							
						} else 
							player.sendMessage("§c§lERROR: §cCannot spawn Wither. The world you are trying to spawn a wither in has its difficulty set to peaceful.");
						return true;
					} else if(args[0].equalsIgnoreCase("removeallnear")) {
						for(Wither withers : player.getWorld().getEntitiesByClass(Wither.class)) {
							withers.remove();
						}
						return true;
					}
				}
			}
		}
		return false;
	}

}
