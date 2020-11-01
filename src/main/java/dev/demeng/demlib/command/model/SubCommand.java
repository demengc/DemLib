package dev.demeng.demlib.command.model;

public interface SubCommand extends BaseCommand {

  /**
   * The name of this sub-command's base command.
   *
   * @return
   */
  String getBaseName();
}
