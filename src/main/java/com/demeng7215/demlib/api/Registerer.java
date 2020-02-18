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

	public static void registerCommand(Command command) {
		try {

			final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);

			final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
			commandMap.register(command.getLabel(), command);

		} catch (final Exception ignored) {
		}
	}

	public static void registerListeners(Listener listeners) {
		Bukkit.getPluginManager().registerEvents(listeners, i);
	}

	public static void registerListenersAdvanced(Event event, Listener listener,
												 EventPriority priority, boolean ignoreCancelled) {

		final Class<? extends Event> listenerEvent = event.getClass();
		final EventExecutor eventExecutor = (EventExecutor) listener;

		Bukkit.getPluginManager().registerEvent(listenerEvent, listener, priority, eventExecutor, i, ignoreCancelled);
	}
}
