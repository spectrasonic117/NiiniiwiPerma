package com.spectrasonic.NiiniiwiPerma.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.spectrasonic.NiiniiwiPerma.Main;
import com.spectrasonic.Utils.MessageUtils;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

@CommandAlias("perma")
public class PermaCommand extends BaseCommand {

    private final Main plugin;

    public PermaCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Subcommand("version")
    @Description("Muestra la la versión del plugin y su autor")
    @CommandPermission("niiniiwipermanent.version")
    public void onVersion(CommandSender sender) {
        String pluginName = plugin.getDescription().getName();
        String version = plugin.getDescription().getVersion();
        String author = plugin.getDescription().getAuthors().isEmpty() ? "Unknown"
                : String.join(", ", plugin.getDescription().getAuthors());

        // Mostrar información del Plugin
        TagResolver placeholders = TagResolver.builder()
                .resolver(Placeholder.parsed("plugin_name", pluginName))
                .resolver(Placeholder.parsed("plugin_version", version))
                .resolver(Placeholder.parsed("plugin_authors", author))
                .build();

        MessageUtils.sendMessage(sender, "messages.version_plugin_info", placeholders);
        MessageUtils.sendMessage(sender, "messages.version_author_info", placeholders);
    }

    @Subcommand("reload")
    @Description("Recarga la configuración del plugin")
    @CommandPermission("niiniiwipermanent.reload")
    public void onReload(CommandSender sender) {
        plugin.getConfigManager().loadConfig();
        MessageUtils.sendMessageFromKey(sender, "messages.reload_success");
    }

    @Subcommand("mechanic")
    @CommandAlias("perma")
    @Description("Comandos relacionados con las mecánicas del plugin")
    @CommandPermission("niiniiwipermanent.mechanic")
    public class MechanicSubcommand extends BaseCommand {

        @Subcommand("mineancient")
        @CommandCompletion("true|false")
        @Description("Habilita o deshabilita la minería de Ancient Debris")
        @CommandPermission("niiniiwipermanent.mechanic.mineancient")
        public void onMineAncient(CommandSender sender, boolean enabled) {
            plugin.getConfigManager().getConfig().set("mechanics.can_player_mine_ancient_debris", !enabled);
            plugin.getConfigManager().saveConfig();

            String messageKey = enabled ? "messages.mineancient_enabled" : "messages.mineancient_disabled";
            MessageUtils.sendMessageFromKey(sender, messageKey);
        }
    }
}