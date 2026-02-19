package com.spectrasonic.NiiniiwiPerma.managers;

import com.spectrasonic.NiiniiwiPerma.Main;
import com.spectrasonic.NiiniiwiPerma.commands.PermaCommand;
import lombok.Getter;

@Getter
public class CommandManager {

    private final Main plugin;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        plugin.getAcfCommandManager().registerCommand(new PermaCommand(plugin));
    }
}