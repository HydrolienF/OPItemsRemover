package fr.formiko.opitemsremover;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

@CommandAlias("opItemRemoverCommand|opirc")
@CommandPermission("opitemsremover.admin")
public class OPItemsRemoverCommand extends BaseCommand {
    @Subcommand("reload")
    public void onReload(CommandSender sender) {
        OPItemsRemoverPlugin.getInstance().reloadConfig();
        sender.sendMessage(Component.text("Configuration reloaded.", NamedTextColor.GREEN));
    }

    @Subcommand("list")
    public void onList(CommandSender sender) {
        Collection<Material> disabled = OPItemsRemoverPlugin.getInstance().getDisabledItems();
        if (disabled == null || disabled.isEmpty()) {
            sender.sendMessage(Component.text("No disabled items.", NamedTextColor.YELLOW));
            return;
        }
        String items = disabled.stream().map(Material::name).sorted().collect(Collectors.joining(", "));
        sender.sendMessage(Component.text("Disabled items: ", NamedTextColor.GREEN)
                .append(Component.text(items, NamedTextColor.WHITE)));
    }

    @Subcommand("add")
    @CommandCompletion("@materials @empty")
    public void onAdd(CommandSender sender, String materialName) {
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            sender.sendMessage(Component.text("Unknown material: ", NamedTextColor.RED)
                    .append(Component.text(materialName, NamedTextColor.WHITE)));
            return;
        }

        OPItemsRemoverPlugin plugin = OPItemsRemoverPlugin.getInstance();
        List<String> disabledList = plugin.getConfig().getStringList("disabledItems");
        String matName = material.name();
        if (disabledList.contains(matName)) {
            sender.sendMessage(Component.text(matName + " is already disabled.", NamedTextColor.YELLOW));
            return;
        }

        disabledList.add(matName);
        plugin.getConfig().set("disabledItems", disabledList);
        plugin.saveConfig();
        plugin.reloadConfig();
        sender.sendMessage(Component.text("Item added to disabled items list: ", NamedTextColor.GREEN)
            .append(Component.text(matName, NamedTextColor.WHITE)));
    }

    @Subcommand("remove")
    @CommandCompletion("@disabledItems")
    public void onRemove(CommandSender sender, String materialName) {
        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            sender.sendMessage(Component.text("Unknown material: ", NamedTextColor.RED)
                    .append(Component.text(materialName, NamedTextColor.WHITE)));
            return;
        }

        OPItemsRemoverPlugin plugin = OPItemsRemoverPlugin.getInstance();
        List<String> disabledList = plugin.getConfig().getStringList("disabledItems");
        String matName = material.name();
        if (!disabledList.contains(matName)) {
            sender.sendMessage(Component.text(matName + " is not in the disabled items list.",
                    NamedTextColor.YELLOW));
            return;
        }

        disabledList.remove(matName);
        plugin.getConfig().set("disabledItems", disabledList);
        plugin.saveConfig();
        plugin.reloadConfig();
        sender.sendMessage(Component.text("Item removed from disabled items list: ", NamedTextColor.GREEN)
            .append(Component.text(matName, NamedTextColor.WHITE)));
    }
}
