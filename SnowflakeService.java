import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class SnowflakeService {

  private static final String SNOWFLAKE_APP_NAME = "Trustlogix";

  private Map<String, String> userAttributes;

  public SnowflakeService(Map<String, String> userAttributes) {
    this.userAttributes = userAttributes;

  }

  public static void main(String args[]) throws SQLException, ClassNotFoundException {
    if (Objects.isNull(args) || args.length < 3) {
    }
    String username = args[0];
    String password = args[1];
    String hostName = args[2];

    hostName = hostName != null ? hostName.contains(".snowflakecomputing.com") ? hostName : hostName + ".snowflakecomputing.com" : "";

    String jdbcUrl = "jdbc:snowflake://" + hostName ;

    Map<String, String> userAttributes = new HashMap<>();

    SnowflakeService snowflakeService = new SnowflakeService(userAttributes);
    try (
        Connection conn = snowflakeService.getConnection(jdbcUrl, username, password);
        Statement statement = conn.createStatement()) {

      ResultSet rs = executeQuery(statement);
      System.out.println(rs);
    }
  }

  private static ResultSet executeQuery(Statement statement) throws SQLException {
    ResultSet rs = statement.executeQuery("SELECT 1 FROM DUAL");
    return rs;
  }


  public Connection getConnection(String jdbcConnectionUrl, String username, String password) throws SQLException, ClassNotFoundException {
    loadDriver();
    Properties properties = initializeProperties(username, password);
    return DriverManager.getConnection(jdbcConnectionUrl, properties);
  }

  private Properties initializeProperties(String username,
      String password) {
    // build connection properties
    Properties properties = new Properties();
    properties.put("application", SNOWFLAKE_APP_NAME);
    properties.put("allowUnderscoresInHost", "true");
    properties.put("user", username);
    if (password != null) {
      properties.put("password", password);
//      properties.put("role", roleName);
    }
////    String account = jdbcConnectionUrl.replace("jdbc:snowflake://", "")
////        .replace(".snowflakecomputing.com", "")
////        .trim();
//    properties.put("account", account);
//    properties.put("db", "SNOWFLAKE");
//    properties.put("schema", "ACCOUNT_USAGE");
//    properties.put("warehouse", warehouse);
    return properties;
  }

  private void loadDriver() throws ClassNotFoundException {
    try {
      Class.forName("com.snowflake.client.jdbc.SnowflakeDriver");
    } catch (ClassNotFoundException ex) {
      throw ex;
    }
  }

  private ResultSet executeQuery(Connection conn, String query) throws SQLException {
    ResultSet rs = null;
    try (Statement statement = conn.createStatement()) {
      rs = statement.executeQuery(query);
    }
    return rs;
  }

  private static void printColumns(ResultSet rs) throws SQLException {
    System.out.println("Metadata:");
    System.out.println("================================");
    // fetch metadata
    ResultSetMetaData resultSetMetaData = rs.getMetaData();
    System.out.println("Number of columns=" +
        resultSetMetaData.getColumnCount());
    for (int colIdx = 0; colIdx < resultSetMetaData.getColumnCount();
        colIdx++) {
      System.out.println("Column " + colIdx + ": type=" +
          resultSetMetaData.getColumnTypeName(colIdx + 1) + " " + resultSetMetaData.getColumnName(
          colIdx + 1));
    }
  }
}
