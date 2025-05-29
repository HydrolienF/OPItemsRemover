package fr.formiko.opitemsremover;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OPItemsRemoverListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Remover.removeOPItemsFromPlayer(event.getPlayer());
    }
    @EventHandler
    public void onPlayerChangeInventory(PlayerInventorySlotChangeEvent event) {
        Remover.removeOPItemsFromPlayer(event.getPlayer());
    }

}
