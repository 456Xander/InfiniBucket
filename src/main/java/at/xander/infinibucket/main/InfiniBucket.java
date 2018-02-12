package at.xander.infinibucket.main;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import at.xander.infinibucket.item.IBItems;
import at.xander.infinibucket.item.RegistryHandler;
import at.xander.infinibucket.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = InfiniBucket.MODID, name = "Infini Bucket", version = "1.0.1")
public class InfiniBucket {
	public static final boolean DEBUG = false;

	public static final String MODID = "infinibucket";

	@Instance
	public static InfiniBucket instance;

	@SidedProxy(serverSide = "at.xander.infinibucket.proxy.ServerProxy", clientSide = "at.xander.infinibucket.proxy.ClientProxy")
	public static ServerProxy proxy;

	public static Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		IBConfigData data = new IBConfigData();
		Configuration conf = new Configuration(e.getSuggestedConfigurationFile());
		data.capacity = conf.getInt("capacity", "bucket", 0, 0, Integer.MAX_VALUE,
				"how many buckets of water an infinite bucket stores (0 = infinity)");
		IBItems.createItems(data);
		MinecraftForge.EVENT_BUS.register(new RegistryHandler());
		conf.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		System.out.println("---------------------------------------------------");
		System.out.println("InfiniBucket Init Begin");
		System.out.println("---------------------------------------------------");
		logger.log(Level.INFO, "Hello");
		InfiniBucket.proxy.registerTexture(IBItems.itemInfiniBucket, 0);
	}
}
