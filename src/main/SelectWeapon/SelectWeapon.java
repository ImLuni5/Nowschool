package main.SelectWeapon;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SelectWeapon {
    public static Inventory setupGUI() {
        Inventory result = Bukkit.createInventory(null, 27, Component.text("무기를 선택하세요."));
        result.setItem(10, new ItemStack(Material.IRON_SWORD));
        result.setItem(12, new ItemStack(Material.BOW));
        result.setItem(14, new ItemStack(Material.IRON_AXE));
        result.setItem(16, new ItemStack(Material.REDSTONE_TORCH));
        return result;
    }
}
