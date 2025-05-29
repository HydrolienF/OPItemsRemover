package fr.formiko.opitemsremover;

import org.bukkit.entity.Player;

public class Remover {
    private Remover() {}
    public static void removeOPItemsFromPlayer(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null && OPItemsRemoverPlugin.getInstance().getDisabledItems()
                    .contains(player.getInventory().getItem(i).getType())) {
                OPItemsRemoverPlugin.log("Removing " + player.getInventory().getItem(i).getAmount() + " "
                        + player.getInventory().getItem(i).getType() + " from " + player.getName());
                player.getInventory().setItem(i, null);
            }
        }
    }
}
