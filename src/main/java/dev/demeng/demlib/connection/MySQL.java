package dev.demeng.demlib.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.demeng.demlib.Common;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Easy MySQL database utility. YOU MUST SHADE HIKARICP AND DATABASE DRIVERS (H2, MARIADB) YOURSELF!
 */
public class MySQL {

  private HikariDataSource source;

  /** The database connection. */
  @Getter(AccessLevel.PROTECTED)
  private Connection connection;

  /** The mode to use. */
  private final Mode mode;

  /** The parent folder of the H2 database. */
  private File parent;

  /** Host or IP of the database. */
  private String host;

  /** Port of the database. */
  private int port;

  /** The database to connect to. */
  private final String database;

  /** Username for connecting to the database. */
  private String username;

  /** Password for connecting to the database. */
  private String password;

  /**
   * Additional options appended to the JDBC connection URL. Default should be:
   * ?autoReconnect=true&useSSL=false
   */
  private String additionalOptions;

  /** The maximum pool size. */
  private int maxPoolSize;

  /**
   * Initialize a new H2 database.
   *
   * @param database The name of the database.
   * @throws SQLException If the database connection failed.
   */
  protected MySQL(File parent, String database, int maxPoolSize) throws SQLException {
    this.mode = Mode.H2;
    this.parent = parent;
    this.database = database;
    this.maxPoolSize = maxPoolSize;

    init();
  }

  /**
   * Initialize a new MySQL or MariaDB database.
   *
   * @param mariadb If we should use the MariaDB driver instead of the MySQL driver.
   * @param host The host.
   * @param port The port.
   * @param database The database name.
   * @param username The username.
   * @param password The password.
   * @param additionalOptions Additional options appended to the JDBC connection URL.
   * @throws SQLException If the database connection fails.
   */
  protected MySQL(
      boolean mariadb,
      String host,
      int port,
      String database,
      String username,
      String password,
      String additionalOptions,
      int maxPoolSize)
      throws SQLException {
    this.mode = mariadb ? Mode.MARIADB : Mode.MYSQL;
    this.host = host;
    this.port = port;
    this.database = database;
    this.username = username;
    this.password = password;
    this.additionalOptions = additionalOptions;
    this.maxPoolSize = maxPoolSize;

    init();
  }

  private void init() throws SQLException {

    final HikariConfig config = new HikariConfig();
    config.setMaximumPoolSize(maxPoolSize);

    if (mode == Mode.H2) {

      config.setDriverClassName("org.h2.Driver");

      final String path;
      if (parent == null) {
        path = new File(database).getAbsolutePath();
      } else {
        path = new File(parent, database).getAbsolutePath();
      }

      config.setJdbcUrl("jdbc:h2:" + path + ";mode=MySQL");

    } else {
      config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + additionalOptions);

      config.setUsername(username);
      config.setPassword(password);

      if (mode == Mode.MYSQL) {
        config.setDriverClassName("com.mysql.jdbc.Driver");
      } else {
        config.setDriverClassName("org.mariadb.jdbc.Driver");
      }
    }

    this.source = new HikariDataSource(config);
    this.connection = source.getConnection();

    Common.repeatTask(
        () -> {
          try {

            if (connection != null && !connection.isClosed()) {
              connection.createStatement().execute("SELECT 1");
            } else {
              connection = getNewConnection();
            }

          } catch (SQLException ex) {
            connection = getNewConnection();
          }
        },
        1200,
        1200,
        true);
  }

  /**
   * Execute an update sync.
   *
   * @param sql SQL statement.
   * @param placeholders Placeholders for the prepared statement.
   * @throws SQLException If the update fails.
   */
  protected void executeUpdate(String sql, Object... placeholders) throws SQLException {

    final PreparedStatement statement = connection.prepareStatement(sql);
    for (int i = 0; i < placeholders.length; i++) {
      statement.setObject(i + 1, placeholders[i]);
    }

    statement.executeUpdate();
  }

  /**
   * Execute a query sync.
   *
   * @param sql SQL statement.
   * @param placeholders Placeholders for the prepared statement.
   * @return The result of the query.
   * @throws SQLException If the query fails.
   */
  protected ResultSet executeQuery(String sql, Object... placeholders) throws SQLException {

    final PreparedStatement statement = connection.prepareStatement(sql);
    for (int i = 0; i < placeholders.length; i++) {
      statement.setObject(i + 1, placeholders[i]);
    }

    return statement.executeQuery();
  }

  /**
   * Close active connection.
   *
   * @throws SQLException If connection fails to close.
   */
  protected void close() throws SQLException {
    if (connection != null) connection.close();
    if (source != null) source.close();
  }

  private Connection getNewConnection() {

    try {
      return source.getConnection();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    return null;
  }

  /** Available database types. */
  public enum Mode {
    H2,
    MYSQL,
    MARIADB
  }
}
