package com.invadermonky.legendarystages.config;

import com.invadermonky.legendarystages.LegendaryStages;
import com.invadermonky.legendarystages.events.TooltipEventHandler;
import com.invadermonky.legendarystages.util.LogHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

@Config(modid = LegendaryStages.MOD_ID)
public class ConfigHandlerLS {
    @Comment("Defines the border used for each game stage. Border priorities are inherited from the Legendary Tooltips\n" +
            "configuration, allowing stage borders to be overwritten when applicable. Format: <stage_name>;<border_index>\n" +
            "Borders are indexed starting from 0, oriented from top to bottom, then left to right.\n" +
            "Examples:\n" +
            "  stage_0;0\n" +
            "  stage_1;1\n" +
            "  stage_4;2")
    public static String[] stageBorderIndex = new String[] {};

    @Mod.EventBusSubscriber(modid = LegendaryStages.MOD_ID)
    public static class ConfigListener {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(LegendaryStages.MOD_ID)) {
                ConfigManager.sync(LegendaryStages.MOD_ID, Type.INSTANCE);
                syncConfig();
            }
        }

        public static void syncConfig() {
            TooltipEventHandler.borderStages.clear();
            for(String stageBorder : stageBorderIndex) {
                String[] split = stageBorder.split(";");
                if(split.length == 2 && split[1].matches("\\d+")) {
                    TooltipEventHandler.borderStages.put(split[0], Integer.valueOf(split[1]));
                } else {
                    LogHelper.error("Invalid config entry: " + stageBorder);
                }
            }

            Map<ItemStack, Integer> frameLevelCache = TooltipEventHandler.getFrameLevelCache();
            if(frameLevelCache != null) {
                frameLevelCache.clear();
            }
        }
    }
}
