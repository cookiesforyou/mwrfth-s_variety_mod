package net.modpie.runtime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.Difficulty;

/**
 * Helper methods for HUD overlay rendering.
 * Provides beginner-friendly access to game state for display in HUDs.
 */
public final class ModpieHudHelper {

    private ModpieHudHelper() {}

    /**
     * Returns the current game time in ticks (total world ticks).
     */
    public static long getGameTime(Minecraft mc) {
        if (mc.level != null) {
            return mc.level.getGameTime();
        }
        return 0;
    }

    /**
     * Returns the current day time (0-24000, where 0=midnight, 6000=dawn, 12000=noon, 18000=dusk).
     */
    public static long getDayTime(Minecraft mc) {
        if (mc.level != null) {
            return mc.level.getDayTime();
        }
        return 0;
    }

    /**
     * Returns the current day number (game time / 24000).
     */
    public static long getDay(Minecraft mc) {
        if (mc.level != null) {
            return mc.level.getDayTime() / 24000L;
        }
        return 0;
    }

    /**
     * Returns the current tick count from the level.
     */
    public static long getTicks(Minecraft mc) {
        if (mc.level != null) {
            return mc.level.getGameTime();
        }
        return 0;
    }

    /**
     * Returns the player's display name.
     */
    public static String getPlayerName(Minecraft mc) {
        if (mc.player != null) {
            return mc.player.getDisplayName().getString();
        }
        return "";
    }

    /**
     * Returns the player's current health (float).
     */
    public static float getPlayerHealth(Minecraft mc) {
        if (mc.player != null) {
            return mc.player.getHealth();
        }
        return 0f;
    }

    /**
     * Returns the player's XP level.
     */
    public static int getPlayerLevel(Minecraft mc) {
        if (mc.player != null) {
            return mc.player.experienceLevel;
        }
        return 0;
    }

    /**
     * Returns the player's food level.
     */
    public static int getPlayerFood(Minecraft mc) {
        if (mc.player != null) {
            return mc.player.getFoodData().getFoodLevel();
        }
        return 0;
    }

    /**
     * Returns the current FPS counter.
     */
    public static int getFps(Minecraft mc) {
        return mc.getFps();
    }

    /**
     * Formats game time as a human-readable clock string (HH:MM).
     * Day time ranges 0-24000, where 0=midnight.
     */
    public static String formatClock(long dayTime) {
        long hours = (dayTime / 1000 + 6) % 24;
        long minutes = (dayTime % 1000) * 60 / 1000;
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String getBiome(Minecraft mc) {
        if (mc.level != null && mc.player != null) {
            Holder<Biome> holder = mc.level.getBiome(mc.player.blockPosition());
            return holder.unwrap().map(
                ref -> ref.location().getPath(),
                biome -> "unknown"
            );
        }
        return "unknown";
    }

    public static String getFacing(Minecraft mc) {
        if (mc.player == null) return "";
        float yaw = mc.player.getYRot();
        String[] directions = {"South", "Southwest", "West", "Northwest", "North", "Northeast", "East", "Southeast"};
        int index = (int) Math.round(((yaw % 360) / 45.0)) & 7;
        return directions[index];
    }

    public static String getDifficulty(Minecraft mc) {
        if (mc.level != null) {
            Difficulty d = mc.level.getDifficulty();
            return d.getKey();
        }
        return "unknown";
    }

    private static long[] clickTimes = new long[20];
    private static int clickIdx = 0;
    private static boolean wasAttackDown = false;

    public static void tickClicks(Minecraft mc) {
        if (mc.player != null && mc.options.keyAttack.isDown() && !wasAttackDown) {
            clickTimes[clickIdx] = System.currentTimeMillis();
            clickIdx = (clickIdx + 1) % clickTimes.length;
        }
        wasAttackDown = mc.options.keyAttack.isDown();
    }

    public static int getCps(Minecraft mc) {
        tickClicks(mc);
        long now = System.currentTimeMillis();
        int count = 0;
        for (long t : clickTimes) {
            if (t > 0 && now - t < 1000) count++;
        }
        return count;
    }

    public static float getArmor(Minecraft mc) {
        if (mc.player != null) return mc.player.getArmorValue();
        return 0f;
    }

    public static int getAir(Minecraft mc) {
        if (mc.player != null) return mc.player.getAirSupply();
        return 0;
    }

    public static int getPosX(Minecraft mc) {
        if (mc.player != null) return mc.player.blockPosition().getX();
        return 0;
    }

    public static int getPosY(Minecraft mc) {
        if (mc.player != null) return mc.player.blockPosition().getY();
        return 0;
    }

    public static int getPosZ(Minecraft mc) {
        if (mc.player != null) return mc.player.blockPosition().getZ();
        return 0;
    }

    public static String getCoords(Minecraft mc) {
        if (mc.player == null) return "0, 0, 0";
        return getPosX(mc) + ", " + getPosY(mc) + ", " + getPosZ(mc);
    }

    public static double getSpeed(Minecraft mc) {
        if (mc.player == null) return 0;
        double dx = mc.player.getDeltaMovement().x;
        double dz = mc.player.getDeltaMovement().z;
        return Math.sqrt(dx * dx + dz * dz) * 20.0;
    }

    public static String getTimeOfDay(Minecraft mc) {
        if (mc.level == null) return "";
        long dayTime = mc.level.getDayTime() % 24000;
        if (dayTime < 6000) return "Morning";
        if (dayTime < 12000) return "Day";
        if (dayTime < 18000) return "Evening";
        return "Night";
    }

    public static String getWeather(Minecraft mc) {
        if (mc.level == null) return "Clear";
        boolean raining = mc.level.isRaining();
        boolean thundering = mc.level.isThundering();
        if (thundering) return "Thunderstorm";
        if (raining) return "Rain";
        return "Clear";
    }

    public static boolean isRaining(Minecraft mc) {
        return mc.level != null && mc.level.isRaining();
    }

    public static boolean isThundering(Minecraft mc) {
        return mc.level != null && mc.level.isThundering();
    }
}
