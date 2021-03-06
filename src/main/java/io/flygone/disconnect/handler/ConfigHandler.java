package io.flygone.disconnect.handler;

import io.flygone.disconnect.Disconnect;
import io.flygone.disconnect.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class ConfigHandler {

    private static final String CATEGORY_DISCORD = "discord";

    public static String discordToken = "";
    public static String discordUsername = "";
    public static String discordChannelId = "";
    public static String botSecret = "";

    public static void readConfig(){
        Configuration cfg = CommonProxy.config;
        try{
            cfg.load();
            initDiscordConfig(cfg);
        }
        catch (Exception ex){
            Disconnect.logger.log(Level.ERROR, "Problem loading config file!", ex);
        }
        finally{
            if (cfg.hasChanged()){
                cfg.save();
            }
        }

    }

    private static void initDiscordConfig(Configuration cfg){
        cfg.addCustomCategoryComment(CATEGORY_DISCORD, "Discord configuration");
        discordToken = cfg.getString("token", CATEGORY_DISCORD, discordToken, "Set your Discord bot token here!");
        discordChannelId = cfg.getString("channel_id", CATEGORY_DISCORD, discordChannelId, "Set your Discord channel_id here!");
        botSecret = cfg.getString("bot_secret", CATEGORY_DISCORD, botSecret, "Set your Discord bot's secret here!");
    }
}
