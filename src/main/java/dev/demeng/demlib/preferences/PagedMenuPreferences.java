package dev.demeng.demlib.preferences;

import dev.demeng.demlib.api.menus.CustomButton;
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
	private CustomButton backButton;

	@Getter
	private CustomButton nextButton;

	@Getter
	private CustomButton dummyBackButton;

	@Getter
	private CustomButton dummyNextButton;

	@Getter
	private boolean includeSeparator;

	@Getter
	private int separatorRow;

	@Getter
	private ItemStack separatorMaterial;
}
