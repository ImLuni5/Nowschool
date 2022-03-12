package main.TimerHandler;

import main.CMDHandler.GameHandler;
import main.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

public class Timer3 implements Runnable{
    public static int count = 120;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendActionBar(Component.text("§0§l남은시간: §4§l" + count / 60 + "§0§l분 §4§l" + count % 60 + "§0§l초"));
        if (count == 0) {
            GameHandler.zombie.setHealth(0.0f);
            GameHandler.zombie = null;
            Bukkit.broadcast(Component.text(Main.index + "윤귀남: 윽.. 누가 나한테 총을 쐈어..."));
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
                                GameHandler.stopGame();
                                return;
                            }
                        }
                        Bukkit.broadcast(Component.text(Main.index + "플레이어는 구조 헬기를 타고 탈출했습니다. 플레이어 승리"));
                        GameHandler.stopGame();
                    }, 40L);
                }, 40L);
            }, 40L);
        }
        count--;
    }
}
