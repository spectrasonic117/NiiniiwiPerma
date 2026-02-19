package com.spectrasonic.NiiniiwiPerma.listeners;

import com.spectrasonic.NiiniiwiPerma.Main;
import com.spectrasonic.Utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MineAncientDebrisListener implements Listener {

    private final Main plugin;

    public MineAncientDebrisListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Verificar si el bloque roto es Ancient Debris
        if (event.getBlock().getType() == Material.ANCIENT_DEBRIS) {
            // Obtener el valor de la configuración: true significa que está PREVENTIDO (no
            // se puede minar)
            boolean preventMining = plugin.getConfig().getBoolean("mechanics.prevent-ancient-debris-mining", false);
            
            if (preventMining) {
                // Cancelar el evento para prevenir que se rompa el bloque
                event.setCancelled(true);
                
                // Prevenir que se generen drops
                event.setDropItems(false);
                
                // Enviar mensaje al jugador usando la clave de configuración
                String message = plugin.getConfig().getString("messages.ancient_debris_denied", "<red>No puedes minar Ancient Debris.");
                MessageUtils.sendMessage(event.getPlayer(), message);
            }
        }
    }
}