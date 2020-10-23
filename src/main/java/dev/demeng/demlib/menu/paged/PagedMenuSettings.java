package dev.demeng.demlib.menu.paged;

import dev.demeng.demlib.menu.MenuButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

/** A bunch of settings and preference options for paged menus. */
@AllArgsConstructor
public class PagedMenuSettings {

  /** The background material of the menu. */
  @Getter private final ItemStack background;

  /** The starting slot for pagination. */
  @Getter private final int fromSlot;

  /** The ending slot for pagination. */
  @Getter private final int toSlot;

  /** The previous page button. */
  @Getter private final MenuButton backButton;

  /** The next page button. */
  @Getter private final MenuButton nextButton;

  /** The previous page button when there are no more previous pages. */
  @Getter private final MenuButton dummyBackButton;

  /** The next page button when there are no more next pages. */
  @Getter private final MenuButton dummyNextButton;

  /** Should there be a separation bar between the content and menu controls? */
  @Getter private final boolean includeSeparator;

  /** The row of which the separator will be in. */
  @Getter private final int separatorRow;

  /** The material that will be used for the separator. */
  @Getter private final ItemStack separatorMaterial;
}
