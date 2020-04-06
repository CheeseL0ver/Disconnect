package io.flygone.disconnect.proxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.SERVER)
public class ServerProxy extends CommonProxy {

//    @SubscribeEvent
//    public static void messageCheck(TickEvent.ServerTickEvent event){
//        System.out.println(event);
//        System.out.println("Server ticked");
//    }
}
