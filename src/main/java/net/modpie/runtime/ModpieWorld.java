package net.modpie.runtime;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Beginner-friendly wrapper around {@link ServerLevel}.
 * <p>Modpie code like {@code world.online_players} or
 * {@code world.spawn_entity(@minecraft:zombie, x, y, z)} gets translated
 * to calls on this class.</p>
 */
public final class ModpieWorld {
    private final ServerLevel level;

    public ModpieWorld(ServerLevel level) {
        this.level = level;
    }

    public ServerLevel raw() { return level; }

    // --- Players ---

    public List<ModpiePlayer> onlinePlayers() {
        return level.players().stream()
            .map(p -> new ModpiePlayer((Player) p))
            .collect(Collectors.toList());
    }

    public List<ServerPlayer> rawPlayers() {
        return level.players();
    }

    // --- Blocks ---

    public BlockState getBlock(double x, double y, double z) {
        return level.getBlockState(BlockPos.containing(x, y, z));
    }

    public BlockState getBlock(BlockPos pos) {
        return level.getBlockState(pos);
    }

    public void setBlock(double x, double y, double z, Block block) {
        level.setBlock(BlockPos.containing(x, y, z), block.defaultBlockState(), 3);
    }

    public void setBlock(BlockPos pos, Block block) {
        level.setBlock(pos, block.defaultBlockState(), 3);
    }

    public void setBlock(String registryId, double x, double y, double z) {
        Block block = resolveBlock(registryId);
        if (block != null) setBlock(x, y, z, block);
    }

    public void breakBlock(double x, double y, double z) {
        level.destroyBlock(BlockPos.containing(x, y, z), true);
    }

    public void breakBlock(BlockPos pos) {
        level.destroyBlock(pos, true);
    }

    // --- Entities ---

    public Entity spawnEntity(String entityTypeId, double x, double y, double z) {
        EntityType<?> type = resolveEntityType(entityTypeId);
        if (type == null) return null;
        Entity entity = type.create(level);
        if (entity != null) {
            entity.setPos(x, y, z);
            level.addFreshEntity(entity);
        }
        return entity;
    }

    public Entity spawnEntity(String entityTypeId, Vec3 pos) {
        return spawnEntity(entityTypeId, pos.x, pos.y, pos.z);
    }

    // --- Time & weather ---

    public long time() {
        return level.getDayTime();
    }

    public void setTime(long time) {
        level.setDayTime(time);
    }

    public boolean isDay() {
        return level.isDay();
    }

    public boolean isNight() {
        return level.isNight();
    }

    public boolean isRaining() {
        return level.isRaining();
    }

    public boolean isThundering() {
        return level.isThundering();
    }

    public void setRaining(boolean raining) {
        level.setWeatherParameters(0, 0, raining, level.isThundering());
    }

    // --- Utility ---

    public BlockPos spawnPoint() {
        return level.getSharedSpawnPos();
    }

    public Vec3 spawnVec3() {
        var pos = level.getSharedSpawnPos();
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public ModpiePlayer nearestPlayer(double x, double y, double z) {
        Player nearest = level.getNearestPlayer(x, y, z, 100, false);
        return nearest != null ? new ModpiePlayer(nearest) : null;
    }

    // --- Resolution helpers ---

    private static Block resolveBlock(String registryId) {
        String[] parts = registryId.split(":", 2);
        String ns = parts.length > 1 ? parts[0] : "minecraft";
        String path = parts.length > 1 ? parts[1] : parts[0];
        return BuiltInRegistries.BLOCK.get(ResourceLocation.tryBuild(ns, path));
    }

    private static EntityType<?> resolveEntityType(String registryId) {
        String[] parts = registryId.split(":", 2);
        String ns = parts.length > 1 ? parts[0] : "minecraft";
        String path = parts.length > 1 ? parts[1] : parts[0];
        return BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryBuild(ns, path));
    }
}
