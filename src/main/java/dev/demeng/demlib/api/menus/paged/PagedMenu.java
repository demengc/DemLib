package dev.demeng.demlib.api.menus.paged;

import dev.demeng.demlib.api.items.ItemCreator;
import dev.demeng.demlib.api.menus.Menu;
import dev.demeng.demlib.api.menus.MenuButton;
import dev.demeng.demlib.api.messages.MessageUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

/** A custom menu with many pages. */
public class PagedMenu {

  public List<Menu> pages;
  private int currentPage;

  @Getter public static final Map<UUID, UUID> openInventories = new HashMap<>();

  private final UUID uuid;

  /**
   * Create a new custom menu.
   *
   * @param slots The number of slots per page
   * @param items The list of items that will be listed
   * @param name The title of the menu
   * @param actions The actions executed when any of the listed items are clicked
   * @param prefs The settings for the menu
   */
  public PagedMenu(
      int slots,
      List<ItemStack> items,
      String name,
      Consumer<InventoryClickEvent> actions,
      PagedMenuSettings prefs) {

    this.uuid = UUID.randomUUID();
    this.pages = new ArrayList<>();
    this.currentPage = 0;

    name = MessageUtils.colorize(name);
    Menu page = newPage(slots, name.replace("%page%", "" + 1), prefs);

    int currentSlot = prefs.getFromSlot();

    pages.add(page);

    for (ItemStack item : items) {

      if (page.getInventory().firstEmpty() == prefs.getToSlot() + 1) {

        page = newPage(slots, name.replace("%page%", Integer.toString(pages.size() + 1)), prefs);

        pages.add(page);

        currentSlot = prefs.getFromSlot();
      }

      page.setItem(new MenuButton(currentSlot, item, actions));
      currentSlot++;
    }

    pages.get(0).setItem(prefs.getDummyBackButton());
    pages.get(pages.size() - 1).setItem(prefs.getDummyNextButton());

    for (Menu menu : pages) menu.setBackground(prefs.getBackground());
  }

  /**
   * Open the menu for a player.
   *
   * @param p
   */
  public void open(Player p) {
    pages.get(0).open(p);
    openInventories.put(p.getUniqueId(), uuid);
    currentPage = 0;
  }

  /**
   * Open a specific page of the menu for a player.
   *
   * @param p
   * @param index The index of the page
   */
  public void open(Player p, int index) {
    pages.get(index).open(p);
    openInventories.put(p.getUniqueId(), uuid);
    currentPage = index;
  }

  private Menu newPage(int slots, String name, PagedMenuSettings prefs) {

    final Menu menu = new Menu(slots, name);

    if (prefs.isIncludeSeparator()) {
      for (int i = 0; i < 9; i++)
        menu.setItem(
            new MenuButton(
                ((prefs.getSeparatorRow() - 1) * 9) + i,
                ItemCreator.quickBuild(prefs.getSeparatorMaterial(), "&0", null),
                null));
    }

    final MenuButton backButton = prefs.getBackButton();
    backButton.setActions(
        event -> {
          if (currentPage - 1 >= 0 && pages.get(currentPage - 1) != null)
            open((Player) event.getWhoClicked(), currentPage - 1);
        });

    final MenuButton nextButton = prefs.getNextButton();
    nextButton.setActions(
        event -> {
          if (currentPage + 1 < pages.size() && pages.get(currentPage + 1) != null)
            open((Player) event.getWhoClicked(), currentPage + 1);
        });

    return menu;
  }
}
