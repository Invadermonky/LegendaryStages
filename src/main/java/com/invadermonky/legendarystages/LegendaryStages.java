package com.invadermonky.legendarystages;

import com.invadermonky.legendarystages.config.ConfigHandlerLS;
import com.invadermonky.legendarystages.proxy.CommonProxy;
import com.invadermonky.legendarystages.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = LegendaryStages.MOD_ID,
        name = LegendaryStages.MOD_NAME,
        version = LegendaryStages.VERSION,
        acceptedMinecraftVersions = LegendaryStages.MC_VERSION,
        dependencies = LegendaryStages.DEPENDENCIES
)
public class LegendaryStages {
    public static final String MOD_ID = "legendarystages";
    public static final String MOD_NAME = "Legendary Stages";
    public static final String VERSION = "1.12.2=1.0.0";
    public static final String MC_VERSION = "[1.12.2]";
    public static final String DEPENDENCIES =
            "required-after:legendarytooltips" +
            ";required-after:itemstages";

    public static final String ProxyClientClass = "com.invadermonky." + MOD_ID + ".proxy.ClientProxy";
    public static final String ProxyServerClass = "com.invadermonky." + MOD_ID + ".proxy.CommonProxy";

    @Mod.Instance(MOD_ID)
    public LegendaryStages instance;

    @SidedProxy(clientSide = ProxyClientClass, serverSide = ProxyServerClass)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.info("Starting " + MOD_NAME);
        proxy.preInit(event);
        LogHelper.debug("Finished preInit phase.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        LogHelper.debug("Finished init phase.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        ConfigHandlerLS.ConfigListener.syncConfig();
        LogHelper.debug("Finished postInit phase.");
    }}
