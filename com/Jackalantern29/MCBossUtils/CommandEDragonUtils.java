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

import net.md_5.bungee.api.chat.BaseComponent;
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
					BaseComponent[] builder;
					TextComponent bossUtils = new TextComponent("§6MCBossUtils");
					bossUtils.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/mcbossutils.68681/"));
					bossUtils.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dVersion: §6" + Main.getInstance().getDescription().getVersion() + "\n§dAuthor: §6" + Main.getInstance().getDescription().getAuthors().get(0) + "\n§dAPI Version: §6" + Main.getInstance().getDescription().getAPIVersion()).create()));
					builder = new ComponentBuilder("§8[").append(bossUtils).append("§8]").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
					player.spigot().sendMessage(builder);
					
					TextComponent wand = new TextComponent("§d/" + label + " wand");
					wand.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dUse this wand to select an Ender Dragon created with this plugin.").create()));
					wand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/edu wand"));
					builder = new ComponentBuilder(wand).append(" §8- §7Get a wand to attatch to a Dragon.").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
					player.spigot().sendMessage(builder);
					
					TextComponent spawn = new TextComponent("§d/" + label + " spawn");
					spawn.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dSpawn an Ender Dragon. Use the wand to configure the Ender Dragon.").create()));
					spawn.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/edu spawn"));
					builder = new ComponentBuilder(spawn).append(" §8- §7Spawn an Ender Dragon.").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
					player.spigot().sendMessage(builder);
					
					TextComponent removeallnear = new TextComponent("§d/" + label + " removeallnear");
					removeallnear.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("§dRemove all Ender Dragons currently around the player that executed this command.").create()));
					removeallnear.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/edu removeallnear"));
					builder = new ComponentBuilder(removeallnear).append(" §8- §7Remove all nearby Ender Dragons.").event(new ClickEvent(ClickEvent.Action.OPEN_FILE, null)).event(new HoverEvent(Action.SHOW_TEXT, null)).create();
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
							dragons.setAI(true);
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
