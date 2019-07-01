package com.Jackalantern29.MCBossUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import com.Jackalantern29.MCBossUtils.Listeners.EnderDragonListeners;
import com.Jackalantern29.MCBossUtils.Listeners.PlayerUseWandListener;

public class Main extends JavaPlugin implements Listener {
	private static Main plugin;
	public static boolean check = false;
	public static UUID uuid;
	public static HashMap<UUID, Location> uuidsLocs = new HashMap<>();
	@Override
	public void onEnable() {
		plugin = this;

		File configFile = new File(getDataFolder() + "/config.yml"); 
		if(!configFile.exists()) {
			saveDefaultConfig();			
		} else {
			
		}
		File bossFile = new File(getDataFolder() + "/bosses.yml");
		if(!bossFile.exists()) {
			try {
				bossFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		getCommand("edragonutils").setExecutor(new CommandEDragonUtils());
		getServer().getPluginManager().registerEvents(new PlayerUseWandListener(), this);
		getServer().getPluginManager().registerEvents(new EInventories(), this);
		getServer().getPluginManager().registerEvents(new EnderDragonListeners(), this);
		getServer().getPluginManager().registerEvents(this, this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {	
			
			@Override
			public void run() {
				for(Chunk chunks : Bukkit.getWorlds().get(2).getForceLoadedChunks())
					chunks.setForceLoaded(false);
				for(Player players : Bukkit.getOnlinePlayers()) {
					Scoreboard board = players.getScoreboard();
					if(board.getObjectives().isEmpty() && board.getTeams().isEmpty()) {
						board = Bukkit.getScoreboardManager().getNewScoreboard();
						players.setScoreboard(board);
					}
					for(ChatColor colors : ChatColor.values()) {
						if(colors != ChatColor.MAGIC || colors != ChatColor.BOLD || colors != ChatColor.STRIKETHROUGH || colors != ChatColor.UNDERLINE) {
							if(board.getTeam("MBU" + colors.name().toLowerCase()) == null) {
								board.registerNewTeam("MBU" + colors.name().toLowerCase());
								board.getTeam("MBU" + colors.name().toLowerCase()).setColor(colors);								
							}
						}
					}
					for(UUID uuids : MCBEnderDragon.listBosses()) {
						BossBar bar = Bukkit.getBossBar(NamespacedKey.minecraft(uuids.toString()));
						if(Bukkit.getEntity(uuids) != null) {
							MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon)Bukkit.getEntity(uuids));
							board.getTeam("MBU" + dragon.getGlowColor().name().toLowerCase()).addEntry(uuids.toString());
							dragon.setGlowing(dragon.getGlowColor() != ChatColor.RESET ? true : false);
							if(bar == null)
								bar = Bukkit.createBossBar(NamespacedKey.minecraft(uuids.toString()), dragon.getCustomName(), dragon.getBarColor(), dragon.getBarStyle(), BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY);
							bar.setProgress(dragon.getHealth() / dragon.getMaxHealth());
							bar.setTitle(dragon.getBarTitle().replace("&", "§"));
							bar.setColor(dragon.getBarColor());
							bar.setStyle(dragon.getBarStyle());
							bar.setVisible(dragon.isBarVisible());
							if(dragon.getBarFlag(BarFlag.CREATE_FOG) && !bar.hasFlag(BarFlag.CREATE_FOG)) bar.addFlag(BarFlag.CREATE_FOG);
							if(dragon.getBarFlag(BarFlag.DARKEN_SKY) && !bar.hasFlag(BarFlag.DARKEN_SKY)) bar.addFlag(BarFlag.DARKEN_SKY);
							if(dragon.getBarFlag(BarFlag.PLAY_BOSS_MUSIC) && !bar.hasFlag(BarFlag.PLAY_BOSS_MUSIC)) bar.addFlag(BarFlag.PLAY_BOSS_MUSIC);
							if(!dragon.getBarFlag(BarFlag.CREATE_FOG) && bar.hasFlag(BarFlag.CREATE_FOG)) bar.removeFlag(BarFlag.CREATE_FOG);
							if(!dragon.getBarFlag(BarFlag.DARKEN_SKY) && bar.hasFlag(BarFlag.DARKEN_SKY)) bar.removeFlag(BarFlag.DARKEN_SKY);
							if(!dragon.getBarFlag(BarFlag.PLAY_BOSS_MUSIC) && bar.hasFlag(BarFlag.PLAY_BOSS_MUSIC)) bar.removeFlag(BarFlag.PLAY_BOSS_MUSIC);
							dragon.dragonSetAI(dragon.hasAI());
//							if(dragon.getBossBar().isVisible()) {
//								bar.setVisible(false);
//							}
							if(players.getNearbyEntities(149, 256, 149).contains(Bukkit.getEntity(uuids)))
								bar.addPlayer(players);
							else
								bar.removePlayer(players);
							if(!uuidsLocs.containsKey(dragon.getUniqueId())) 
								uuidsLocs.put(dragon.getUniqueId(), dragon.getLocation());
							else
								uuidsLocs.replace(dragon.getUniqueId(), dragon.getLocation());
						} else {
							if(bar != null)
								bar.removeAll();
							Bukkit.removeBossBar(NamespacedKey.minecraft(uuids.toString()));
							if(uuidsLocs.containsKey(uuids)) {
								MCBEnderDragon.saveDragonCurrentLocation(uuids, uuidsLocs.get(uuids));
								uuidsLocs.remove(uuids);								
							}
						}
					}
				}
			}
		}, 0l, MCBConfig.getConfig().getInt("mainRepeatingScheduler"));
	}
	
