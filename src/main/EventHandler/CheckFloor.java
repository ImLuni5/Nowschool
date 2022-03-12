package main.EventHandler;

import main.CMDHandler.GameHandler;
import main.Main;
import main.TimerHandler.Timer2;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CheckFloor implements Listener {

    public static Location location1;
    public static Location location2;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().getLocation().getY() >= 106.0f && e.getPlayer().getGameMode() != GameMode.SPECTATOR) {
            HandlerList.unregisterAll(Main.getPlugin(Main.class));
            Bukkit.getPluginManager().registerEvents(new main.EventHandler.EventHandler(), Main.getPlugin(Main.class));
            for (LivingEntity livingEntity : e.getPlayer().getWorld().getLivingEntities())
                if (livingEntity.getType() == EntityType.ZOMBIE) livingEntity.setHealth(0.0);
            Bukkit.getScheduler().cancelTasks(Main.getPlugin(Main.class));
            location1 = new Location(e.getPlayer().getWorld(), 35.0f, 106.0f, 22.0f);
            location2 = new Location(e.getPlayer().getWorld(), 47.0f, 105.0f, 25.0f);
            Bukkit.broadcast(Component.text(Main.index + "플레이어가 옥상으로 탈출했습니다. 플레이어 승쀍쫅?쀍쫅쀍뷁쫅쫅?"));
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                Bukkit.broadcast(Component.text(Main.index + "???: 제법이구나, 너희들. 이 수많은 좀비를 뚫고 옥상까지 올라오다니."));
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                    Bukkit.broadcast(Component.text(Main.index + "???: 근데 어쩌냐 내가 더 많은 좀비를 데려왔거든"));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                        Bukkit.broadcast(Component.text(Main.index + "윤귀남: §4빨리 죽는 것이 좋을거야. 어차피 너희들에겐 희망 따윈 없어."));
                        for (int i = 0; i < 100; i++) {
                            Zombie zombie = (Zombie) e.getPlayer().getWorld().spawnEntity(location1, EntityType.ZOMBIE, false);
                            Zombie zombie1 = (Zombie) e.getPlayer().getWorld().spawnEntity(location2, EntityType.ZOMBIE, false);
                            zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 2, true));
                            zombie1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 2, true));
                        }
                        GameHandler.remain = 200;
                        Bukkit.getPluginManager().registerEvents(new KillCount(), Main.getPlugin(Main.class));
                        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Timer2(), 0L, 20L);
                    }, 40L);
                }, 40L);
            }, 40L);
        }
    }

}
