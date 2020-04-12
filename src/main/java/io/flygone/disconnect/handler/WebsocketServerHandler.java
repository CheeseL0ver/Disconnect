package io.flygone.disconnect.handler;

import io.flygone.disconnect.gateway.message.CreateMessage;
import io.flygone.disconnect.proxy.CommonProxy;
import io.flygone.disconnect.proxy.ServerProxy;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class WebsocketServerHandler {

    public static void sendGlobalMessage(CreateMessage message){

        if (message.d.content.contains(ServerProxy.lastMessageSent)){
            return;
        }
            PlayerList playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList();
            String purpleText = TextFormatting.DARK_PURPLE + "[Discordbot]";
            String yellowText = TextFormatting.YELLOW + "[" + message.d.author.username + "]";
            String whiteText = TextFormatting.WHITE + message.d.content;
            String text = String.format("%s %s %s",purpleText, yellowText, whiteText);
            playerList.sendMessage(new TextComponentString(text));


    }
}
