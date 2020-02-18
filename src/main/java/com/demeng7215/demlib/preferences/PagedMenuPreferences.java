package com.demeng7215.demlib.preferences;

import com.demeng7215.demlib.api.menus.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PagedMenuPreferences {

	@Getter
	private ItemStack background;

	@Getter
	private int fromSlot;

	@Getter
	private int toSlot;

	@Getter
	private Button backButton;

	@Getter
	private Button nextButton;

	@Getter
	private Button dummyBackButton;

	@Getter
	private Button dummyNextButton;

	@Getter
	private boolean includeSeparator;

	@Getter
	private int separatorRow;

	@Getter
	private ItemStack separatorMaterial;
}
