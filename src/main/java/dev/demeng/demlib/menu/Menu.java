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
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/** A custom GUI menu. */
public abstract class Menu implements IMenu {

  @Getter(AccessLevel.PRIVATE)
  private final UUID uuid;

  @Getter private final Inventory inventory;

  @Getter private final Map<Integer, Consumer<InventoryClickEvent>> actions;

  @Getter private static final Map<UUID, Menu> inventories = new HashMap<>();

  @Getter public static final Map<UUID, UUID> openInventories = new HashMap<>();

  protected Menu(int size, String title) {

    this.uuid = UUID.randomUUID();
    this.inventory = Bukkit.createInventory(null, size, MessageUtils.colorize(title));
    this.actions = new HashMap<>();

    inventories.put(getUuid(), this);
  }

  protected void setItem(int slot, ItemStack stack, Consumer<InventoryClickEvent> actions) {
    setItem(new MenuButton(slot, stack, actions));
  }

  public void setItem(MenuButton button) {
    if (button.getSlot() == -1) return;
    inventory.setItem(button.getSlot(), button.getStack());
    if (button.getConsumer() != null) actions.put(button.getSlot(), button.getConsumer());
  }

  public void setBackground(ItemStack background) {

    if (background == null || background.getType() == Material.AIR) {
      return;
    }

    for (int i = 0; i < inventory.getSize(); i++) {
      if (inventory.getItem(i) == null
          || Objects.requireNonNull(inventory.getItem(i)).getType() == Material.AIR) {
        setItem(new MenuButton(i, ItemCreator.quickBuild(background, "&0", null), null));
      }
    }
  }

  @Override
  public void open(Player p) {

    getOpenInventories().remove(p.getUniqueId());
    PagedMenu.getOpenInventories().remove(p.getUniqueId());

    p.openInventory(inventory);
    openInventories.put(p.getUniqueId(), uuid);
  }
}
