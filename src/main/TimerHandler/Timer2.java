package main.TimerHandler;

import main.CMDHandler.GameHandler;
import main.EventHandler.CancelDoor;
import main.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.HandlerList;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Timer2 implements Runnable {
    public static int count = 120;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendActionBar(Component.text("§0§l남은시간: §4§l" + count / 60 + "§0§l분 §4§l" + count % 60 + "§0§l초"));
        if (count == 0) {
            HandlerList.unregisterAll(Main.getPlugin(Main.class));
            Bukkit.getPluginManager().registerEvents(new main.EventHandler.EventHandler(), Main.getPlugin(Main.class));
            for (Player player : GameHandler.players) {
                if (player.getLocation().getY() >= 106.0f) {
                    for (LivingEntity livingEntity : player.getWorld().getLivingEntities())
                        if (livingEntity.getType() == EntityType.ZOMBIE) livingEntity.setHealth(0.0);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                        Bukkit.broadcast(Component.text(Main.index + "군인: 얘들아 괜찮니? 빨리 구조 헬기타고 가자."));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                            Bukkit.broadcast(Component.text(Main.index + "군인: 효산고 생존자 확인. 총 " + GameHandler.players.size() + "명"));
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                                if (!GameHandler.julB.isEmpty()) {
                                    Random random = new Random();
                                    if (random.nextInt(5) == random.nextInt(5)) {
                                        Bukkit.broadcast(Component.text(Main.index + "군인: 잠깐만.. 너희들중에서 무증상 감염자가 있네..? 미안하다.. 구출 하진 못하겠구나.."));
                                        Bukkit.broadcast(Component.text(Main.index + "플레이어는 절비인것을 들켜 탈출하지 못했습니다. 플레이어 패배"));
                                    } else {
                                        Bukkit.broadcast(Component.text(Main.index + "플레이어는 구조 헬기를 타고 탈출했습니다. 플레이어 승리"));
                                    }
                                } else {
                                    Bukkit.broadcast(Component.text(Main.index + "플레이어는 구조 헬기를 타고 탈출했습니다. 플레이어 승리"));
                                }
                                GameHandler.stopGame();
                            }, 40L);
                        }, 40L);
                    }, 40L);
                    return;
                }
            }
            for (LivingEntity livingEntity : Bukkit.getOnlinePlayers().iterator().next().getWorld().getLivingEntities())
                if (livingEntity.getType() == EntityType.ZOMBIE) livingEntity.setHealth(0.0f);
            Bukkit.getPluginManager().registerEvents(new CancelDoor(), Main.getPlugin(Main.class));
            Bukkit.broadcast(Component.text(Main.index + "구조 헬기가 왔으나 플레이어가 옥상에 있지 않아 발견하지 못하고 돌아갔습니다."));
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                Bukkit.broadcast(Component.text(Main.index + "윤귀남: 너희들 바보구나. 구조될 기회를 버리다니."));
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
                    Bukkit.broadcast(Component.text(Main.index + "윤귀남: 자자, 문은 다 잠궜고 이제 우리들만의 게임을 시작해볼까? 술래잡기 시작."));
                    GameHandler.zombie = (Zombie) Bukkit.getOnlinePlayers().iterator().next().getWorld().spawnEntity(new Location(Bukkit.getOnlinePlayers().iterator().next().getWorld(), 47.0f, 105.0f, 25.0f), EntityType.ZOMBIE, false);
                    GameHandler.zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 5, true));
                    GameHandler.zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 32767, 5, true));
                    GameHandler.zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(GameHandler.players.size() * 200.0f);
                    GameHandler.zombie.setCustomName("윤귀남");
                    GameHandler.zombie.setHealth(GameHandler.players.size() * 200.0f);
                    GameHandler.zombie.setTarget(null);
                    Bukkit.getScheduler().cancelTasks(Main.getPlugin(Main.class));
                    Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), new Timer3(), 0L, 20L);
                }, 40L);
            }, 40L);
        }
        count--;
    }
}
