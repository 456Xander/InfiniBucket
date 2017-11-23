package at.xander.infinibucket.main;

import at.xander.infinibucket.item.IBItems;
import at.xander.infinibucket.item.RegistryHandler;
import at.xander.infinibucket.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = InfiniBucket.MODID, name = "Infini Bucket", version = "1.0.0")
public class InfiniBucket {
	
	public static final String MODID = "infinibucket";

	@Instance
	public static InfiniBucket instance;

	@SidedProxy(serverSide = "at.xander.infinibucket.proxy.ServerProxy", clientSide = "at.xander.infinibucket.proxy.ClientProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new RegistryHandler());
		IBItems.createItems();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		System.out.println("---------------------------------------------------");
		System.out.println("InfiniBucket Init Begin");
		System.out.println("---------------------------------------------------");
		InfiniBucket.proxy.registerTexture(IBItems.itemInfiniBucket);
	}
}
