package com.spectrasonic.NiiniiwiPerma.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.spectrasonic.NiiniiwiPerma.Main;
import com.spectrasonic.Utils.MessageUtils;
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

        // Mostrar información con formato bonito usando mensajes de la configuración
        String header = plugin.getConfig().getString("messages.version_header", "");
        if (!header.isEmpty()) {
            MessageUtils.sendMessage(sender, header);
        }

        String pluginInfo = plugin.getConfig()
                .getString("messages.version_plugin_info",
                        "<gold><bold>{plugin_name}</bold> <gray>v{plugin_version}</gray>")
                .replace("{plugin_name}", pluginName)
                .replace("{plugin_version}", version);
        MessageUtils.sendMessage(sender, pluginInfo);

        String authorInfo = plugin.getConfig()
                .getString("messages.version_author_info", "<yellow>Autor: <white>{plugin_authors}")
                .replace("{plugin_authors}", author);
        MessageUtils.sendMessage(sender, authorInfo);

        String footer = plugin.getConfig().getString("messages.version_footer", "");
        if (!footer.isEmpty()) {
            MessageUtils.sendMessage(sender, footer);
        }
    }

    @Subcommand("reload")
    @Description("Recarga la configuración del plugin")
    @CommandPermission("niiniiwipermanent.reload")
    public void onReload(CommandSender sender) {
        plugin.reloadConfig();
        String message = plugin.getConfig().getString("messages.reload_success");
        MessageUtils.sendMessage(sender, message);
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
            plugin.getConfig().set("mechanics.prevent-ancient-debris-mining", !enabled);
            plugin.saveConfig();

            String messageKey = enabled ? "messages.mineancient_enabled" : "messages.mineancient_disabled";
            String message = plugin.getConfig().getString(messageKey,
                    enabled ? "<green>[✔] <#787878>Ahora se puede puede Minar <#ff8d0a>Ancient Debris.<reset>"
                            : "<#a11316>[✖] <#787878>No se puede puede Minar <#a18100>Ancient Debris.<reset>");

            MessageUtils.sendMessage(sender, message);
        }
    }
}