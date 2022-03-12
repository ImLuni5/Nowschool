package main.EventHandler;

import main.CMDHandler.GameHandler;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

public class Walking implements Listener {

    @EventHandler
    public void onWalk(PlayerMoveEvent e) {
        if (GameHandler.players2.contains(e.getPlayer())) {
            GameHandler.opponent.playSound(e.getPlayer().getLocation(), Sound.BLOCK_WOOD_STEP, 100.0f, 1.0f);
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        if (GameHandler.players2.contains(e.getPlayer())) {
            if (e.getClickedBlock() != null && e.getHand() == EquipmentSlot.HAND) {
                if (e.getClickedBlock().getType() == Material.OAK_DOOR || e.getClickedBlock().getType() == Material.ACACIA_DOOR || e.getClickedBlock().getType() == Material.BIRCH_DOOR) {
                    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        GameHandler.opponent.playSound(e.getPlayer().getLocation(), Sound.BLOCK_WOODEN_DOOR_OPEN, 100.0f, 1.0f);
                    }
                }
            }
        }
    }

}
