package dev.demeng.demlib.file;

import dev.demeng.demlib.core.DemLib;
import lombok.Getter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** .txt log file with folder by date and logging with time prefix. */
public class LogFile {

  /** The File object, in case you need it for further customization. */
  @Getter private final File logFile;

  /**
   * Creating a new file.
   *
   * @throws IOException
   */
  public LogFile() throws IOException {

    final File dataFolder =
        new File(DemLib.getPlugin().getDataFolder().getPath() + File.separator + "logs");
    if (!dataFolder.exists()) dataFolder.mkdirs();

    this.logFile =
        new File(
            dataFolder,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ".txt");
    if (!this.logFile.exists()) this.logFile.createNewFile();
  }

  /**
   * Log information with date.
   *
   * @param info The information you wish to log
   * @throws IOException
   */
  public void log(String info) throws IOException {

    final FileWriter fw = new FileWriter(logFile, true);
    final PrintWriter pw = new PrintWriter(fw);

    pw.println(
        "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + info);

    pw.flush();
    pw.close();
  }
}
