package com.spectrasonic.NiiniiwiPerma.managers;

import com.spectrasonic.NiiniiwiPerma.Main;
import com.spectrasonic.NiiniiwiPerma.listeners.MineAncientDebrisListener;
import lombok.Getter;

@Getter
public class EventManager {

    private final Main plugin;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        registerEvents();
    }

    private void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new MineAncientDebrisListener(plugin), plugin);
    }

}