package dev.demeng.demlib.api.commands.types;

/** The interface for sub commands, the argument directly after the base command. */
public interface SubCommand extends BaseCommand {

  /**
   * The name of the base command.
   *
   * @return
   */
  String getBaseCommand();
}
