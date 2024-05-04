package fr.formiko.opitemsremover;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;

public class OPItemsRemoverListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) { Remover.removeOPItemsFromPlayer(event.getPlayer()); }
    @EventHandler
    public void onPlayerChangeInventory(PlayerInventorySlotChangeEvent event) { Remover.removeOPItemsFromPlayer(event.getPlayer()); }

}
