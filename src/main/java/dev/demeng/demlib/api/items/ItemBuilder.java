package dev.demeng.demlib.api.items;

import dev.demeng.demlib.api.messages.MessageUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;

public final class ItemBuilder {

  public static ItemStack build(ItemStack stack, String name, List<String> lore) {

    ItemMeta meta = stack.getItemMeta();

    meta.setDisplayName(MessageUtils.colorize(name));

    if (lore != null && !lore.get(0).equalsIgnoreCase("none")) {
      meta.setLore(MessageUtils.colorize(lore));
    }

    stack.setItemMeta(meta);

    return stack;
  }

  public static ItemStack fromConfig(FileConfiguration config, String path) {
    return ItemBuilder.build(
        ItemBuilder.getMaterial(config.getString(path + ".material")),
        config.getString(path + ".display-name"),
        config.getStringList(path + ".lore"));
  }

  public static ItemStack getMaterial(String name) {
    final Optional<XMaterial> mat = XMaterial.matchXMaterial(name);
    final ItemStack stack = mat.map(XMaterial::parseItem).orElse(null);

    if (stack == null) MessageUtils.error(null, 6, "Invalid material name: " + name, false);

    return stack;
  }

  public static ItemStack getDummy(ItemStack stack) {
    return build(stack, "&0", null);
  }
}
