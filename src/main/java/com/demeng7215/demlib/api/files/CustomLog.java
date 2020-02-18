package com.demeng7215.demlib.api.files;

import com.demeng7215.demlib.DemLib;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLog {

	@Getter
	private final File logFile;

	public CustomLog() throws IOException {

		final File dataFolder = new File(DemLib.getPlugin().getDataFolder().getPath() + File.separator +
				"logs");
		if (!dataFolder.exists()) dataFolder.mkdirs();

		this.logFile = new File(dataFolder, LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ".txt");
		if (!this.logFile.exists()) this.logFile.createNewFile();
	}

	public void log(String info) throws IOException {

		final FileWriter fw = new FileWriter(logFile, true);
		final PrintWriter pw = new PrintWriter(fw);

		pw.println("[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + info);

		pw.flush();
		pw.close();
	}
}
