package net.modpie.runtime;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

/**
 * Beginner-friendly wrapper around {@link Player}.
 * <p>Modpie code like {@code player.heal(10)} or {@code player.give_xp(5)}
 * gets translated to calls on this class.</p>
 */
public final class ModpiePlayer {
    private final Player player;

    public ModpiePlayer(Player player) {
        this.player = player;
    }

    public Player raw() { return player; }

    // --- Health & damage ---

    public void heal(double amount) {
        player.heal((float) amount);
    }

    public void damage(double amount) {
        player.hurt(player.damageSources().generic(), (float) amount);
    }

    public double health() {
        return player.getHealth();
    }

    public void setHealth(double amount) {
        player.setHealth((float) amount);
    }

    public double maxHealth() {
        return player.getMaxHealth();
    }

    // --- Experience ---

    public void giveXp(int amount) {
        player.giveExperiencePoints(amount);
    }

    public int xpLevel() {
        return player.experienceLevel;
    }

    public void setXpLevel(int level) {
        player.experienceLevel = level;
    }

    // --- Inventory ---

    public void give(Item item, int count) {
        player.getInventory().add(new ItemStack(item, count));
    }

    public void give(Item item) {
        give(item, 1);
    }

    public void give(String registryId, int count) {
        Item item = resolveItem(registryId);
        if (item != null) give(item, count);
    }

    public void give(String registryId) {
        give(registryId, 1);
    }

    public boolean has(Item item, int count) {
        return player.getInventory().countItem(item) >= count;
    }

    public void clearInventory() {
        player.getInventory().clearContent();
    }

    // --- Position & movement ---

    public double x() { return player.getX(); }
    public double y() { return player.getY(); }
    public double z() { return player.getZ(); }

    public void teleport(double x, double y, double z) {
        player.teleportTo(x, y, z);
    }

    public void teleport(ServerLevel level, double x, double y, double z) {
        if (player instanceof ServerPlayer sp) {
            sp.teleportTo(level, x, y, z, java.util.Set.of(), player.getYRot(), player.getXRot());
        } else {
            teleport(x, y, z);
        }
    }

    public void teleport(Vec3 pos) {
        teleport(pos.x, pos.y, pos.z);
    }

    // --- Messaging ---

    public void sendMessage(String message) {
        player.sendSystemMessage(Component.literal(message));
    }

    public void sendActionBar(String message) {
        player.displayClientMessage(Component.literal(message), true);
    }

    // --- Food & saturation ---

    public int foodLevel() {
        return player.getFoodData().getFoodLevel();
    }

    public void setFoodLevel(int level) {
        player.getFoodData().setFoodLevel(level);
    }

    public void feed(int amount) {
        player.getFoodData().eat(amount, 0.5f);
    }

    // --- Status effects ---

    public void addEffect(String effectId, int duration, int amplifier) {
        // Effect resolution would go here
        // player.addEffect(new MobEffectInstance(...));
    }

    // --- World ---

    public ServerLevel world() {
        if (player instanceof ServerPlayer sp) {
            return sp.serverLevel();
        }
        return (ServerLevel) player.level();
    }

    public BlockPos blockPos() {
        return player.blockPosition();
    }

    // --- Properties ---

    public String name() {
        return player.getName().getString();
    }

    public boolean isOnline() {
        return !player.isRemoved();
    }

    public boolean isSneaking() {
        return player.isShiftKeyDown();
    }

    public boolean isFlying() {
        return player.getAbilities().flying;
    }

    public boolean isCreative() {
        return player.isCreative();
    }

    // --- Utility ---

    private static Item resolveItem(String registryId) {
        String[] parts = registryId.split(":", 2);
        String ns = parts.length > 1 ? parts[0] : "minecraft";
        String path = parts.length > 1 ? parts[1] : parts[0];
        return BuiltInRegistries.ITEM.get(ResourceLocation.tryBuild(ns, path));
    }
}
