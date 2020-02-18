package com.demeng7215.demlib.api.menus;

import com.demeng7215.demlib.api.items.ItemBuilder;
import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.demlib.preferences.PagedMenuPreferences;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class PagedMenu {

	public List<Menu> pages;
	private int currentPage;

	@Getter
	public static final Map<UUID, UUID> openInventories = new HashMap<>();

	private UUID uuid;

	public PagedMenu(int slots, List<ItemStack> items, String name, Consumer<InventoryClickEvent> actions,
					 PagedMenuPreferences prefs) {

		this.uuid = UUID.randomUUID();
		this.pages = new ArrayList<>();
		this.currentPage = 0;

		name = MessageUtils.colorize(name);
		Menu page = newPage(slots, name.replace("%page%", "" + 1), prefs);

		int currentSlot = Menu.asSlot(prefs.getFromSlot());

		pages.add(page);

		for (ItemStack item : items) {

			if (page.getInventory().firstEmpty() == Menu.asSlot(prefs.getToSlot()) + 1) {

				page = newPage(slots, name.replace("%page%", Integer.toString(pages.size() + 1)), prefs);

				pages.add(page);

				currentSlot = Menu.asSlot(prefs.getFromSlot());
			}

			page.setItem(new Button(currentSlot, item, actions));
			currentSlot++;
		}

		pages.get(0).setItem(prefs.getDummyBackButton());
		pages.get(pages.size() - 1).setItem(prefs.getDummyNextButton());

		for (Menu menu : pages)
			menu.setBackground(prefs.getBackground());
	}

	public void open(Player p) {
		pages.get(0).open(p);
		openInventories.put(p.getUniqueId(), uuid);
		currentPage = 0;
	}

	public void open(Player p, int index) {
		pages.get(index).open(p);
		openInventories.put(p.getUniqueId(), uuid);
		currentPage = index;
	}

	private Menu newPage(int slots, String name, PagedMenuPreferences prefs) {

		final Menu menu = new Menu(slots, name);

		if (prefs.isIncludeSeparator()) {
			for (int i = 0; i < 9; i++)
				menu.setItem(new Button(((prefs.getSeparatorRow() - 1) * 9) + i,
						ItemBuilder.build(prefs.getSeparatorMaterial(), "&0", null), null));
		}

		menu.setItem(prefs.getBackButton().setActions(event -> {
			if (currentPage - 1 >= 0 && pages.get(currentPage - 1) != null)
				open((Player) event.getWhoClicked(), currentPage - 1);
		}));

		menu.setItem(prefs.getNextButton().setActions(event -> {
			if (currentPage + 1 < pages.size() && pages.get(currentPage + 1) != null)
				open((Player) event.getWhoClicked(), currentPage + 1);
		}));

		return menu;
	}
}
