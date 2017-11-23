package at.xander.infinibucket.item;

import java.util.List;

import at.xander.infinibucket.main.InfiniBucket;
import at.xander.infinibucket.main.ItemInfBucket;
import net.minecraft.item.Item;

public class IBItems {
	public static Item itemInfiniBucket;

	public static void registerItems(List<Item> registry) {
		registry.add(itemInfiniBucket);
	}

	public static void createItems() {
		itemInfiniBucket = new ItemInfBucket().setUnlocalizedName("infini_bucket").setRegistryName("infini_bucket");

	}
}
