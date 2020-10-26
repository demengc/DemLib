package dev.demeng.demlib.menu.templates;

import dev.demeng.demlib.item.ItemCreator;
import dev.demeng.demlib.menu.MenuButton;
import dev.demeng.demlib.menu.paged.PagedMenu;
import dev.demeng.demlib.menu.paged.PagedMenuSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public abstract class ConfigBasedPagedMenu extends PagedMenu {

  protected ConfigBasedPagedMenu(String title, FileConfiguration config) {
    super(
        config.getInt("size"),
        title,
        new PagedMenuSettings() {
          @Override
          public ItemStack getBackgroundMaterial() {
            return ItemCreator.getMaterial(config.getString("background"));
          }

          @Override
          public boolean hasSeparator() {
            return false;
          }

          @Override
          public int getSeparatorRow() {
            return config.getInt("separator.row");
          }

          @Override
          public ItemStack getSeparatorMaterial() {
            return ItemCreator.getMaterial(config.getString("separator.material"));
          }

          @Override
          public MenuButton getBackButton() {
            return MenuButton.fromConfig(config, "previous-page-button");
          }

          @Override
          public MenuButton getNextButton() {
            return MenuButton.fromConfig(config, "next-page-button");
          }

          @Override
          public MenuButton getDummyBackButton() {

            final MenuButton button =
                MenuButton.fromConfig(config, "previous-page-button.no-more-pages");
            button.setSlot(getBackButton().getSlot());

            return button;
          }

          @Override
          public MenuButton getDummyNextButton() {
            final MenuButton button =
                MenuButton.fromConfig(config, "next-page-button.no-more-pages");
            button.setSlot(getNextButton().getSlot());

            return button;
          }

          @Override
          public int getFromSlot() {
            return config.getInt("listing-range.start");
          }

          @Override
          public int getToSlot() {
            return config.getInt("listing-range.end");
          }
        });
  }
}
