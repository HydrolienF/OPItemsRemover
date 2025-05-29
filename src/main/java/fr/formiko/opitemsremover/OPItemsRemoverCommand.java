package fr.formiko.opitemsremover;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;

@CommandAlias("opItemRemoverCommand|opirc")
@CommandPermission("opitemsremover.admin")
public class OPItemsRemoverCommand extends BaseCommand {
    @Subcommand("reload")
    public void onReload() {
        OPItemsRemoverPlugin.getInstance().reloadConfig();
    }
}
