package main.EventHandler;

import main.CMDHandler.GameHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KillCount implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getType() == EntityType.ZOMBIE) {
            if (--GameHandler.remain <= 100) {
                for (int i = 0; i < 100; i++) {
                    Zombie zombie = (Zombie) e.getEntity().getWorld().spawnEntity(CheckFloor.location1, EntityType.ZOMBIE, false);
                    Zombie zombie1 = (Zombie) e.getEntity().getWorld().spawnEntity(CheckFloor.location2, EntityType.ZOMBIE, false);
                    zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 1));
                    zombie1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 1));
                }
                GameHandler.remain += 200;
            }
        }
    }

}
