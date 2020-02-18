package com.demeng7215.demlib.api;

import com.demeng7215.demlib.DemLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class Common {

	private static final Plugin i = DemLib.getPlugin();

	public static String getName() {
		return i.getDescription().getName();
	}

	public static String getVersion() {
		return i.getDescription().getVersion();
	}

	public static <T> T getOrDefault(T nullable, T def) {
		return nullable != null ? nullable : def;
	}

	public static String booleanToString(boolean bool) {
		if (bool) return "Yes";
		return "No";
	}

	public static int repeatTask(Runnable task, long interval) {
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(i, task, 0L, interval);
	}

	public static int repeatTaskAsync(Runnable task, long interval) {
		return Bukkit.getScheduler().scheduleAsyncRepeatingTask(i, task, 0L, interval);
	}

	public static int delayTask(Runnable task, long delay) {
		return Bukkit.getScheduler().scheduleSyncDelayedTask(i, task, delay);
	}

	public static int delayTaskAsync(Runnable task, long delay) {
		return Bukkit.getScheduler().scheduleAsyncDelayedTask(i, task, delay);
	}

	/**
	 * Check if the string contains another string while ignoring case.
	 *
	 * @param s        The string to check in
	 * @param contains The string to check for
	 * @return true if the string contains at least ONE of the other strings, false otherwise
	 */
	public static boolean checkContains(String s, List<String> contains) {
		for (String c : contains) if (s.toLowerCase().contains(c.toLowerCase())) return true;
		return false;
	}
}
