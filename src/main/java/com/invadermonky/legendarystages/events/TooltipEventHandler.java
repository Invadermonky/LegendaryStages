package com.invadermonky.legendarystages.events;

import com.anthonyhilyard.legendarytooltips.LegendaryTooltipsConfig;
import com.invadermonky.legendarystages.LegendaryStages;
import com.invadermonky.legendarystages.config.ConfigHandlerLS;
import gnu.trove.map.hash.THashMap;
import net.darkhax.itemstages.ItemStages;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = LegendaryStages.MOD_ID)
public class TooltipEventHandler {
    public static THashMap<String, Integer> borderStages = new THashMap<>(ConfigHandlerLS.stageBorderIndex.length);

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPreTooltipEvent(RenderTooltipEvent.Pre event) {
        ItemStack stack = event.getStack();
        String stage = ItemStages.getStage(stack);
        if(borderStages.containsKey(stage)) {
            putStageFrameLevel(stack);
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static Map<ItemStack, Integer> getFrameLevelCache() {
        try {
            Field frameLevelCacheField = LegendaryTooltipsConfig.class.getDeclaredField("frameLevelCache");
            frameLevelCacheField.setAccessible(true);
            return (Map<ItemStack, Integer>) frameLevelCacheField.get(LegendaryTooltipsConfig.INSTANCE);
        } catch (Exception ignored) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static int getFrameLevel(ItemStack stack) {
        int stageFrame = borderStages.get(ItemStages.getStage(stack));
        int checkFrame = LegendaryTooltipsConfig.INSTANCE.getFrameLevelForItem(stack);

        if(checkFrame >= 0 && checkFrame < 16) {
            try {
                Field framePrioritiesField = LegendaryTooltipsConfig.class.getDeclaredField("framePriorities");
                framePrioritiesField.setAccessible(true);
                List<Integer> framePriorities = (List<Integer>) framePrioritiesField.get(LegendaryTooltipsConfig.INSTANCE);
                if (framePriorities.contains(checkFrame) && framePriorities.indexOf(checkFrame) < framePriorities.indexOf(stageFrame)) {
                    stageFrame = checkFrame;
                }
            } catch (Exception ignored) {}
        }

        return stageFrame;
    }

    private static void putStageFrameLevel(ItemStack stack) {
        Map<ItemStack, Integer> frameLevelCache = getFrameLevelCache();
        if(frameLevelCache != null && !frameLevelCache.containsKey(stack)) {
            frameLevelCache.put(stack, getFrameLevel(stack));
        }
    }
}
