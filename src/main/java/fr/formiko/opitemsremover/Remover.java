package fr.formiko.opitemsremover;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Remover {
    private Remover() {}
    public static void removeOPItemsFromPlayer(Player player) {
        removeOPItemsFromInventory(player.getInventory(), player.getName());
    }
    public static void removeOPItemsFromInventory(Inventory inventory, String from) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) != null
                    && OPItemsRemoverPlugin.getInstance().getDisabledItems().contains(inventory.getItem(i).getType())) {
                OPItemsRemoverPlugin.log("Removing " + inventory.getItem(i).getAmount() + " "
                        + inventory.getItem(i).getType() + " from " + from);
                inventory.setItem(i, null);
            }
        }
    }
}