	@Override
	public void onDisable() {
		if(uuidsLocs.keySet() != null)
			for(UUID uuids : uuidsLocs.keySet()) {
				uuidsLocs.get(uuids).getChunk().setForceLoaded(true);
				MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon)Bukkit.getEntity(uuids));
				dragon.dragonSetAI(false);
				MCBEnderDragon.saveDragonCurrentLocation(uuids, dragon.getLocation());
				uuidsLocs.get(uuids).getChunk().setForceLoaded(false);
			}
	}
	
	public static Main getInstance() {
		return plugin;
	}	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(check == false) {
			Player player = event.getPlayer();
			if(player.getWorld().getUID().equals(Bukkit.getWorlds().get(2).getUID())) {
				check = true;
				for(UUID uuids : MCBEnderDragon.listBosses()) {
					MCBEnderDragon.getDragonSavedLocation(uuids).getChunk().setForceLoaded(true);
					MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(uuids));
					if(Bukkit.getEntity(uuids) != null) {
						dragon.getDragon().teleport(new Location(Bukkit.getWorlds().get(0), 0, 250, 0));
						Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
							
							@Override
							public void run() {
								dragon.getDragon().teleport(new Location(Bukkit.getWorlds().get(2), 0, 100 + new Random().nextInt(50), 0));
								MCBEnderDragon.getDragonSavedLocation(uuids).getChunk().setForceLoaded(false);
							}
						}, 20l);						
					} else {
						Bukkit.getConsoleSender().sendMessage("");
						Bukkit.getConsoleSender().sendMessage("[MCBOSSUTILS] [ERROR] Could not find saved Ender Dragons in The End world.");
						Bukkit.getConsoleSender().sendMessage("");
					}
				}
			}
		}
	}
	@EventHandler
	public void onWorldEnter(PlayerPortalEvent event) {
		if(event.getTo().getWorld().getUID() == Bukkit.getWorlds().get(2).getUID()) {
			if(check == false) {
				Player player = event.getPlayer();
				if(player.getWorld().getUID().equals(Bukkit.getWorlds().get(2).getUID())) {
					check = true;
					for(UUID uuids : MCBEnderDragon.listBosses()) {
						MCBEnderDragon.getDragonSavedLocation(uuids).getChunk().setForceLoaded(true);
						MCBEnderDragon dragon = new MCBEnderDragon((EnderDragon) Bukkit.getEntity(uuids));
						if(Bukkit.getEntity(uuids) != null) {
							dragon.getDragon().teleport(new Location(Bukkit.getWorlds().get(0), 0, 250, 0));
							Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
								
								@Override
								public void run() {
									dragon.getDragon().teleport(new Location(Bukkit.getWorlds().get(2), 0, 100 + new Random().nextInt(50), 0));
									MCBEnderDragon.getDragonSavedLocation(uuids).getChunk().setForceLoaded(false);
								}
							}, 20l);						
						} else {
							Bukkit.getConsoleSender().sendMessage("");
							Bukkit.getConsoleSender().sendMessage("[MCBOSSUTILS] [ERROR] Could not find saved Ender Dragons in The End world.");
							Bukkit.getConsoleSender().sendMessage("");
						}
					}
				}
			}
		}
	}
}
