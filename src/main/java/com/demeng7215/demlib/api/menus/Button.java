package com.demeng7215.demlib.api.menus;

import com.demeng7215.demlib.api.NBTEditor;
import com.demeng7215.demlib.api.files.CustomConfig;
import com.demeng7215.demlib.api.items.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@AllArgsConstructor
public class Button {

	@Getter
	public int slot;

	@Getter
	public ItemStack stack;

	@Getter
	public Consumer<InventoryClickEvent> actions;

	public void run(InventoryClickEvent e) {
		actions.accept(e);
	}

	public static Button fromConfig(CustomConfig config, String path) {

		final FileConfiguration style = config.getConfig();

		final int slot = Menu.asSlot(style.getInt(path + ".slot"));

		final ItemStack stack = ItemBuilder.build(ItemBuilder.getMaterial(style.getString(path + ".material")),
				style.getString(path + ".name"),
				style.getStringList(path + ".lore"));

		return new Button(slot, stack, null);
	}

	public Button setSlot(int slot) {
		this.slot = slot;
		return this;
	}

	public Button setActions(Consumer<InventoryClickEvent> actions) {
		this.actions = actions;
		return this;
	}

	public Button addData(String key, String value) {
		return new Button(slot, NBTEditor.set(stack, value, key), actions);
	}
}
