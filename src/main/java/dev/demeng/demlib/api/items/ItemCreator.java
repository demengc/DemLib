package dev.demeng.demlib.api.items;

import dev.demeng.demlib.api.messages.MessageUtils;
import dev.demeng.demlib.api.xseries.XMaterial;
import lombok.Builder;
import lombok.Singular;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Builder
public final class ItemCreator {

  private final ItemStack item;

  @Builder.Default private final int amount = 1;

  private final String name;

  @Singular private final List<String> lores;

  @Singular private final Map<Enchantment, Integer> enchants;

  @Singular private final List<ItemFlag> flags;

  @Builder.Default private final boolean unbreakable = false;

  @Builder.Default private final boolean hideTags = false;

  private final boolean glow;

  /**
   * Create a detailed item from ItemCreatorBuilder.
   *
   * @return
   */
  public ItemStack create() {

    final ItemStack stack = new ItemStack(item);
    stack.setAmount(amount);

    final ItemMeta meta = stack.getItemMeta();

    meta.setDisplayName(MessageUtils.colorize(name));

    if (lores != null && !lores.isEmpty() && !lores.get(0).equalsIgnoreCase("none")) {
      meta.setLore(MessageUtils.colorize(lores));
    }

    if (enchants != null) {
      for (Enchantment e : enchants.keySet()) {
        meta.addEnchant(e, enchants.get(e), true);
      }
    }

    if (flags != null) {
      meta.addItemFlags(flags.toArray(new ItemFlag[0]));
    }

    meta.setUnbreakable(unbreakable);

    if (hideTags) {
      meta.addItemFlags(ItemFlag.values());
    }

    if (glow) {
      meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }

    stack.setItemMeta(meta);

    return stack;
  }

  /**
   * Quickly build an item.
   *
   * @param stack The initial stack of the item
   * @param name The display name of the item
   * @param lore The lore of the time
   * @return
   */
  public static ItemStack quickBuild(ItemStack stack, String name, List<String> lore) {

    ItemMeta meta = stack.getItemMeta();

    meta.setDisplayName(MessageUtils.colorize(name));

    if (lore != null && !lore.get(0).equalsIgnoreCase("none")) {
      meta.setLore(MessageUtils.colorize(lore));
    }

    stack.setItemMeta(meta);

    return stack;
  }

  /**
   * Create a simple item from config.
   *
   * @param config The config file containing the item config
   * @param path The path to the item config
   * @return
   */
  public static ItemStack fromConfig(FileConfiguration config, String path) {
    return ItemCreator.quickBuild(
        ItemCreator.getMaterial(config.getString(path + ".material")),
        config.getString(path + ".display-name"),
        config.getStringList(path + ".lore"));
  }

  /**
   * Get an ItemStack from a material name.
   *
   * @param name
   * @return
   * @throws IllegalArgumentException If the name is invalid.
   */
  public static ItemStack getMaterial(String name) throws IllegalArgumentException {
    final Optional<XMaterial> mat = XMaterial.matchXMaterial(name);
    final ItemStack stack = mat.map(XMaterial::parseItem).orElse(null);

    if (stack == null) {
      throw new IllegalArgumentException("Invalid material name: " + name);
    }

    return stack;
  }

  /**
   * Return an item with no display name or lore.
   *
   * @param stack The initial stack
   * @return
   */
  public static ItemStack getDummy(ItemStack stack) {
    return quickBuild(stack, "&0", null);
  }
}
