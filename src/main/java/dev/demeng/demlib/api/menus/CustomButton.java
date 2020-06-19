package dev.demeng.demlib.api.menus;

import dev.demeng.demlib.api.NBTEditor;
import dev.demeng.demlib.api.files.CustomConfig;
import dev.demeng.demlib.api.items.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@AllArgsConstructor
public class CustomButton {

	@Getter
	public int slot;

	@Getter
	public ItemStack stack;

	@Getter
	public Consumer<InventoryClickEvent> actions;

	public void run(InventoryClickEvent e) {
		actions.accept(e);
	}

	public static CustomButton fromConfig(CustomConfig config, String path) {

		final FileConfiguration style = config.getConfig();

		final int slot = CustomMenu.asSlot(style.getInt(path + ".slot"));

		final ItemStack stack = ItemBuilder.build(ItemBuilder.getMaterial(style.getString(path + ".material")),
				style.getString(path + ".name"),
				style.getStringList(path + ".lore"));

		return new CustomButton(slot, stack, null);
	}

	public CustomButton setSlot(int slot) {
		this.slot = slot;
		return this;
	}

	public CustomButton setActions(Consumer<InventoryClickEvent> actions) {
		this.actions = actions;
		return this;
	}

	public CustomButton addData(String key, String value) {
		return new CustomButton(slot, NBTEditor.set(stack, value, key), actions);
	}
}
