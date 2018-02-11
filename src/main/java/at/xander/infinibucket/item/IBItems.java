package at.xander.infinibucket.item;

import java.util.List;

import at.xander.infinibucket.main.InfiniBucket;
import at.xander.infinibucket.main.ItemInfBucket;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class IBItems {
	public static Item itemInfiniBucket;

	public static void registerItems(List<Item> registry) {
		if (itemInfiniBucket == null) {
			new ItemInfBucket().setUnlocalizedName("infini_bucket").setRegistryName("infini_bucket");
			InfiniBucket.logger.warn(
					"The infini Bucket item was not initialised before registerItems event! That should not be possible");
		}
		registry.add(itemInfiniBucket);
	}

	public static void createItems(Configuration conf) {
		if (itemInfiniBucket == null) {
			itemInfiniBucket = new ItemInfBucket().setUnlocalizedName("infini_bucket").setRegistryName("infini_bucket");
		}
	}
}
