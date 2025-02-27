package com.st0x0ef.beyond_earth.common.menus.planetselection;

import com.st0x0ef.beyond_earth.common.config.Config;
import com.st0x0ef.beyond_earth.common.menus.planetselection.helper.PlanetSelectionMenuNetworkHandlerHelper;
import com.st0x0ef.beyond_earth.common.util.Methods;
import com.st0x0ef.beyond_earth.common.util.Planets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlanetSelectionMenuNetworkHandler extends PlanetSelectionMenuNetworkHandlerHelper {
    public final int integer;

    public PlanetSelectionMenuNetworkHandler(int integer) {
        this.integer = integer;
    }

    public PlanetSelectionMenuNetworkHandler(FriendlyByteBuf buffer) {
        this.integer = buffer.readInt();
    }

    public static PlanetSelectionMenuNetworkHandler decode(FriendlyByteBuf buffer) {
        return new PlanetSelectionMenuNetworkHandler(buffer);
    }

    public static void encode(PlanetSelectionMenuNetworkHandler message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.integer);
    }

    public static void handle(PlanetSelectionMenuNetworkHandler message,
            Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            boolean isPlanet = Planets.PLANET_ID_MAPS.containsKey(message.integer);
            if (isPlanet) {
                ResourceKey<Level> dest = Planets.PLANET_ID_MAPS.get(message.integer);
                message.defaultOptions(player);
                Methods.createLanderAndTeleportTo(player, dest, 700, false);
                return;
            }

            boolean isOrbit = Planets.ORBIT_ID_MAPS.containsKey(message.integer);
            if (isOrbit) {
                ResourceKey<Level> dest = Planets.ORBIT_ID_MAPS.get(message.integer);
                message.defaultOptions(player);
                Methods.createLanderAndTeleportTo(player, dest, 700, false);
                return;
            }

            boolean isStation = Planets.STATION_ID_MAPS.containsKey(message.integer);
            if (isStation) {
                ResourceKey<Level> dest = Planets.STATION_ID_MAPS.get(message.integer);
                if(Config.CHECK_SPACE_STATION.get()) {
                    if (Methods.canPlaceStation(player.getServer().getLevel(dest), player)) {
                        message.defaultOptions(player);
                        message.deleteItems(player);
                        Methods.createLanderAndTeleportTo(player, dest, 700, true);
                    } else {
                        player.closeContainer();
                        Methods.openErrorGui(player);
                    }
                } else {
                    message.defaultOptions(player);
                    message.deleteItems(player);
                    Methods.createLanderAndTeleportTo(player, dest, 700, true);
                }
            }
        });

        context.setPacketHandled(true);
    }
}
