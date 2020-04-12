package io.flygone.disconnect;

import com.google.common.collect.Lists;
import io.flygone.disconnect.proxy.ServerProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class DiscordCommand extends CommandBase {

    private final List<String> aliases;

    public DiscordCommand(){
        aliases = Lists.newArrayList(Disconnect.MODID, "discord", "dis");
    }
    @Override
    public String getName() {
        return "dis";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "dis <message>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1)
            return;

        String message = "";
        for (String s : args){
            message += s;
        }

        try {
            ServerProxy.httpClient.sendPost(String.format("[%s] %s", sender.getName(), message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
