package dev.demeng.demlib.api.commands;

import lombok.Getter;
import lombok.Setter;

public class CommandSettings {

	@Getter @Setter
	private String notPlayerMessage;

	@Getter @Setter
	private String noPermissionMessage;

	@Getter @Setter
	private String incorrectUsageMessage;
}
