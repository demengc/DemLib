package dev.demeng.demlib.api;

import dev.demeng.demlib.DemLib;
import dev.demeng.demlib.api.messages.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class CustomCommand extends Command {

	private static final Plugin i = DemLib.getPlugin();

	protected CustomCommand(String name) {
		super(name);
	}

	@Override
	public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
		run(sender, args);
		return false;
	}

	protected abstract void run(CommandSender sender, String[] args);

	protected boolean checkIsPlayer(CommandSender sender, String returnMessage) {

		if (!(sender instanceof Player)) {
			MessageUtils.tell(sender, returnMessage);
			return false;
		}

		return true;
	}

	protected boolean checkArgs(String[] args, int argsLength, CommandSender sender, String returnMessage) {

		if (args.length < argsLength) {
			MessageUtils.tell(sender, returnMessage);
			return false;
		}

		return true;
	}

	protected boolean checkArgsStrict(String[] args, int argsLength, CommandSender sender, String returnMessage) {

		if (args.length != argsLength) {
			MessageUtils.tell(sender, returnMessage);
			return false;
		}

		return true;
	}

	protected boolean checkHasPerm(String permission, CommandSender sender, String returnMessage) {

		if (!sender.hasPermission(permission)) {
			MessageUtils.tell(sender, returnMessage.replace("%permission%", permission));
			return false;
		}

		return true;
	}
}
