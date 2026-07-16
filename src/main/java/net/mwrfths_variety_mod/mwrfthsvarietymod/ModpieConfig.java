package net.mwrfths_variety_mod.mwrfthsvarietymod;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModpieConfig {

    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CLOCK_ENABLED = COMMON_BUILDER
        .comment("clock_Enabled")
        .translation("mwrfths_variety_mod.config.clock_Enabled")
        .define("clock_Enabled", true);

    public static final ModConfigSpec.BooleanValue SHOW_TIME_OF_DAY = COMMON_BUILDER
        .comment("show_TimeOfDay")
        .translation("mwrfths_variety_mod.config.show_TimeOfDay")
        .define("show_TimeOfDay", true);

    public static final ModConfigSpec.BooleanValue SHOW_CPS = COMMON_BUILDER
        .comment("show_CPS")
        .translation("mwrfths_variety_mod.config.show_CPS")
        .define("show_CPS", false);

    public static final ModConfigSpec.BooleanValue SHOW_CURRENT_WEATHER = COMMON_BUILDER
        .comment("show_CurrentWeather")
        .translation("mwrfths_variety_mod.config.show_CurrentWeather")
        .define("show_CurrentWeather", true);

    public static final ModConfigSpec.BooleanValue SHOW_CURRENT_BIOME = COMMON_BUILDER
        .comment("show_CurrentBiome")
        .translation("mwrfths_variety_mod.config.show_CurrentBiome")
        .define("show_CurrentBiome", true);

    public static final ModConfigSpec COMMON_SPEC = COMMON_BUILDER.build();

    public static void register(net.neoforged.fml.ModContainer container) {
        container.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, COMMON_SPEC);
    }
}
