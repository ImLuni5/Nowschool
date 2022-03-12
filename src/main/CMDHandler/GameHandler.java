package main.CMDHandler;

import main.EventHandler.CheckFloor;
import main.EventHandler.EventHandler;
import main.EventHandler.Walking;
import main.Main;
import main.SelectWeapon.SelectWeapon;
import main.TimerHandler.Timer;
import main.TimerHandler.Timer2;
import main.TimerHandler.Timer3;
import main.TimerHandler.Timer4;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameHandler {

    public static boolean isStarted = false;
    private static final String index = Main.index;
    public static List<Player> players = new ArrayList<>();
    public static List<Player> players2 = new ArrayList<>();
    public static List<Player> julB = new ArrayList<>();
    private static final Main plugin = Main.getPlugin(Main.class);
    public static final HashMap<Player, Integer> weapon = new HashMap<>();
    public static final HashMap<Player, Boolean> target = new HashMap<>();
    private static World world;
    private static Location classroom = new Location(world, 22.0, 90.0, 10.0);
    public static int taskID = -1;
    public static int remain = 0;
    public static Zombie zombie;

    public static Player opponent = null;

    public static void onCommand(CommandSender commandSender, String[] args) {

        if (!isStarted) {
            isStarted = true;
            switch (args[1]) {
                case "학교탈출" -> {
                    players.clear();
                    for (Player player1 : Bukkit.getOnlinePlayers())
                        if (player1.getGameMode() != GameMode.SPECTATOR) {
                            players.add(player1);
                            player1.openInventory(SelectWeapon.setupGUI());
                        }
                    Bukkit.broadcast(Component.text(index + "게임에 참가할 플레이어 수: " + players.size() + "명"));
                    Bukkit.broadcast(Component.text(index + "무기를 선택해주세요. 선택하지 않으면 랜덤으로 선택됩니다."));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        Player player = (Player) commandSender;
                        world = player.getWorld();
                        Location[] location = new Location[5];
                        location[0] = new Location(world, 34.0, 86.0, 21.0);
                        location[1] = new Location(world, 34.0, 90.0, 21.0);
                        location[2] = new Location(world, 34.0, 94.0, 21.0);
                        location[3] = new Location(world, 34.0, 98.0, 21.0);
                        location[4] = new Location(world, 34.0, 102.0, 21.0);
                        for (int i = 0; i < 100; i++) {
                            Zombie zombie1 = (Zombie) world.spawnEntity(location[0], EntityType.ZOMBIE, false);
                            Zombie zombie2 = (Zombie) world.spawnEntity(location[1], EntityType.ZOMBIE, false);
                            Zombie zombie3 = (Zombie) world.spawnEntity(location[2], EntityType.ZOMBIE, false);
                            Zombie zombie4 = (Zombie) world.spawnEntity(location[3], EntityType.ZOMBIE, false);
                            Zombie zombie5 = (Zombie) world.spawnEntity(location[4], EntityType.ZOMBIE, false);
                            zombie1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 2, true));
                            zombie2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 2, true));
                            zombie3.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 2, true));
                            zombie4.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 2, true));
                            zombie5.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 2, true));
                        }
                        classroom = new Location(world, 22.0, 90.0, 10.0);
                        Random random = new Random();
                        for (Player player1 : players) {
                            switch (weapon.getOrDefault(player1, random.nextInt(4) + 1)) {
                                case 1 -> {
                                    player1.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                                    player1.getInventory().addItem(new ItemStack(Material.SHIELD));
                                }
                                case 2 -> {
                                    player1.getInventory().addItem(new ItemStack(Material.BOW));
                                    player1.getInventory().addItem(new ItemStack(Material.ARROW, 1000));
                                }
                                case 3 -> player1.getInventory().addItem(new ItemStack(Material.IRON_AXE));
                                case 4 -> player1.getInventory().addItem(new ItemStack(Material.REDSTONE_TORCH));
                            }
                            player1.teleportAsync(classroom);
                            player1.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        }
                        Bukkit.getPluginManager().registerEvents(new CheckFloor(), plugin);
                        weapon.clear();
                        Bukkit.broadcast(Component.text(index + "게임 시작."));
                        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Timer(), 0, 20);
                    }, 200L);
                }
                case "절비" -> {
                    players2.clear();
                    Random random = new Random();
                    opponent = (Player) Bukkit.getOnlinePlayers().toArray()[random.nextInt(Bukkit.getOnlinePlayers().size())];
                    Bukkit.broadcast(Component.text(index + "이번 절비는.. " + opponent.getName() + "입니다."));
                    world = opponent.getWorld();
                    classroom = new Location(world, 22.0, 90.0, 10.0);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        Bukkit.broadcast(Component.text(index + opponent.getName() + ": 으아아아아아악!!"));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                if (player == opponent) {
                                    player.teleportAsync(new Location(player.getWorld(), 35.0f, 106.0f, 22.0f));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 32767, 5, true));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 32767, 5, true));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 32767, 1, true));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 32767, 5, true));
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 32767, 255, true));
                                } else {
                                    players2.add(player);
                                    player.teleportAsync(classroom);
                                }
                            }
                            Bukkit.getPluginManager().registerEvents(new Walking(), plugin);
                            Bukkit.broadcast(Component.text(index + "게임 시작."));
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Timer4(), 0L, 20L);
                        }, 40L);
                    }, 40L);
                }
            }
        } else commandSender.sendMessage(index + "이미 게임이 시작되었습니다.");
    }

    public static void stopGame() {
        GameHandler.isStarted = false;
        GameHandler.opponent = null;
        players.clear();
        players2.clear();
        julB.clear();
        target.clear();
        HandlerList.unregisterAll(plugin);
        Bukkit.getPluginManager().registerEvents(new EventHandler(), plugin);
        for (LivingEntity livingEntity : world.getLivingEntities()) {
            if (livingEntity.getType() == EntityType.ZOMBIE) livingEntity.setHealth(0.0);
            else if (livingEntity.getType() == EntityType.PLAYER) {
                Player player = (Player) livingEntity;
                player.getInventory().clear();
                for (PotionEffect effect : player.getActivePotionEffects())
                    player.removePotionEffect(effect.getType());
                player.setGameMode(GameMode.SURVIVAL);
            }
        }
        Bukkit.getScheduler().cancelTasks(plugin);
        Timer.count = 300;
        Timer2.count = 120;
        Timer3.count = 120;
        Timer4.count = 300;
    }
}
