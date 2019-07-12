package com.Jackalantern29.MCBossUtils;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.metadata.FixedMetadataValue;

public class EnderDragonInventories implements Listener {
	Player player;
	MCBEnderDragon dragon;
	Inventory inventory;
	public EnderDragonInventories() {
		
	}
	public EnderDragonInventories(Player player, MCBEnderDragon dragon) {
		this.player = player;
		this.dragon = dragon;
	}
	
	public void openWandMenu() {
		inventory = Bukkit.createInventory(null, 9, "Ender Dragon Menu");
		inventory.setItem(0, new ItemOptions(Material.DRAGON_HEAD, 1).setItemName(dragon.getCustomName()).setLore("§eUUID: §f" + dragon.getUniqueId().toString() + "\n§cMax HP: §f" + dragon.getMaxHealth() + "\n§cCurrent HP: §f" + dragon.getHealth() + "\n§aColor: §f" + dragon.getGlowColor() + StringUtils.capitalize(dragon.getGlowColor().name().toLowerCase()).replace("Reset", "None")).create());
		inventory.setItem(1, dragon.hasAI() ? new ItemOptions(Material.REDSTONE_TORCH, 1).setItemName("§eToggle AI").setLore("§aStatus: §cOn").create() : new ItemOptions(Material.LEVER, 1).setItemName("§eToggle AI").setLore("§aStatus: §cOff").create());
		inventory.setItem(2, new ItemOptions(Material.valueOf((dragon.getGlowColor().name().replace("DARK_AQUA", "CYAN").replace("DARK_BLUE", "BLUE").replace("AQUA", "LIGHT_BLUE").replace("INK_SAC", "BLACK").replace("DARK_GRAY", "GRAY").replace("GREEN", "LIME").replace("DARK_LIME", "GREEN").replace("DARK_PURPLE", "PURPLE").replace("DARK_RED", "RED").replace("GOLD", "ORANGE").replace("GRAY", "LIGHT_GRAY").replace("LIGHT_PURPLE", "MAGENTA") + "_WOOL").replace("RESET_WOOL", "BARRIER")), 1).setItemName("§eSet Glow Color").setLore("§aColor: §f" + dragon.getGlowColor() + StringUtils.capitalize(dragon.getGlowColor().name().toLowerCase()).replace("Reset", "None")).create());
		inventory.setItem(3, new ItemOptions(Material.APPLE, 1).setItemName("§eSet Max HP").setLore("§cMax HP: §f" + dragon.getMaxHealth()).create());
		inventory.setItem(4, new ItemOptions(Material.IRON_SWORD, 1).setItemName("§eConfigure Boss Bar").setLore("§7Title§8: §f" + dragon.getBarTitle().replace("&", "§") + "\n§7Color§8: §f" + ChatColor.valueOf(dragon.getBarColor().name().replace("PURPLE", "DARK_PURPLE").replace("PINK", "LIGHT_PURPLE")) + StringUtils.capitalize(dragon.getBarColor().name().toLowerCase()) + "\n§7Style§8: §a" + StringUtils.capitalize(dragon.getBarStyle().name().toLowerCase()).replace("_", " ")).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).create());
		//inventory.setItem(5, new ItemOptions(Material.SADDLE, 1).create());
		inventory.setItem(5, new ItemOptions(Material.PAPER, 1).setItemName("§eSet Ender Dragon Name").setLore("§fCurrent Name§7: §f" + dragon.getCustomName()).create());
		StringBuilder builder = new StringBuilder();
		for(int ints : dragon.getXPDrops()) {
			if(builder.toString().length() > 0)
				builder.append("\n");
			builder.append("§a- " + ints + " XP");
		}
		inventory.setItem(6, new ItemOptions(Material.EXPERIENCE_BOTTLE, 1).setItemName("§eConfigure Exp. Drops").setLore(builder.toString()).create());
		//inventory.setItem(6, new ItemOptions(Material.BARRIER, 1).create());
		player.openInventory(inventory);
	}
	public void openColorMenu(Player player, MCBEnderDragon dragon) {
		inventory = Bukkit.createInventory(null, 18, "Dragon Glow Color");
		inventory.setItem(0, new ItemOptions(Material.DRAGON_HEAD, 1).setItemName(dragon.getCustomName()).setLore("§eUUID: §f" + dragon.getUniqueId().toString() + "\n§cMax HP: §f" + dragon.getMaxHealth() + "\n§aColor: §f" + dragon.getGlowColor() + StringUtils.capitalize(dragon.getGlowColor().name().toLowerCase()).replace("Reset", "None")).create());	
		inventory.setItem(1, new ItemOptions(Material.LIGHT_BLUE_DYE, 1).setItemName(ChatColor.AQUA + "Aqua").create());
		inventory.setItem(2, new ItemOptions(Material.INK_SAC, 1).setItemName(ChatColor.BLACK + "Black").create());
		inventory.setItem(3, new ItemOptions(Material.LAPIS_LAZULI, 1).setItemName(ChatColor.BLUE + "Blue").create());
		inventory.setItem(4, new ItemOptions(Material.CYAN_DYE, 1).setItemName(ChatColor.DARK_AQUA + "Dark Aqua").create());
		inventory.setItem(5, new ItemOptions(Material.LAPIS_LAZULI, 1).setItemName(ChatColor.DARK_BLUE + "Dark Blue").create());
		inventory.setItem(6, new ItemOptions(Material.GRAY_DYE, 1).setItemName(ChatColor.DARK_GRAY + "Dark Gray").create());
		inventory.setItem(7, new ItemOptions(Material.CACTUS_GREEN, 1).setItemName(ChatColor.DARK_GREEN + "Dark Green").create());
		inventory.setItem(8, new ItemOptions(Material.PURPLE_DYE, 1).setItemName(ChatColor.DARK_PURPLE + "Dark Purple").create());
		inventory.setItem(9, new ItemOptions(Material.ROSE_RED, 1).setItemName(ChatColor.DARK_RED + "Dark Red").create());
		inventory.setItem(10, new ItemOptions(Material.ORANGE_DYE, 1).setItemName(ChatColor.GOLD + "Gold").create());
		inventory.setItem(11, new ItemOptions(Material.LIGHT_GRAY_DYE, 1).setItemName(ChatColor.GRAY + "Gray").create());
		inventory.setItem(12, new ItemOptions(Material.LIME_DYE, 1).setItemName(ChatColor.GREEN + "Green").create());
		inventory.setItem(13, new ItemOptions(Material.MAGENTA_DYE, 1).setItemName(ChatColor.LIGHT_PURPLE + "Light Purple").create());
		inventory.setItem(14, new ItemOptions(Material.ROSE_RED, 1).setItemName(ChatColor.RED + "Red").create());
		inventory.setItem(15, new ItemOptions(Material.BONE_MEAL, 1).setItemName(ChatColor.WHITE + "White").create());
		inventory.setItem(16, new ItemOptions(Material.DANDELION_YELLOW, 1).setItemName(ChatColor.YELLOW + "Yellow").create());
		inventory.setItem(17, new ItemOptions(Material.BARRIER, 1).setItemName(ChatColor.RESET + "Reset").create());
		player.openInventory(inventory);
	}
	public void openBarMenu(Player player, MCBEnderDragon dragon) {
		inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Dragon Boss Bar Menu");
		inventory.setItem(0, new ItemOptions(Material.PAPER, 1).setItemName("§eSet Title").setLore("§fCurrent Title§7: " + dragon.getBarTitle().replace("&", "§") + "\n§7§oClick to edit boss bar title.").create());
		inventory.setItem(1, new ItemOptions(Material.valueOf(dragon.getBarColor().name().replace("BLUE", "LAPIS_LAZULI").replace("GREEN", "LIME_DYE").replace("PINK", "PINK_DYE").replace("PURPLE", "PURPLE_DYE").replace("RED", "ROSE_RED").replace("WHITE", "BONE_MEAL").replace("YELLOW", "DANDELION_YELLOW")), 1).setItemName("§eSet Bar Color").setLore("§7Color§8: §f" + ChatColor.valueOf(dragon.getBarColor().name().replace("PURPLE", "DARK_PURPLE").replace("PINK", "LIGHT_PURPLE")) + StringUtils.capitalize(dragon.getBarColor().name().toLowerCase()) + "\n§7Style§8: §a" + StringUtils.capitalize(dragon.getBarStyle().name().toLowerCase()).replace("_", " ")).create());
		inventory.setItem(2, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eSet Style").setLore("§fStyle§7: §e" + StringUtils.capitalize(dragon.getBarStyle().name().toLowerCase().replace("_", " "))).create());
		inventory.setItem(3, new ItemOptions(Material.PURPLE_BANNER, 1).setItemName("§eToggle Flags").setLore("§fCreate Fog§7: " + (dragon.getBarFlag(BarFlag.CREATE_FOG) ? "§aTrue" : "§cFalse") + "\n§fDarken Sky§7: " + (dragon.getBarFlag(BarFlag.DARKEN_SKY) ? "§aTrue" : "§cFalse") + "\n§fPlay Boss Music§7: " + (dragon.getBarFlag(BarFlag.PLAY_BOSS_MUSIC) ? "§aTrue" : "§cFalse")).create());
		inventory.setItem(4, new ItemOptions(dragon.isBarVisible() ? Material.ENDER_EYE : Material.ENDER_PEARL, 1).setItemName("§eToggle Bar").setLore("§fStatus§7: " + (dragon.isBarVisible() ? "§aTrue" : "§cFalse")).create());
		player.openInventory(inventory);
	}
	public void openBarColorMenu(Player player, MCBEnderDragon dragon) {
		inventory = Bukkit.createInventory(null, 9, "Dragon Boss Bar Color Menu");
		inventory.setItem(1, new ItemOptions(Material.LAPIS_LAZULI, 1).setItemName("§9Blue").create());
		inventory.setItem(2, new ItemOptions(Material.LIME_DYE, 1).setItemName("§aGreen").create());
		inventory.setItem(3, new ItemOptions(Material.PINK_DYE, 1).setItemName("§dPink").create());
		inventory.setItem(4, new ItemOptions(Material.PURPLE_DYE, 1).setItemName("§5Purple").create());
		inventory.setItem(5, new ItemOptions(Material.ROSE_RED, 1).setItemName("§cRed").create());
		inventory.setItem(6, new ItemOptions(Material.BONE_MEAL, 1).setItemName("§fWhite").create());
		inventory.setItem(7, new ItemOptions(Material.DANDELION_YELLOW, 1).setItemName("§eYellow").create());
		player.openInventory(inventory);
	}
	public void openBarStyleMenu(Player player, MCBEnderDragon dragon) {
		inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Dragon Boss Bar Style Menu");
		inventory.setItem(0, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eSolid").create());
		inventory.setItem(1, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eSegmented 6").create());
		inventory.setItem(2, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eSegmented 10").create());
		inventory.setItem(3, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eSegmented 12").create());
		inventory.setItem(4, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eSegmented 20").create());
		player.openInventory(inventory);
	}
	public void openBarFlagMenu(Player player, MCBEnderDragon dragon) {
		inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Dragon Boss Bar Flag Menu");
		inventory.setItem(1, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eCreate Fog").setLore("§fStatus§7: "  + (dragon.getBarFlag(BarFlag.CREATE_FOG) ? "§aTrue" : "§cFalse")).create());
		inventory.setItem(2, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§eDarken Sky").setLore("§fStatus§7: "  + (dragon.getBarFlag(BarFlag.DARKEN_SKY) ? "§aTrue" : "§cFalse")).create());
		inventory.setItem(3, new ItemOptions(Material.NETHER_STAR, 1).setItemName("§ePlay Boss Music").setLore("§fStatus§7: "  + (dragon.getBarFlag(BarFlag.PLAY_BOSS_MUSIC) ? "§aTrue" : "§cFalse")).create());
		player.openInventory(inventory);
	}
	public void openXPMenu(Player player, MCBEnderDragon dragon) {
		inventory = Bukkit.createInventory(null, 54, "Dragon Configure Exp. Drop");
		int count = 0;
		for(int ints : dragon.getXPDrops()) {
			if(count <= 53) {
				inventory.setItem(count, new ItemOptions(Material.EXPERIENCE_BOTTLE, 1).setItemName("§a" + ints + " XP").setLore("§7§oRight click to remove.\n§7§oShift+Right click to edit value."/*\n§7§oLeft click to edit Exp."*/).create());
				count++;
			}
		}
		player.openInventory(inventory);
	}
	
	public void openEduMenu() {
		
	}
	
	@EventHandler
	public void onEInventoryInteract(InventoryClickEvent event) {
		if(event.getView().getTitle().equals("Ender Dragon Menu")) {
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).replace("§eUUID: §f", ""))));
			event.setCancelled(true);
			int slot = event.getRawSlot();
			//EnderDragon dragon = (EnderDragon) Bukkit.getEntity(UUID.fromString(event.getInventory().getItem(0).getItemMeta().getLore().get(0).replace("§eUUID: §f", "")));
			Player player = (Player) event.getWhoClicked();
			if(slot == 1) {
				if(dragon.hasAI()) {
					dragon.setAI(false);
					event.getInventory().setItem(1, new ItemOptions(Material.LEVER, 1).setItemName("§eToggle AI").setLore("§aStatus: §cOff").create());
				} else {
					dragon.setAI(true);
					event.getInventory().setItem(1, new ItemOptions(Material.REDSTONE_TORCH, 1).setItemName("§eToggle AI").setLore("§aStatus: §cOn").create());
				}
				String dragonAIToggle = MCBConfig.getMessage("dragonAIToggle");
				String replacer = "";
				String replace = "";
				if(dragonAIToggle.contains("%result%")) {
					replacer = dragon.hasAI() + "".toLowerCase();
					replace = "%result%";
				}
				if(dragonAIToggle.split(replace)[0].endsWith("{C}")) {
					replace = "{C}" + replace;
					replacer = StringUtils.capitalize(replacer);
				} else if(dragonAIToggle.split(replace)[0].endsWith("{CC}")) {
					replace = "{CC}" + replace;
					replacer = replacer.toUpperCase();
				}
				if(dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].charAt(0) == '(' && dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].charAt(8)  == ')' && dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9).startsWith("(§")
						&& dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9).endsWith(")")
						&& dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9).contains("|%|")
						&& "abcdefklmno0123456789".indexOf(dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9).charAt(2)) >= 0 
						&& "abcdefklmno0123456789".indexOf(dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9).charAt(7)) >= 0) {
					replacer = "§" + (replacer.toLowerCase().equals("true") ? dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9).charAt(2) : dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9).charAt(7)) + replacer;
					replace = replace + dragonAIToggle.split(replace.replace("{C}", "").replace("{CC}", ""))[1].substring(0, 9);
				}
				player.sendMessage(dragonAIToggle.replace(replace, replacer));
				
			}
			if(slot == 2) {
				openColorMenu(player, dragon);
			}
			if(slot == 3) {
				if(!player.hasMetadata("configuringDragonHealth"))
					player.setMetadata("configuringDragonHealth", new FixedMetadataValue(Main.getInstance(), dragon.getUniqueId()));
				player.closeInventory();
				player.sendMessage(MCBConfig.getMessage("typeIntegerDragonHealth"));
			}
			if(slot == 4) {
				openBarMenu(player, dragon);
			}
			if(slot == 5) {
				if(!player.hasMetadata("configuringDragonName"))
					player.setMetadata("configuringDragonName", new FixedMetadataValue(Main.getInstance(), dragon.getUniqueId()));
				player.closeInventory();
				player.sendMessage(MCBConfig.getMessage("typeStringDragonName"));
			}
			if(slot == 6) {
				openXPMenu(player, dragon);
			}
		}
		if(event.getView().getTitle().equals("Dragon Glow Color")) {
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).replace("§eUUID: §f", ""))));
			event.setCancelled(true);
			int slot = event.getSlot();
			if(event.getClickedInventory() == event.getInventory())
				if(EnumUtils.isValidEnum(ChatColor.class, ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().replace(" ", "_").toUpperCase())))
					dragon.setGlowColor(ChatColor.valueOf(ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().replace(" ", "_").toUpperCase())));
		}
		if(event.getView().getTitle().equals("Dragon Boss Bar Menu")) {
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).replace("§eUUID: §f", ""))));
			event.setCancelled(true);
			Player player = (Player) event.getWhoClicked();
			int slot = event.getRawSlot();
			if(slot == 0) {
				if(!player.hasMetadata("configuringDragonBossTitle"))
					player.setMetadata("configuringDragonBossTitle", new FixedMetadataValue(Main.getInstance(), dragon.getUniqueId()));
				player.closeInventory();
				player.sendMessage(MCBConfig.getMessage("typeStringDragonBarTitle"));
			}
			if(slot == 1) {
				openBarColorMenu(player, dragon);
			}
			if(slot == 2) {
				openBarStyleMenu(player, dragon);
			}
			if(slot == 3) {
				openBarFlagMenu(player, dragon);
			}
			if(slot == 4) {
				dragon.setBarVisible(dragon.isBarVisible() ? false : true);
				openBarMenu(player, dragon);
			}
		}
		if(event.getView().getTitle().equals("Dragon Boss Bar Color Menu")) {
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).replace("§eUUID: §f", ""))));
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if(event.getClickedInventory() == event.getInventory() && event.getInventory().getItem(slot) != null)
				if(EnumUtils.isValidEnum(BarColor.class, ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().toUpperCase())))
					dragon.setBarColor(BarColor.valueOf(ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().toUpperCase())));
		}
		if(event.getView().getTitle().equals("Dragon Boss Bar Style Menu")) {
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).replace("§eUUID: §f", ""))));
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if(event.getClickedInventory() == event.getInventory() && event.getInventory().getItem(slot) != null)
				if(EnumUtils.isValidEnum(BarStyle.class, ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().toUpperCase().replace(" ", "_"))))
					dragon.setBarStyle(BarStyle.valueOf(ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().toUpperCase().replace(" ", "_"))));
		}
		if(event.getView().getTitle().equals("Dragon Boss Bar Flag Menu")) {
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).replace("§eUUID: §f", ""))));
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if(event.getClickedInventory() == event.getInventory() && event.getInventory().getItem(slot) != null) {
				BarFlag flag = BarFlag.valueOf(ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().toUpperCase().replace(" ", "_")));
				if(EnumUtils.isValidEnum(BarFlag.class, ChatColor.stripColor(event.getInventory().getItem(slot).getItemMeta().getDisplayName().toUpperCase().replace(" ", "_")))) {
					dragon.setBarFlag(flag, dragon.getBarFlag(flag) ? false : true);
					openBarFlagMenu((Player) event.getWhoClicked(), dragon);
				}				
			}
		}
		if(event.getView().getTitle().equals("Dragon Configure Exp. Drop")) {
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(event.getWhoClicked().getInventory().getItemInMainHand().getItemMeta().getLore().get(1).replace("§eUUID: §f", ""))));
			int slot = event.getRawSlot();
			InventoryAction action = event.getAction();
			Player player = (Player) event.getWhoClicked();
			if(event.getClickedInventory() != event.getInventory())
				event.setCancelled(true);
			else {
				if(action == InventoryAction.NOTHING) {
					if(!player.hasMetadata("addingDragonXPSlot"))
						player.setMetadata("addingDragonXPSlot", new FixedMetadataValue(Main.getInstance(), dragon.getUniqueId()));
					player.closeInventory();
					player.sendMessage(MCBConfig.getMessage("typeIntegerAddDragonExpDrop"));
				}
				if(action == InventoryAction.PICKUP_ALL) {
				} else {
					if(action == InventoryAction.PICKUP_HALF) {
						event.setCancelled(true);
						List<Integer> xps = dragon.getXPDrops();
						xps.remove(slot);
						dragon.setXPDrops(xps);
						openXPMenu((Player) event.getWhoClicked(), dragon);
					}
					if(action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
						event.setCancelled(true);
						if(!player.hasMetadata("settingDragonXPSlot"))
							player.setMetadata("settingDragonXPSlot", new FixedMetadataValue(Main.getInstance(), dragon.getUniqueId().toString() + ":" + slot));
						player.closeInventory();
						player.sendMessage(MCBConfig.getMessage("typeIntegerSetDragonExpDrop"));
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if(player.hasMetadata("configuringDragonHealth")) {
			event.setCancelled(true);
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(player.getMetadata("configuringDragonHealth").get(0).asString())));
			try {
				dragon.setMaxHealth(Double.valueOf(event.getMessage()));
				player.sendMessage(MCBConfig.getMessage("dragonHealthSet").replace("%result%", dragon.getMaxHealth() + ""));
			} catch(NumberFormatException e) {
				player.sendMessage(MCBConfig.getMessage("errorValueNotInteger").replace("%value%", event.getMessage()));
			}
			player.removeMetadata("configuringDragonHealth", Main.getInstance());
		}
		if(player.hasMetadata("configuringDragonBossTitle")) {
			event.setCancelled(true);
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(player.getMetadata("configuringDragonBossTitle").get(0).asString())));
			dragon.setBarTitle(event.getMessage());
			player.removeMetadata("configuringDragonBossTitle", Main.getInstance());
			player.sendMessage(MCBConfig.getMessage("dragonBarTitleSet").replace("%result%", dragon.getBarTitle()).replace("&", "§"));
		}
		if(player.hasMetadata("configuringDragonName")) {
			event.setCancelled(true);
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(player.getMetadata("configuringDragonName").get(0).asString())));
			dragon.setCustomName(event.getMessage().equals("|reset|") ? null : event.getMessage());
			player.removeMetadata("configuringDragonName", Main.getInstance());
			player.sendMessage(MCBConfig.getMessage("dragonNameSet").replace("%result%", dragon.getCustomName()).replace("&", "§"));
		}
		if(player.hasMetadata("addingDragonXPSlot")) {
			event.setCancelled(true);
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(player.getMetadata("addingDragonXPSlot").get(0).asString())));
			List<Integer> xps = dragon.getXPDrops();
			try {
				xps.add(Integer.valueOf(event.getMessage()));
				dragon.setXPDrops(xps);
				player.sendMessage(MCBConfig.getMessage("dragonExpAdded").replace("%result%", event.getMessage()));
			} catch(NumberFormatException e) {
				player.sendMessage(MCBConfig.getMessage("errorValueNotInteger").replace("%value%", event.getMessage()));
			}
			player.removeMetadata("addingDragonXPSlot", Main.getInstance());
		}
		if(player.hasMetadata("settingDragonXPSlot")) {
			event.setCancelled(true);
			MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(UUID.fromString(player.getMetadata("settingDragonXPSlot").get(0).asString().split(":")[0])));
			List<Integer> xps = dragon.getXPDrops();
			try {
				xps.set(Integer.valueOf(player.getMetadata("settingDragonXPSlot").get(0).asString().split(":")[1]), Integer.valueOf(event.getMessage()));
				dragon.setXPDrops(xps);
				player.sendMessage(MCBConfig.getMessage("dragonExpSetSlot").replace("%result%", event.getMessage()).replace("%slot%", (Integer.valueOf(player.getMetadata("settingDragonXPSlot").get(0).asString().split(":")[1]) + 1) + ""));
			} catch(NumberFormatException e) {
				player.sendMessage(MCBConfig.getMessage("errorValueNotInteger"));
			}
			player.removeMetadata("settingDragonXPSlot", Main.getInstance());
		}
	}
}
