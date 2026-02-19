package com.spectrasonic.NiiniiwiPerma;

import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.NiiniiwiPerma.managers.CommandManager;
import com.spectrasonic.NiiniiwiPerma.managers.ConfigManager;
import com.spectrasonic.NiiniiwiPerma.managers.EventManager;

import lombok.Getter;

import com.spectrasonic.Utils.CommandUtils;
import com.spectrasonic.Utils.MessageUtils;

import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigManager configManager;
    private CommandManager commandManager;
    private EventManager eventManager;
    private PaperCommandManager acfCommandManager;

    public PaperCommandManager getAcfCommandManager() {
        return acfCommandManager;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        
        // Inicializar ACF Command Manager
        this.acfCommandManager = new PaperCommandManager(this);
        this.commandManager = new CommandManager(this);
        this.eventManager = new EventManager(this);

        CommandUtils.setPlugin(this);
        MessageUtils.sendStartupMessage(this);

    }

    @Override
    public void onDisable() {
        MessageUtils.sendShutdownMessage(this);
    }

}