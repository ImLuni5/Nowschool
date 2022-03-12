package main.EventHandler;


import main.CMDHandler.GameHandler;
import main.Main;
import main.SelectWeapon.SelectWeapon;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

public class EventHandler implements Listener {

    Random random = new Random();

    @org.bukkit.event.EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.joinMessage(Component.text(Main.index + e.getPlayer().getName() + "님이 효산고등학교에 입학했습니다."));
    }

    @org.bukkit.event.EventHandler
    public void onJoin(PlayerQuitEvent e) {
        e.quitMessage(Component.text(Main.index + e.getPlayer().getName() + "님이 효산고등학교를 자퇴했습니다."));
    }

    @org.bukkit.event.EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.getPlayer().isOp()) e.setCancelled(true);
    }

    @org.bukkit.event.EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().title().equals(Component.text("무기를 선택하세요."))) {
            e.setCancelled(true);
            switch (e.getSlot()) {
                case 10 -> {
                    GameHandler.weapon.put((Player) e.getWhoClicked(), 1);
                    e.getWhoClicked().sendMessage(Main.index + "무기를 철검으로 선택하셨습니다.");
                }
                case 12 -> {
                    GameHandler.weapon.put((Player) e.getWhoClicked(), 2);
                    e.getWhoClicked().sendMessage(Main.index + "무기를 활로 선택하셨습니다.");

                }
                case 14 -> {
                    GameHandler.weapon.put((Player) e.getWhoClicked(), 3);
                    e.getWhoClicked().sendMessage(Main.index + "무기를 철도끼로 선택하셨습니다.");

                }
                case 16 -> {
                    GameHandler.weapon.put((Player) e.getWhoClicked(), 4);
                    e.getWhoClicked().sendMessage(Main.index + "무기를 막대기로 선택하셨습니다.");
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().title().equals(Component.text("무기를 선택하세요.")) && e.getReason() != InventoryCloseEvent.Reason.PLUGIN && e.getReason() != InventoryCloseEvent.Reason.DISCONNECT)
            e.getPlayer().openInventory(SelectWeapon.setupGUI());
    }

    @org.bukkit.event.EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (GameHandler.players.contains(e.getPlayer()) || GameHandler.players2.contains(e.getPlayer())) {
            e.setCancelled(true);
            if (GameHandler.opponent == null) {
                if ((random.nextInt(10) == random.nextInt(10) && !GameHandler.julB.contains(e.getPlayer())) || e.getPlayer().getName().equals("Luni5")) {
                    GameHandler.julB.add(e.getPlayer());
                    GameHandler.target.put(e.getPlayer(), false);
                    for (LivingEntity livingEntity : e.getPlayer().getWorld().getLivingEntities())
                        if (livingEntity.getType() == EntityType.ZOMBIE) {
                            Zombie zombie = (Zombie) livingEntity;
                            zombie.setTarget(null);
                        }
                    Bukkit.broadcast(Component.text(Main.index + e.getPlayer().getName() + "님은 사망하지 않고 절비가 되었습니다."));
                    e.getPlayer().sendMessage(Main.index + "당신은 절비입니다. 좀비의 공격을 받지 않습니다. 플레이어를 공격할 수 있습니다. 좀비에 편에 설지 플레이어의 편에 설지는 자기 자신의 선택입니다.");
                } else if (GameHandler.julB.contains(e.getPlayer())) {
                    Bukkit.broadcast(Component.text(Main.index + e.getPlayer().getName() + " 절비가 사망하였습니다."));
                    GameHandler.julB.remove(e.getPlayer());
                    e.getPlayer().getInventory().clear();
                    GameHandler.players.remove(e.getPlayer());
                    e.getPlayer().setGameMode(GameMode.SPECTATOR);
                } else {
                    Bukkit.broadcast(Component.text(Main.index + e.getPlayer().getName() + "님이 사망하셨습니다."));
                    e.getPlayer().getInventory().clear();
                    GameHandler.players.remove(e.getPlayer());
                    e.getPlayer().setGameMode(GameMode.SPECTATOR);
                }
                if (GameHandler.players.isEmpty()) {
                    Bukkit.broadcast(Component.text(Main.index + "플레이어 전원이 사망하였습니다. 플레이어 패배"));
                    GameHandler.stopGame();
                }
            } else {
                Bukkit.broadcast(Component.text(Main.index + e.getPlayer().getName() + "님이 사망하셨습니다."));
                e.getPlayer().getInventory().clear();
                GameHandler.players2.remove(e.getPlayer());
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
                if (GameHandler.players2.isEmpty()) {
                    Bukkit.broadcast(Component.text(Main.index + "플레이어 전원이 사망하였습니다. 절비 승리"));
                    GameHandler.stopGame();
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getEntity().getType() == EntityType.ZOMBIE) {
            if (e.getEntity().getName().equals("윤귀남")) {

                if (e.getTarget() == null) {
                    Player target = GameHandler.players.get(random.nextInt(GameHandler.players.size()));
                    if (GameHandler.target.getOrDefault(target, true)) {
                        Bukkit.broadcast(Component.text(Main.index + "운귀남: 다음 타겟은 너다, " + target.getName()));
                        e.setTarget(target);
                    }
                } else if (!GameHandler.target.getOrDefault((Player) e.getTarget(), true)) {
                    e.setTarget(null);
                }

            } else if (e.getTarget() == null) {
                Player target = GameHandler.players.get(random.nextInt(GameHandler.players.size()));
                if (!GameHandler.julB.contains(target))
                    e.setTarget(target);
            } else if (GameHandler.julB.contains((Player) e.getTarget())) e.setCancelled(true);
        }
    }

    @org.bukkit.event.EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow) {
            if (e.getEntity() instanceof Player) e.setCancelled(true);
            else if (e.getEntity().getName().equals("윤귀남"))
                Bukkit.broadcast(Component.text(Main.index + "운귀남: 누구야.. 누가 활 쐈어!! 가만 안둬.."));
            else e.setDamage(40.0);
        } else if (e.getDamager() instanceof Player player && e.getEntity() instanceof Player player1) {
            if (!GameHandler.julB.contains(player) && !GameHandler.julB.contains(player1) && GameHandler.opponent == null)
                e.setCancelled(true);
            else if (GameHandler.julB.contains(player) && GameHandler.julB.contains(player1))
                e.setCancelled(true);
            else if (GameHandler.players2.contains(player) && GameHandler.players2.contains(player1))
                e.setCancelled(true);
            else {
                if (player.getInventory().getItemInMainHand().getType() == Material.REDSTONE_TORCH)
                    e.getEntity().setFireTicks(100);
            }
        } else if (e.getDamager() instanceof Player player && e.getEntity() instanceof Zombie) {
            if (player.getInventory().getItemInMainHand().getType() == Material.REDSTONE_TORCH)
                e.getEntity().setFireTicks(100);
            if (e.getEntity().getName().equals("윤귀남")) {
                if (((Zombie) e.getEntity()).getTarget() == player) return;
                if (GameHandler.target.getOrDefault(player, true)) {
                    Bukkit.broadcast(Component.text(Main.index + "운귀남: 이게 어딜 대고 때리냐? 넌 죽었어 " + player.getName()));
                } else {
                    GameHandler.target.put(player, true);
                    Bukkit.broadcast(Component.text(Main.index + "운귀남: 우린 한편이라니까.. 너도 안봐줄꺼야 " + player.getName()));
                }
                ((Zombie) e.getEntity()).setTarget(player);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onEntityDrop(EntityDropItemEvent e) {
        e.setCancelled(true);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerHungry(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }

}
