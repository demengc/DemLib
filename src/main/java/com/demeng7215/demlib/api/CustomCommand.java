package com.demeng7215.demlib.api;

import com.demeng7215.demlib.DemLib;
import com.demeng7215.demlib.api.messages.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * An easier way to create commands.
 * This library saves lines of code and makes everything more simple.
 * All you have to do to use this is make your command class extend this class and implement the methods!
 */
public abstract class CustomCommand extends Command {

    private static final Plugin i = DemLib.getPlugin();

    /**
     * Sets the name of the command.
     * This name is your command. Do not put / in front of the name, unless you want a double-slash command.
     * Your command will be: /(name)
     *
     * @param name The name of the command.
     */
    protected CustomCommand(String name) {
        super(name);
    }

    // Some fancy custom command manager stuff.
    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
        run(sender, args);
        return false;
    }

    protected abstract void run(CommandSender sender, String[] args);

    /**
     * Checks if the command sender is a player.
     * If the sender is not a player, a message will be sent.
     *
     * @param sender        The command sender you want to check
     * @param returnMessage The "error" message the command sender will receive if they are not a player.
     * @return false if sender is not a player, true if sender is a player.
     */
    protected boolean checkIsPlayer(CommandSender sender, String returnMessage) {

        if (!(sender instanceof Player)) {
            MessageUtils.tell(sender, returnMessage);
            return false;
        }

        return true;
    }

    /**
     * Checks if the command arguments provided by the player is at least the required number of arguments.
     * If the number of arguments are not sufficient, a message will be sent.
     *
     * @param args          The string list of arguments
     * @param argsLength    The minimum argument length/amount
     * @param sender        The command sender that may be receiving the return message
     * @param returnMessage The "error" message that will be sent if the argument length/amount is not sufficient
     * @return false if the argument length/amount is insufficient, true if it is sufficient
     */
    protected boolean checkArgs(String[] args, int argsLength, CommandSender sender, String returnMessage) {

        if (args.length < argsLength) {
            MessageUtils.tell(sender, returnMessage);
            return false;
        }

        return true;
    }

    /**
     * Does pretty much the same thing as the method above, #checkArgs(...).
     * But, the argument length/amount MUST be the specified length/amount. No more, no less.
     */
    protected boolean checkArgsStrict(String[] args, int argsLength, CommandSender sender, String returnMessage) {

        if (args.length != argsLength) {
            MessageUtils.tell(sender, returnMessage);
            return false;
        }

        return true;
    }

    /**
     * Checks if the player has the specified permission, is a server operator, or is the console.
     *
     * @param permission    The permission node.
     * @param sender        The command sender that may be receiving the return message.
     * @param returnMessage The "error" message that will be sent if the argument length/amount is not sufficient.
     * @return false if the sender does not have the specified permission, true if the sender does.
     */
    protected boolean checkHasPerm(String permission, CommandSender sender, String returnMessage) {

        if (!sender.hasPermission(permission)) {
            MessageUtils.tell(sender, returnMessage);
            return false;
        }

        return true;
    }
}
