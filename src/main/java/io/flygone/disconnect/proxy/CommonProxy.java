package io.flygone.disconnect.proxy;

import io.flygone.disconnect.handler.ConfigHandler;
import io.flygone.disconnect.socket.Client;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;


@Mod.EventBusSubscriber
public class CommonProxy {

    public static Configuration config;
    public void preInit(FMLPreInitializationEvent e){

        MinecraftForge.EVENT_BUS.register(this);
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "disconnect.cfg"));
        ConfigHandler.readConfig();
    }

    public void init(FMLInitializationEvent e){

    }

    public void postInit(FMLPostInitializationEvent e) {

        if (config.hasChanged()){
            config.save();
        }
        Client client = null;
        try {
            client = new Client(new URI("wss://gateway.discord.gg/v=6&encoding=json"));
            client.connect();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }


    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){

    }

//    @SubscribeEvent
//    public static void chatMessage(ServerChatEvent event){
//
//        sendGlobalMessage(event);
//        System.out.println(event);
//        System.out.println("User just chatted!");
//    }
//
//    private static void sendGlobalMessage(ServerChatEvent event){
//        PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
//        String purpleText = TextFormatting.DARK_PURPLE + "[Discordbot]";
//        String yellowText = TextFormatting.YELLOW + "[" + event.getUsername() + "]";
//        String whiteText = TextFormatting.WHITE + event.getMessage();
//        String text = String.format("%s %s %s",purpleText, yellowText, whiteText);
//        playerList.sendMessage(new TextComponentString(text));
//    }
}
