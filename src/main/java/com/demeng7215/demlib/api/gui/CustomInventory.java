package com.demeng7215.demlib.api.gui;

import com.demeng7215.demlib.api.messages.MessageUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Make epic inventories and GUIs easily.
 */
public class CustomInventory {

    @Getter(AccessLevel.PRIVATE)
    private final UUID uuid;

    private final Inventory inv;
    private final Map<Integer, InvAction> actions;
    private static final Map<UUID, CustomInventory> inventoriesByUUID = new HashMap<>();
    public static final Map<UUID, UUID> openInventories = new HashMap<>();

    /**
     * Create the custom inventory.
     *
     * @param size  The size of the GUI, in slots
     * @param title The name and title of the GUI
     */
    protected CustomInventory(int size, String title) {

        this.uuid = UUID.randomUUID();

        this.inv = Bukkit.createInventory(null, size, MessageUtils.colorize(title));

        this.actions = new HashMap<>();
        inventoriesByUUID.put(getUuid(), this);
    }

    public interface InvAction {
        void click(Player player);
    }

    /**
     * Set the button in the GUI.
     *
     * @param slot The slot number, starting from 0
     * @param stack The item stack of the button
     * @param name The display name of the button
     * @param lore The description/lore of the button
     * @param action The action that the button will perform when clicked
     */
    protected void setItem(int slot, ItemStack stack, String name, List<String> lore, InvAction action) {

        ItemMeta meta = stack.getItemMeta();
        List<String> loreList = new ArrayList<>();

        for (String s : lore) loreList.add(MessageUtils.colorize(s));

        meta.setDisplayName(MessageUtils.colorize(name));
        meta.setLore(loreList);

        stack.setItemMeta(meta);

        inv.setItem(slot, stack);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void open(Player p) {
        p.openInventory(inv);
        openInventories.put(p.getUniqueId(), getUuid());
    }

    public static Map<UUID, CustomInventory> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public Map<Integer, InvAction> getActions() {
        return actions;
    }

    public Inventory getInventory() {
        return inv;
    }
}
