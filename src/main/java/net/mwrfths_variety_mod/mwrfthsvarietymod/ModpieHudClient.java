package net.mwrfths_variety_mod.mwrfthsvarietymod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class ModpieHudClient {

    public static void register(IEventBus modBus) {
        modBus.addListener(RegisterGuiLayersEvent.class, event -> {
            ClockHud.register(event);
            TimeofdayHud.register(event);
            CpsHud.register(event);
            CurrentweatherHud.register(event);
            CurrentbiomeHud.register(event);
        });
    }
}
