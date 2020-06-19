package dev.demeng.demlib.api;

import dev.demeng.demlib.DemLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;

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

	public static void dispatch(String... commands) {
		for (String cmd : commands)
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
	}

	public static void dispatch(List<String> commands) {
		for (String cmd : commands) dispatch(cmd);
	}

	public static void dispatch(Player sender, String... commands) {
		for (String cmd : commands)
			Bukkit.dispatchCommand(sender, cmd);
	}

	public static void dispatch(Player sender, List<String> commands) {
		for (String cmd : commands) dispatch(sender, cmd);
	}

	public static int getIndex(Set<? extends Object> set, Object value) {
		int result = 0;
		for (Object entry : set) {
			if (entry.equals(value)) return result;
			result++;
		}
		return -1;
	}
}
