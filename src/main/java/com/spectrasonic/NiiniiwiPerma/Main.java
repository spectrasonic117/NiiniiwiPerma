package com.spectrasonic.NiiniiwiPerma;

import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.NiiniiwiPerma.managers.CommandManager;
import com.spectrasonic.NiiniiwiPerma.managers.ConfigManager;
import com.spectrasonic.NiiniiwiPerma.managers.EventManager;

import lombok.Getter;

import com.spectrasonic.Utils.CommandUtils;
import com.spectrasonic.Utils.MessageUtils;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigManager configManager;
    private CommandManager commandManager;
    private EventManager eventManager;
    private PaperCommandManager acfCommandManager;
    private BukkitAudiences adventure;

    public PaperCommandManager getAcfCommandManager() {
        return acfCommandManager;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        this.adventure = BukkitAudiences.create(this);
        
        // Inicializar MessageUtils con las dependencias necesarias
        MessageUtils.initialize(this, this.configManager, this.adventure);
        
        // Inicializar ACF Command Manager
        this.acfCommandManager = new PaperCommandManager(this);
        this.commandManager = new CommandManager(this);
        this.eventManager = new EventManager(this);

        CommandUtils.setPlugin(this);
        MessageUtils.sendStartupMessage(this);

    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
        }
        MessageUtils.sendShutdownMessage(this);
    }

}