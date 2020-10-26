package dev.demeng.demlib.menu.paged;

import dev.demeng.demlib.item.ItemCreator;
import dev.demeng.demlib.menu.IMenu;
import dev.demeng.demlib.menu.Menu;
import dev.demeng.demlib.menu.MenuButton;
import dev.demeng.demlib.message.MessageUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class PagedMenu implements IMenu {

  private final UUID uuid;
  private final int slots;
  private final String title;
  private final PagedMenuSettings settings;
  private final List<Menu> pages;
  private int currentPage;

  @Getter public static final Map<UUID, UUID> openInventories = new HashMap<>();

  protected PagedMenu(int slots, String title, PagedMenuSettings settings) {
    this.uuid = UUID.randomUUID();
    this.slots = slots;
    this.title = MessageUtils.colorize(title);
    this.settings = settings;
    this.pages = new ArrayList<>();
    this.currentPage = 0;
  }

  protected void fill(List<MenuButton> buttons) {

    int currentSlot = settings.getFromSlot() - 1;

    Menu page = new Page(slots, title.replace("%page%", "" + 1), settings);
    pages.add(page);

    for (MenuButton button : buttons) {

      if (currentSlot == settings.getToSlot()
          || page.getInventory().firstEmpty() == settings.getToSlot() + 1
          || page.getInventory().firstEmpty() == -1) {

        page = new Page(slots, title.replace("%page%", String.valueOf(pages.size() + 1)), settings);
        pages.add(page);

        currentSlot = settings.getFromSlot() - 1;
      }

      button.setSlot(currentSlot);
      page.setItem(button);
      currentSlot++;
    }

    pages.get(0).setItem(settings.getDummyBackButton());
    pages.get(pages.size() - 1).setItem(settings.getDummyNextButton());

    for (Menu menu : pages) menu.setBackground(settings.getBackgroundMaterial());
  }

  protected void setStaticItem(MenuButton button) {
    for (Menu menu : pages) {
      menu.setItem(button);
    }
  }

  @Override
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

  private class Page extends Menu {

    public Page(int slots, String title, PagedMenuSettings settings) {
      super(slots, title);

      if (settings.hasSeparator())
        for (int i = 0; i < 9; i++) {

          final int slot = ((settings.getSeparatorRow() - 1) * 9) + i;
          final ItemStack stack = getInventory().getItem(slot);

          if (stack == null || stack.getType() == Material.AIR)
            setItem(
                new MenuButton(slot, ItemCreator.getDummy(settings.getSeparatorMaterial()), null));
        }

      setItem(
          new MenuButton(
              settings.getBackButton().getSlot(),
              settings.getBackButton().getStack(),
              event -> {
                if (currentPage - 1 >= 0 && pages.get(currentPage - 1) != null)
                  PagedMenu.this.open((Player) event.getWhoClicked(), currentPage - 1);
              }));

      setItem(
          new MenuButton(
              settings.getNextButton().getSlot(),
              settings.getNextButton().getStack(),
              event -> {
                if (currentPage + 1 < pages.size() && pages.get(currentPage + 1) != null)
                  PagedMenu.this.open((Player) event.getWhoClicked(), currentPage + 1);
              }));
    }
  }
}
