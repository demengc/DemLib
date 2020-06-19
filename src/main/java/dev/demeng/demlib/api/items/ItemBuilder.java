package dev.demeng.demlib.api.items;

import dev.demeng.demlib.api.messages.MessageUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Optional;

public class ItemBuilder {

	public static ItemStack build(ItemStack stack, String name, List<String> lore) {

		ItemMeta meta = stack.getItemMeta();

		meta.setDisplayName(MessageUtils.colorize(name));

		if (lore != null && !lore.isEmpty() &&
				!lore.get(0).equalsIgnoreCase("none")) meta.setLore(MessageUtils.colorize(lore));

		stack.setItemMeta(meta);

		return stack;
	}

	public static ItemStack getMaterial(String name) {
		Optional<XMaterial> mat = XMaterial.matchXMaterial(name);
		return mat.map(XMaterial::parseItem).orElse(null);
	}

	public static Material getActualMaterial(String name) {
		Optional<XMaterial> mat = XMaterial.matchXMaterial(name);
		return mat.map(XMaterial::parseMaterial).orElse(null);
	}
}
