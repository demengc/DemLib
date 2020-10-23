package dev.demeng.demlib.command;

import dev.demeng.demlib.core.DemLib;
import dev.demeng.demlib.message.MessageUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Extend this class to make a custom command. */
public abstract class CustomCommand extends Command {

  private final boolean playersOnly;
  private final String permission;
  private final int minimumArgs;
  private final String usage;

  /*
  setDescription()
  setAliases()
   */

  /**
   * Create a new custom command.
   *
   * @param name The name of the command.
   * @param playersOnly If this command can be used by players only.
   * @param permission The permission node required for this command.
   * @param minimumArgs The minimum amount of arguments.
   * @param usage The usage of the command.
   */
  protected CustomCommand(
      String name, boolean playersOnly, String permission, int minimumArgs, String usage) {
    super(name);

    this.playersOnly = playersOnly;
    this.permission = permission;
    this.minimumArgs = minimumArgs;
    this.usage = usage;
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {

    try {

      if (playersOnly && !(sender instanceof Player)) {
        returnTell(DemLib.getCommandMessages().getNotPlayer());
      }

      if (permission != null && !sender.hasPermission(permission)) {
        returnTell(
            DemLib.getCommandMessages().getNoPermission().replace("%permission%", permission));
      }

      if (args.length < minimumArgs) {
        returnTell(
            DemLib.getCommandMessages()
                .getInvalidArgs()
                .replace("%usage%", "/" + label + " " + usage));
      }

      run(sender, args);
    } catch (ReturnedCommandException ex) {
      MessageUtils.tell(sender, ex.getReturnMessage());
    }

    return true;
  }

  /**
   * Code executed if the command executed and the player passes all checks.
   *
   * @param sender The command sender.
   * @param args The arguments provided.
   */
  protected abstract void run(CommandSender sender, String[] args);

  /**
   * Send the command sender a message and return.
   *
   * @param message The message to send.
   */
  protected void returnTell(String message) {
    throw new ReturnedCommandException(message);
  }

  @RequiredArgsConstructor
  private static final class ReturnedCommandException extends RuntimeException {
    private static final long serialVersionUID = 1;
    @Getter private final String returnMessage;
  }
}
