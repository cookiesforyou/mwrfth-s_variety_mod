package net.mwrfths_variety_mod.mwrfthsvarietymod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.modpie.runtime.ModpieHudHelper;

@OnlyIn(Dist.CLIENT)
public class CpsHud implements LayeredDraw.Layer {

    public static final CpsHud INSTANCE = new CpsHud();

    @Override
    public void render(GuiGraphics gui, DeltaTracker deltaTracker) {
        if (!(ModpieConfig.SHOW_CPS.get())) return;
        Minecraft mc = Minecraft.getInstance();
        var font = mc.font;
        int guiWidth = mc.getWindow().getGuiScaledWidth();
        int guiHeight = mc.getWindow().getGuiScaledHeight();
        String text = String.valueOf("CPS " + String.valueOf(ModpieHudHelper.getCps(mc)));
        int x = guiWidth - font.width(text) - 10 + 0;
        int y = 10 + 24;
        gui.drawString(font, Component.literal(text), x, y, 0xffffff, true);
    }

    public static void register(RegisterGuiLayersEvent event) {
        event.registerAboveAll(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("mwrfths_variety_mod", "cps"), INSTANCE);
    }
}
