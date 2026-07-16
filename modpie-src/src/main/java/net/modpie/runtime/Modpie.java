package net.modpie.runtime;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.MinecraftServer;

/**
 * Convenience entry point that provides static access to common runtime helpers.
 * <p>Modpie code can reference these directly: {@code Modpie.player(e.player)},
 * {@code Modpie.world(level)}, etc.</p>
 */
public final class Modpie {

    private Modpie() {}

    public static ModpiePlayer player(Player player) {
        return new ModpiePlayer(player);
    }

    public static ModpieWorld world(ServerLevel level) {
        return new ModpieWorld(level);
    }

    public static void broadcast(MinecraftServer server, String message) {
        ModpieMessages.broadcast(server, message);
    }

    public static int random(int min, int max) {
        return ModpieRandom.randomInt(min, max);
    }

    public static int random(int max) {
        return ModpieRandom.randomInt(max);
    }

    public static boolean chance(double probability) {
        return ModpieRandom.chance(probability);
    }
}
