package com.demeng7215.demlib;

import org.bukkit.plugin.java.JavaPlugin;

public final class DemLibPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("DemLib has been successfully loaded as a plugin.");
	}

	@Override
	public void onDisable() {
		getLogger().info("DemLib has been successfully disabled as a plugin.");
	}
}
