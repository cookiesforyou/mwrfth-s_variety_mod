package net.modpie.runtime;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

/**
 * Registry lookup helpers for Modpie code.
 * <p>Modpie code like {@code @minecraft:diamond} gets translated
 * to calls on this class.</p>
 */
public final class ModpieRegistries {

    private ModpieRegistries() {}

    public static Item item(String registryId) {
        String[] parts = registryId.split(":", 2);
        String ns = parts.length > 1 ? parts[0] : "minecraft";
        String path = parts.length > 1 ? parts[1] : parts[0];
        return BuiltInRegistries.ITEM.get(ResourceLocation.tryBuild(ns, path));
    }

    public static Block block(String registryId) {
        String[] parts = registryId.split(":", 2);
        String ns = parts.length > 1 ? parts[0] : "minecraft";
        String path = parts.length > 1 ? parts[1] : parts[0];
        return BuiltInRegistries.BLOCK.get(ResourceLocation.tryBuild(ns, path));
    }

    public static boolean itemExists(String registryId) {
        return item(registryId) != null;
    }

    public static boolean blockExists(String registryId) {
        return block(registryId) != null;
    }
}
