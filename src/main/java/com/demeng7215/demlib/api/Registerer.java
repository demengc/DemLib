package com.demeng7215.demlib.api;

import com.demeng7215.demlib.DemLib;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class Registerer {

    private static final Plugin i = DemLib.getPlugin();

    /**
     * Registers a command you created with the DemCommand class.
     *
     * @param command The class containing the DemCommand
     */
    public static void registerCommand(Command command) {
        try {

            final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(command.getLabel(), command);

        } catch (final Exception ignored) {
        }
    }

    /**
     * Registers all the listeners in the specified class.
     * Class must implement Listener.
     * For more advanced listener registering, see #registerListenersAdvanced(...).
     *
     * @param listeners The class which you have listeners in.
     * @see #registerListenersAdvanced(Event, Listener, EventPriority, boolean)
     */
    public static void registerListeners(Listener listeners) {
        Bukkit.getPluginManager().registerEvents(listeners, i);
    }

    /**
     * Registers a specific listener in the class.
     * This is a more advanced way of registering your listeners.
     * This allows you to set the event priority, choose weather you want to ignore cancelled, and more.
     * For a simpler listener registration method, see #registerListeners(Listener).
     *
     * @param event           The event that your listener is listening to: eg. PlayerInteractEvent, PlayerMoveEvent
     * @param listener        Your listener class
     * @param priority        The event priority- from LOWEST to HIGHEST, and MONITOR
     * @param ignoreCancelled Weather if the event should ignore cancelled
     */
    public static void registerListenersAdvanced(Event event, Listener listener,
                                                 EventPriority priority, boolean ignoreCancelled) {

        final Class<? extends Event> listenerEvent = event.getClass();
        final EventExecutor eventExecutor = (EventExecutor) listener;

        Bukkit.getPluginManager().registerEvent(listenerEvent, listener, priority, eventExecutor, i, ignoreCancelled);
    }
}
