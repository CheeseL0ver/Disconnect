package io.flygone.disconnect.proxy;

import io.flygone.disconnect.http.HttpClient;
import io.flygone.disconnect.socket.Client;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;

import java.net.URI;
import java.net.URISyntaxException;

import static io.flygone.disconnect.Disconnect.logger;

@Mod.EventBusSubscriber(Side.SERVER)
public class ServerProxy extends CommonProxy {

    public static HttpClient httpClient;
    public static String lastMessageSent = "";
    public static Client client = null;

    @Override
    public void postInit(FMLPostInitializationEvent e) {

        super.postInit(e);
        if (config.hasChanged()){
            config.save();
        }

        try {
            client = new Client(new URI("wss://gateway.discord.gg/v=6&encoding=json"));
            client.connect();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }

        httpClient = new HttpClient();


    }

    @SubscribeEvent
    public static void handleLoginLogout (PlayerEvent event) throws Exception {
        if (event instanceof PlayerEvent.PlayerLoggedInEvent){
            httpClient.sendPost(String.format("[%s] joined the server!", event.player.getName()));
        }
        else if (event instanceof PlayerEvent.PlayerLoggedOutEvent){
            httpClient.sendPost(String.format("[%s] left the server!", event.player.getName()));
        }
    }

    @SubscribeEvent
    public static void chatMessage(ServerChatEvent event){

        lastMessageSent = event.getMessage();
        try {
            httpClient.sendPost(String.format("[%s] %s", event.getUsername(), event.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public static void checkClient(TickEvent event){
        if (client.isClosed()){
            logger.log(Level.DEBUG, "The connection is closed. Starting a new client...");
            try {
                client = new Client(new URI("wss://gateway.discord.gg/v=6&encoding=json"));
                client.connect();
                logger.log(Level.DEBUG, "The connect made!");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
