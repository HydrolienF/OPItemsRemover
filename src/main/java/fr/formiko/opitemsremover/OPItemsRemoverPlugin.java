package fr.formiko.opitemsremover;

import co.aikar.commands.PaperCommandManager;
import java.util.Collection;
import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class OPItemsRemoverPlugin extends JavaPlugin {
    private Collection<Material> disabledItems;

    public static OPItemsRemoverPlugin getInstance() {
        return JavaPlugin.getPlugin(OPItemsRemoverPlugin.class);
    }

    @Override
    public void onEnable() {
        new Metrics(this, 21798);
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new OPItemsRemoverListener(), this);

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new OPItemsRemoverCommand());
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        disabledItems = getConfig().getStringList("disabledItems").stream().map(String::toUpperCase)
                .map(Material::valueOf).toList();
        for (Player player : getServer().getOnlinePlayers()) {
            Remover.removeOPItemsFromPlayer(player);
        }
    }

    public Collection<Material> getDisabledItems() {
        return disabledItems;
    }

    public static void log(String message) {
        if (getInstance().getConfig().getBoolean("debug", false)) {
            getInstance().getLogger().info(message);
        }
    }
}
