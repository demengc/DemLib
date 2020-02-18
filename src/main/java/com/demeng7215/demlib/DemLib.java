package com.demeng7215.demlib;

import com.demeng7215.demlib.api.Registerer;
import com.demeng7215.demlib.listeners.MenuListener;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.Plugin;

/**
 * DemLib - A useful library, util, and API.
 *
 * @author Demeng7215
 */
public final class DemLib {

    /**
     * The plugin that DemLib has hooked with.
     */
    @NonNull
    @Getter
    private static Plugin plugin;

    /**
     * Sets the plugin that will be using this API.
     * This should be called before API usage, preferably on plugin enable.
     *
     * @param plugin The main class of your plugin.
     */
    public static void setPlugin(Plugin plugin) {

        DemLib.plugin = plugin;

        // Register some things required for DemLib to work.
        Registerer.registerListeners(new MenuListener());
    }
}
