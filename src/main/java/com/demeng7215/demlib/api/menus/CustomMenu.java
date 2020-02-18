package com.demeng7215.demlib.api.menus;

import com.demeng7215.demlib.api.Common;
import com.demeng7215.demlib.api.items.ItemBuilder;
import com.demeng7215.demlib.api.messages.MessageUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class CustomMenu {

	@Getter(AccessLevel.PRIVATE)
	private final UUID uuid;

	@Getter
	private final Inventory inventory;

	@Getter
	private final Map<Integer, Consumer<InventoryClickEvent>> actions;

	@Getter
	private static final Map<UUID, CustomMenu> inventories = new HashMap<>();

	@Getter
	public static final Map<UUID, UUID> openInventories = new HashMap<>();

	public CustomMenu(int size, String title) {

		this.uuid = UUID.randomUUID();

		this.inventory = Bukkit.createInventory(null, size, MessageUtils.colorize(title));

		this.actions = new HashMap<>();

		inventories.put(getUuid(), this);
	}

	public void setItem(int slot, ItemStack stack, Consumer<InventoryClickEvent> actions) {
		setItem(new CustomButton(slot, stack, actions));
	}

	public void setItem(CustomButton button) {
		inventory.setItem(button.getSlot(), button.getStack());
		if (button.getActions() != null) actions.put(button.getSlot(), button.getActions());
	}

	public void setBackground(ItemStack material) {
		for (int i = 0; i < inventory.getSize(); i++)
			if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)
				setItem(new CustomButton(i, ItemBuilder.build(material, "&0", null),
						null));
	}

	public void open(Player p) {
		p.closeInventory();
		Common.delayTask(() -> {
			p.openInventory(inventory);
			openInventories.put(p.getUniqueId(), uuid);
		}, 1L);
	}

	public static int roundSlots(int slots) {

		slots = Math.round(slots / 9) * 9;

		if (slots < 9) slots = 9;
		if (slots > 54) slots = 54;

		return slots;
	}

	public static int asSlot(int slot) {
		return slot - 1;
	}
}
