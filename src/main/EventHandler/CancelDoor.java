package main.EventHandler;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class CancelDoor implements Listener {

    @EventHandler
    public void onOpen(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null && e.getHand() == EquipmentSlot.HAND) {
            if (e.getClickedBlock().getType() == Material.OAK_DOOR || e.getClickedBlock().getType() == Material.ACACIA_DOOR || e.getClickedBlock().getType() == Material.BIRCH_DOOR) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
