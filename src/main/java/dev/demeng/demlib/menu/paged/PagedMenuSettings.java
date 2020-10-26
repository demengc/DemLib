package dev.demeng.demlib.menu.paged;

import dev.demeng.demlib.menu.MenuButton;
import org.bukkit.inventory.ItemStack;

/** A bunch of settings and preference options for paged menus. */
public interface PagedMenuSettings {

  ItemStack getBackgroundMaterial();

  boolean hasSeparator();

  int getSeparatorRow();

  ItemStack getSeparatorMaterial();

  MenuButton getBackButton();

  MenuButton getNextButton();

  MenuButton getDummyBackButton();

  MenuButton getDummyNextButton();

  int getFromSlot();

  int getToSlot();
}
