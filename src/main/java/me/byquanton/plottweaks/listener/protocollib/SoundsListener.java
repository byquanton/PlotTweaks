package me.byquanton.plottweaks.listener.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;


public class SoundsListener {

    public SoundsListener(Plugin plugin) {

        // 1023 = Wither spawn (https://wiki.vg/Protocol)
        PacketAdapter wither_sound = new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_EVENT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packetContainer = event.getPacket();

                if (packetContainer.getIntegers().read(0) == 1023) {
                    packetContainer.getBooleans().write(0, false);
                }
            }
        };

        // 1038 = End Portal opening (https://wiki.vg/Protocol)
        PacketAdapter end_portal = new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_EVENT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packetContainer = event.getPacket();

                if (packetContainer.getIntegers().read(0) == 1038) {
                    packetContainer.getBooleans().write(0, false);
                }
            }
        };

        if(plugin.getConfig().getBoolean("sound-blocking.block-wither-sound")){
            ProtocolLibrary.getProtocolManager().addPacketListener(wither_sound);
        }
        if(plugin.getConfig().getBoolean("sound-blocking.block-end-portal-opening")){
            ProtocolLibrary.getProtocolManager().addPacketListener(end_portal);
        }
    }

}
