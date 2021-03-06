package io.flygone.disconnect;

import io.flygone.disconnect.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Disconnect.MODID, name = Disconnect.NAME, version = Disconnect.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
public class Disconnect
{
    public static final String MODID = "disconnect";
    public static final String NAME = "Disconnect";
    public static final String VERSION = "1.2";

    @SidedProxy(serverSide = "io.flygone.disconnect.proxy.ServerProxy")
    public static CommonProxy proxy;
    public static Logger logger;

    public Disconnect(){
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event){
//        event.registerServerCommand(new DiscordCommand());
    }
}
