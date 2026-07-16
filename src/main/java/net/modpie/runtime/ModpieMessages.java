package net.modpie.runtime;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

/**
 * Beginner-friendly messaging helpers.
 * <p>Supports Modpie verbs like {@code tell}, {@code broadcast}, and
 * {@code send_action_bar}.</p>
 */
public final class ModpieMessages {

    private ModpieMessages() {}

    public static void tell(ServerPlayer player, String message) {
        if (player != null) {
            player.sendSystemMessage(Component.literal(message));
        }
    }

    public static void tell(net.minecraft.world.entity.player.Player player, String message) {
        if (player != null) {
            player.sendSystemMessage(Component.literal(message));
        }
    }

    public static void broadcast(MinecraftServer server, String message) {
        if (server != null) {
            Component comp = Component.literal(message);
            for (ServerPlayer p : server.getPlayerList().getPlayers()) {
                p.sendSystemMessage(comp);
            }
        }
    }

    public static void actionBar(ServerPlayer player, String message) {
        if (player != null) {
            player.displayClientMessage(Component.literal(message), true);
        }
    }

    public static void title(ServerPlayer player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (player != null) {
            player.sendSystemMessage(Component.literal(title + " - " + subtitle));
        }
    }
}
