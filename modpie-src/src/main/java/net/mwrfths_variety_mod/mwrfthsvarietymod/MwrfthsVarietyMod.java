package net.mwrfths_variety_mod.mwrfthsvarietymod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;
import net.modpie.runtime.*;
import java.util.*;

@Mod("mwrfths_variety_mod")
public class MwrfthsVarietyMod {

    public static final String MOD_NAME = "Mwrfth's Variety Mod";
    public static final String MOD_VERSION = "1.0.0";

    private static final DeferredRegister.Items ITEMS =
        DeferredRegister.createItems("mwrfths_variety_mod");
    private static final DeferredRegister.Blocks BLOCKS =
        DeferredRegister.createBlocks("mwrfths_variety_mod");

    public static final DeferredHolder<Block, ReinforcedCobblestone> REINFORCED_COBBLESTONE =
        BLOCKS.register("reinforced_cobblestone", ReinforcedCobblestone::new);

    public static final DeferredHolder<Item, net.minecraft.world.item.BlockItem> REINFORCED_COBBLESTONE_ITEM =
        ITEMS.registerSimpleBlockItem(REINFORCED_COBBLESTONE);

    public static final DeferredHolder<Block, ReinforcedCobblestoneSlab> REINFORCED_COBBLESTONE_SLAB =
        BLOCKS.register("reinforced_cobblestone_slab", ReinforcedCobblestoneSlab::new);

    public static final DeferredHolder<Item, net.minecraft.world.item.BlockItem> REINFORCED_COBBLESTONE_SLAB_ITEM =
        ITEMS.registerSimpleBlockItem(REINFORCED_COBBLESTONE_SLAB);

    public static final DeferredHolder<Block, ReinforcedCobblestoneStairs> REINFORCED_COBBLESTONE_STAIRS =
        BLOCKS.register("reinforced_cobblestone_stairs", ReinforcedCobblestoneStairs::new);

    public static final DeferredHolder<Item, net.minecraft.world.item.BlockItem> REINFORCED_COBBLESTONE_STAIRS_ITEM =
        ITEMS.registerSimpleBlockItem(REINFORCED_COBBLESTONE_STAIRS);

    public static final DeferredHolder<Block, ReinforcedCobblestoneWall> REINFORCED_COBBLESTONE_WALL =
        BLOCKS.register("reinforced_cobblestone_wall", ReinforcedCobblestoneWall::new);

    public static final DeferredHolder<Item, net.minecraft.world.item.BlockItem> REINFORCED_COBBLESTONE_WALL_ITEM =
        ITEMS.registerSimpleBlockItem(REINFORCED_COBBLESTONE_WALL);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "mwrfths_variety_mod");

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB =
        CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.literal("Mwrfth's Variety Mod"))
            .icon(() -> new ItemStack(REINFORCED_COBBLESTONE_ITEM.get()))
            .displayItems((params, output) -> {
                output.accept(REINFORCED_COBBLESTONE_ITEM.get());
                output.accept(REINFORCED_COBBLESTONE_SLAB_ITEM.get());
                output.accept(REINFORCED_COBBLESTONE_STAIRS_ITEM.get());
                output.accept(REINFORCED_COBBLESTONE_WALL_ITEM.get());
            })
            .build());

    public MwrfthsVarietyMod(IEventBus modBus, net.neoforged.fml.ModContainer modContainer) {
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        CREATIVE_TABS.register(modBus);
        ModpieHudClient.register(modBus);
        ModpieConfig.register(modContainer);
        modContainer.registerExtensionPoint(net.neoforged.neoforge.client.gui.IConfigScreenFactory.class, net.neoforged.neoforge.client.gui.ConfigurationScreen::new);
        onInit();
    }

    private void onInit() {
        var neoBus = NeoForge.EVENT_BUS;

    }

}
