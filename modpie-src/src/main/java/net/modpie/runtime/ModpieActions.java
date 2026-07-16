package net.modpie.runtime;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

/**
 * Beginner-friendly item & block manipulation helpers.
 * <p>Supports Modpie verbs like {@code drop} and {@code spawn}.</p>
 */
public final class ModpieActions {

    private ModpieActions() {}

    public static void dropItem(ServerLevel level, String registryId, int count, double x, double y, double z) {
        var item = ModpieRegistries.item(registryId);
        if (item == null) return;
        ItemStack stack = new ItemStack(item, count);
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        level.addFreshEntity(entity);
    }

    public static void dropItem(ServerLevel level, String registryId, double x, double y, double z) {
        dropItem(level, registryId, 1, x, y, z);
    }

    public static void spawnEntity(ServerLevel level, String entityTypeId, double x, double y, double z) {
        var world = new ModpieWorld(level);
        world.spawnEntity(entityTypeId, x, y, z);
    }

    public static void strikeLightning(ServerLevel level, double x, double y, double z) {
        var lightning = net.minecraft.world.entity.EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.moveTo(x, y, z);
            level.addFreshEntity(lightning);
        }
    }

    public static void explosion(ServerLevel level, double x, double y, double z, float power) {
        level.explode(null, x, y, z, power, ServerLevel.ExplosionInteraction.MOB);
    }

    public static void setBlock(ServerLevel level, String registryId, double x, double y, double z) {
        var world = new ModpieWorld(level);
        world.setBlock(registryId, x, y, z);
    }

    public static void breakBlock(ServerLevel level, double x, double y, double z) {
        var world = new ModpieWorld(level);
        world.breakBlock(x, y, z);
    }
}
