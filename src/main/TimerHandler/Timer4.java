package main.TimerHandler;


import main.CMDHandler.GameHandler;
import main.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Timer4 implements Runnable{
    public static int count = 300;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendActionBar(Component.text("§0§l남은시간: §4§l" + count / 60 + "§0§l분 §4§l" + count % 60 + "§0§l초"));
        if (count == 0) {
            Bukkit.broadcast(Component.text(Main.index + "절비는 모든 플레이어를 사살하지 못했습니다. 플레이어 승리"));
            GameHandler.stopGame();
        }
        count--;
    }
}

