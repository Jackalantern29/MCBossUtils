package com.Jackalantern29.MCBossUtils.Listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderDragon.Phase;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.Jackalantern29.MCBossUtils.MCBEnderDragon;
import com.Jackalantern29.MCBossUtils.Main;

public class EnderDragonListeners implements Listener {
	@EventHandler
	public void onDragonDeath(EnderDragonChangePhaseEvent event) {
		final UUID uuid = event.getEntity().getUniqueId();
		MCBEnderDragon dragon = new MCBEnderDragon(event.getEntity());
		final List<Integer> xpDrops = dragon.getXPDrops();
		final int dropRadius = dragon.getXPDropRadius();
		if(MCBEnderDragon.listBosses().contains(uuid)) {
			if(event.getNewPhase() == Phase.DYING) {
				new BukkitRunnable() {
					@Override
					public void run() {
						if(event.getEntity().getPhase() != Phase.DYING) {
							this.cancel();
							return;
						}
						if(Bukkit.getEntity(uuid) == null) {
							for(ExperienceOrb orbs : event.getEntity().getWorld().getEntitiesByClass(ExperienceOrb.class)) {
								if(orbs.getLocation().getY() - event.getEntity().getLocation().getY() == 0.0) {
									orbs.remove();
									for(int xps : xpDrops) {
										int minX = event.getEntity().getLocation().clone().getBlockX() - dropRadius;
										int minZ = event.getEntity().getLocation().clone().getBlockZ() - dropRadius;
										int maxX = event.getEntity().getLocation().clone().getBlockX() + dropRadius;
										int maxZ = event.getEntity().getLocation().clone().getBlockZ() + dropRadius;
										
										double dx = Math.random() * (maxX - minX) + minX;
										double dz = Math.random() * (maxZ - minZ) + minZ;
										ExperienceOrb orb = (ExperienceOrb)event.getEntity().getWorld().spawn(new Location(event.getEntity().getWorld(), dx, event.getEntity().getLocation().getY(), dz), ExperienceOrb.class);
										orb.setExperience(xps);
									}
								}
							}
							MCBEnderDragon.removeBoss(uuid);
							Bukkit.removeBossBar(NamespacedKey.minecraft(uuid.toString()));
							this.cancel();
						}
					}
				}.runTaskTimer(Main.getInstance(), 0l, 1l);
			}
		}
	}
}
