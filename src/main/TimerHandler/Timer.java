package main.TimerHandler;

import main.CMDHandler.GameHandler;
import main.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Timer implements Runnable {

    public static int count = 300;

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendActionBar(Component.text("§0§l남은시간: §4§l" + count / 60 + "§0§l분 §4§l" + count % 60 + "§0§l초"));
        if (count <= 0) {
            Bukkit.broadcast(Component.text(Main.index + "플레이어는 결국 옥상으로 탈출하지 못하였습니다. 플레이어 패배"));
            GameHandler.stopGame();
        }
        count--;
    }
}
