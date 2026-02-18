package com.spectrasonic.Utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import com.spectrasonic.NiiniiwiPerma.managers.ConfigManager;

@UtilityClass
@SuppressWarnings("unused")
public final class MessageUtils {

    private static JavaPlugin plugin;
    private static ConfigManager configManager;
    private static BukkitAudiences adventure;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static final String DIVIDER = "&8----------------------------------------";
    public static final String PREFIX = "&8[&6RoleplayCore&8]&6 » ";

    // Alert Prefixes
    public static final String SUCCESS_PREFIX = "&a&l[✔]&a ";
    public static final String ALERT_PREFIX = "&e&l[!]&e ";
    public static final String DENY_PREFIX = "&c&l[✖]&c ";
    public static final String WARNING_PREFIX = "&c&l[⚠]&c ";
    public static final String INFO_PREFIX = "&b&l[i]&b ";
    public static final String DEBUG_PREFIX = "&9&l[d]&9 ";

    // Bungee API constants
    private static final ChatMessageType ACTION_BAR_TYPE = ChatMessageType.ACTION_BAR;

    // Regex patterns for color parsing
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    // private static final Pattern LEGACY_COLOR_PATTERN =
    // Pattern.compile("&[0-9A-Fa-fK-Ok-oRr]");

    public static String processColorCodes(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        // First, convert hex colors (&#FFFFFF) to legacy format
        Matcher hexMatcher = HEX_COLOR_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();

        while (hexMatcher.find()) {
            String hexColor = hexMatcher.group(1);
            String replacement = "§x" + String.join("§", hexColor.split(""));
            hexMatcher.appendReplacement(sb, replacement);
        }
        hexMatcher.appendTail(sb);
        message = sb.toString();

        // Then translate legacy color codes (& to §)
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(PREFIX + message));
    }

    public static void noPrefixMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(message));
    }

    public static void sendMessageFromKey(CommandSender sender, String key) {
        Component component = getMessageComponent(key);
        adventure.sender(sender).sendMessage(component);
    }

    public static void sendMessage(CommandSender sender, String key, TagResolver... placeholders) {
        Component component = getMessageComponent(key, placeholders);
        adventure.sender(sender).sendMessage(component);
    }

    public static void sendMessage(CommandSender sender, String key, String placeholderKey, String placeholderValue) {
        Component component = getMessageComponent(key, placeholderKey, placeholderValue);
        adventure.sender(sender).sendMessage(component);
    }

    public static void sendMessage(Player player, String key) {
        Component component = getMessageComponent(key);
        adventure.player(player).sendMessage(component);
    }

    public static void sendMessage(Player player, String key, TagResolver... placeholders) {
        Component component = getMessageComponent(key, placeholders);
        adventure.player(player).sendMessage(component);
    }

    public static void sendMessage(Player player, String key, String placeholderKey, String placeholderValue) {
        Component component = getMessageComponent(key, placeholderKey, placeholderValue);
        adventure.player(player).sendMessage(component);
    }

    public static void sendMessage(Player player, Component component) {
        adventure.player(player).sendMessage(component);
    }

    public static void sendMessage(CommandSender sender, Component component) {
        adventure.sender(sender).sendMessage(component);
    }

    public static Component getMessageComponent(String key) {
        String message = configManager.getMessage(key);
        if (message == null || message.isEmpty()) {
            return Component.empty();
        }
        return miniMessage.deserialize(message);
    }

    public static Component getMessageComponent(String key, TagResolver... placeholders) {
        String message = configManager.getMessage(key);
        if (message == null || message.isEmpty()) {
            return Component.empty();
        }
        return miniMessage.deserialize(message, placeholders);
    }

    public static Component getMessageComponent(String key, String placeholderKey, String placeholderValue) {
        return getMessageComponent(key, Placeholder.parsed(placeholderKey, placeholderValue));
    }

    public static Component processChannelMessage(String message) {
        if (message == null || message.isEmpty()) {
            return Component.empty();
        }
        return miniMessage.deserialize(message);
    }

    public static Component processChannelMessage(String message, TagResolver... placeholders) {
        if (message == null || message.isEmpty()) {
            return Component.empty();
        }
        return miniMessage.deserialize(message, placeholders);
    }

    public static Component processChannelMessage(String message, String placeholderKey, String placeholderValue) {
        return processChannelMessage(message, Placeholder.parsed(placeholderKey, placeholderValue));
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(processColorCodes(PREFIX + message));
    }

    public static void sendPermissionMessage(CommandSender sender) {
        sender.sendMessage(processColorCodes(PREFIX + "&4&l[✖] &7No tienes permisos suficientes"));
    }

    public static void sendOnlyPlayerCommandMessage(CommandSender sender) {
        sender.sendMessage(processColorCodes(PREFIX + "&4&l[✖] &7Solo jugadores pueden usar este comando"));
    }

    public static void configReloadedMessage(CommandSender sender) {
        sender.sendMessage(processColorCodes(PREFIX + "&a&l[✔] &7Configuración recargada"));
    }

    // Alert Messages

    public static void successMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(PREFIX + SUCCESS_PREFIX + message));
    }

    public static void alertMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(PREFIX + ALERT_PREFIX + message));
    }

    public static void denyMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(PREFIX + DENY_PREFIX + message));
    }

    public static void warningMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(PREFIX + WARNING_PREFIX + message));
    }

    public static void infoMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(PREFIX + INFO_PREFIX + message));
    }

    public static void debugMessage(CommandSender sender, String message) {
        sender.sendMessage(processColorCodes(PREFIX + DEBUG_PREFIX + message));
    }

    public static void sendStartupMessage(JavaPlugin plugin) {
        String[] messages = {
                DIVIDER,
                PREFIX + "&f" + plugin.getDescription().getName() + " &aPlugin Activado!",
                PREFIX + "&dVersión: &f" + plugin.getDescription().getVersion(),
                PREFIX + "&fDesarrollado por: &c" + plugin.getDescription().getAuthors(),
                DIVIDER
        };

        for (String message : messages) {
            Bukkit.getConsoleSender().sendMessage(processColorCodes(message));
        }
    }

    public static void sendVeiMessage(JavaPlugin plugin) {
        String[] messages = {
                PREFIX + "&8⣇⣿⠘⣿⣿⣿⡿⡿⣟⣟⢟⢟⢝⠵⡝⣿⡿⢂⣼⣿⣷⣌⠩⡫⡻⣝⠹⢿⣿⣷",
                PREFIX + "&8⡆⣿⣆⠱⣝⡵⣝⢅⠙⣿⢕⢕⢕⢕⢝⣥⢒⠅⣿⣿⣿⡿⣳⣌⠪⡪⣡⢑⢝⣇",
                PREFIX + "&8⡆⣿⣿⣦⠹⣳⣳⣕⢅⠈⢗⢕⢕⢕⢕⢕⢈⢆⠟⠋⠉⠁⠉⠉⠁⠈⠼⢐⢕⢽",
                PREFIX + "&8⡗⢰⣶⣶⣦⣝⢝⢕⢕⠅⡆⢕⢕⢕⢕⢕⣴⠏⣠⡶⠛⡉⡉⡛⢶⣦⡀⠐⣕⢕",
                PREFIX + "&8⡝⡄⢻⢟⣿⣿⣷⣕⣕⣅⣿⣔⣕⣵⣵⣿⣿⢠⣿⢠⣮⡈⣌⠨⠅⠹⣷⡀⢱⢕",
                PREFIX + "&8⡝⡵⠟⠈⢀⣀⣀⡀⠉⢿⣿⣿⣿⣿⣿⣿⣿⣼⣿⢈⡋⠴⢿⡟⣡⡇⣿⡇⡀⢕",
                PREFIX + "&8⡝⠁⣠⣾⠟⡉⡉⡉⠻⣦⣻⣿⣿⣿⣿⣿⣿⣿⣿⣧⠸⣿⣦⣥⣿⡇⡿⣰⢗⢄",
                PREFIX + "&8⠁⢰⣿⡏⣴⣌⠈⣌⠡⠈⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣬⣉⣉⣁⣄⢖⢕⢕⢕",
                PREFIX + "&8⡀⢻⣿⡇⢙⠁⠴⢿⡟⣡⡆⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣵⣵⣿",
                PREFIX + "&8⡻⣄⣻⣿⣌⠘⢿⣷⣥⣿⠇⣿⣿⣿⣿⣿⣿⠛⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿",
                PREFIX + "&8⣷⢄⠻⣿⣟⠿⠦⠍⠉⣡⣾⣿⣿⣿⣿⣿⣿⢸⣿⣦⠙⣿⣿⣿⣿⣿⣿⣿⣿⠟",
                PREFIX + "&8⡕⡑⣑⣈⣻⢗⢟⢞⢝⣻⣿⣿⣿⣿⣿⣿⣿⠸⣿⠿⠃⣿⣿⣿⣿⣿⣿⡿⠁⣠",
                PREFIX + "&8⡝⡵⡈⢟⢕⢕⢕⢕⣵⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣿⣿⣿⣿⣿⠿⠋⣀⣈⠙",
                PREFIX + "&8⡝⡵⡕⡀⠑⠳⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⢉⡠⡲⡫⡪⡪⡣"
        };

        for (String message : messages) {
            Bukkit.getConsoleSender().sendMessage(processColorCodes(message));
        }
    }

    public static void broadcastMessage(String message) {
        String processedMessage = processColorCodes(message);
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(processedMessage));
    }

    public static void sendShutdownMessage(JavaPlugin plugin) {
        String[] messages = {
                DIVIDER,
                PREFIX + "&c" + plugin.getDescription().getName() + " plugin Desactivado!",
                DIVIDER
        };

        for (String message : messages) {
            Bukkit.getConsoleSender().sendMessage(processColorCodes(message));
        }
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        String processedTitle = processColorCodes(title);
        String processedSubtitle = processColorCodes(subtitle);
        player.sendTitle(processedTitle, processedSubtitle, fadeIn, stay, fadeOut);
    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ACTION_BAR_TYPE,
                TextComponent.fromLegacyText(processColorCodes(message)));
    }

    public static void broadcastTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        String processedTitle = processColorCodes(title);
        String processedSubtitle = processColorCodes(subtitle);

        Bukkit.getOnlinePlayers()
                .forEach(player -> player.sendTitle(processedTitle, processedSubtitle, fadeIn, stay, fadeOut));
    }

    public static void broadcastActionBar(String message) {
        String processedMessage = processColorCodes(message);
        TextComponent component = new TextComponent(
                TextComponent.fromLegacyText(processedMessage));

        Bukkit.getOnlinePlayers().forEach(
                player -> player.spigot().sendMessage(ACTION_BAR_TYPE, component));
    }

    public static void sendMessageWithPlaceholders(CommandSender sender, String message) {
        String processedMessage = processPlaceholders(sender, message);
        sender.sendMessage(processColorCodes(PREFIX + processedMessage));
    }

    public static void noPrefixMessageWithPlaceholders(CommandSender sender, String message) {
        String processedMessage = processPlaceholders(sender, message);
        sender.sendMessage(processColorCodes(processedMessage));
    }

    public static void broadcastMessageWithPlaceholders(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            String processedMessage = processPlaceholders(player, message);
            player.sendMessage(processColorCodes(processedMessage));
        });
    }

    public static String processPlaceholders(Player player, String message) {
        if (isPlaceholderAPIAvailable()) {
            return applyPlaceholders(player, message);
        }
        return message;
    }

    private static String processPlaceholders(CommandSender sender, String message) {
        if (isPlaceholderAPIAvailable() && sender instanceof Player) {
            return applyPlaceholders((Player) sender, message);
        }
        return message;
    }

    private static boolean isPlaceholderAPIAvailable() {
        try {
            Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static String applyPlaceholders(Player player, String text) {
        try {
            // Use reflection to avoid compile-time dependency
            Class<?> placeholderAPIClass = Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            java.lang.reflect.Method setPlaceholdersMethod = placeholderAPIClass.getMethod("setPlaceholders",
                    org.bukkit.OfflinePlayer.class, String.class);
            return (String) setPlaceholdersMethod.invoke(null, player, text);
        } catch (Exception e) {
            // Silently fail if PlaceholderAPI is not available or there's an error
            return text;
        }
    }

    public static void initialize(JavaPlugin plugin, ConfigManager configManager,
            BukkitAudiences adventure) {
        MessageUtils.plugin = plugin;
        MessageUtils.configManager = configManager;
        MessageUtils.adventure = adventure;
    }

}