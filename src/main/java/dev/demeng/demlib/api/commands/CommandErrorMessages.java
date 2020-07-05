package dev.demeng.demlib.api.commands;

import lombok.Getter;
import lombok.Setter;

/** Getters/setters for command error messages. */
public class CommandErrorMessages {

  @Getter @Setter private String notPlayer;

  @Getter @Setter private String insufficientPermission;

  @Getter @Setter private String incorrectUsage;
}
