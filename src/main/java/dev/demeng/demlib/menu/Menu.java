package dev.demeng.demlib.menu;

import dev.demeng.demlib.item.ItemCreator;
import dev.demeng.demlib.menu.paged.PagedMenu;
import dev.demeng.demlib.message.MessageUtils;
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

/** A custom GUI menu. */
public class Menu {

  @Getter(AccessLevel.PRIVATE)
  private final UUID uuid;

  /** The actual Inventory object. */
  @Getter private final Inventory inventory;

  @Getter private final Map<Integer, Consumer<InventoryClickEvent>> actions;

  @Getter private static final Map<UUID, Menu> inventories = new HashMap<>();

  /** A map of currently open inventories and the player who has it open. */
  @Getter public static final Map<UUID, UUID> openInventories = new HashMap<>();

  /**
   * Create a new custom menu.
   *
   * @param size The size of the GUI
   * @param title The title of the GUI
   */
  public Menu(int size, String title) {
    this.uuid = UUID.randomUUID();
    this.inventory = Bukkit.createInventory(null, size, MessageUtils.colorize(title));
    this.actions = new HashMap<>();

    inventories.put(getUuid(), this);
  }

  /**
   * Add an item to the GUI.
   *
   * @param slot The slot that the item should go in
   * @param stack The item that should be displayed
   * @param actions The actions executed click
   */
  public void setItem(int slot, ItemStack stack, Consumer<InventoryClickEvent> actions) {
    setItem(new MenuButton(slot, stack, actions));
  }

  /**
   * Add a button to the GUI.
   *
   * @param button
   */
  public void setItem(MenuButton button) {
    inventory.setItem(button.getSlot(), button.getStack());
    if (button.getActions() != null) actions.put(button.getSlot(), button.getActions());
  }

  /**
   * Fill the background of the menu.
   *
   * @param material The background material
   */
  public void setBackground(ItemStack material) {
    for (int i = 0; i < inventory.getSize(); i++)
      if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)
        setItem(new MenuButton(i, ItemCreator.getDummy(material), null));
  }

  /**
   * Set the border of the menu to an item.
   *
   * @param material The border material
   */
  public void setBorder(ItemStack material) {

    final int size = inventory.getSize();
    final int rows = size / 9;
    final ItemStack stack = ItemCreator.getDummy(material);

    if (rows >= 3) {
      for (int i = 0; i <= 8; i++) setItem(i, stack, null);

      for (int s = 8; s < (size - 9); s += 9) {
        int lastSlot = s + 1;
        setItem(s, stack, null);
        setItem(lastSlot, stack, null);
      }

      for (int lr = (size - 9); lr < size; lr++) setItem(lr, stack, null);
    }
  }

  /**
   * Open the menu for the player.
   *
   * @param p
   */
  public void open(Player p) {
    getOpenInventories().remove(p.getUniqueId());
    PagedMenu.getOpenInventories().remove(p.getUniqueId());

    p.openInventory(inventory);
    openInventories.put(p.getUniqueId(), uuid);
  }

  /**
   * Round the provided slots to a working amount of slots.
   *
   * @param slots
   * @return
   */
  public static int roundSlots(int slots) {

    slots = Math.round(slots / 9) * 9;

    if (slots < 9) slots = 9;
    if (slots > 54) slots = 54;

    return slots;
  }
}
