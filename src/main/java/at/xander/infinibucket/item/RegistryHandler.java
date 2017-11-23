package at.xander.infinibucket.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryHandler {
	@SubscribeEvent
	public void onItemRegistry(Register<Item> event) {
		List<Item> items = new ArrayList<>();
		IBItems.registerItems(items);
		items.forEach(i -> event.getRegistry().register(i));
	}
}
