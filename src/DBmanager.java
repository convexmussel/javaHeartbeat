


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * The type DBmanager.
 */
public class DBmanager {

    private static DBmanager uniqueInstance = null;
    private static Connection connection = null;

    /**
     * Instantiates a new DBmanager.
     */
    protected DBmanager() {
        if(!dbExists()) {
            System.err.println(">>> DBManager: The database doesn't exist...");
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized DBmanager getInstance() {
        if(uniqueInstance == null) {
            uniqueInstance = new DBmanager();
        }
        return uniqueInstance;
    }

    private Boolean dbExists() {
        Boolean exists = false;

        Statement statement = null;
        try {
            if(connection == null) {
                makeConnection();
            }
            System.out.println(connection);
            statement = connection.createStatement();

            // Use the database 'World'.
            statement.executeQuery("Use weather_data");
        }
        catch (SQLException SQLexc) {
            SQLexc.printStackTrace();
        }
        finally {
            try {
                if(statement != null) {
                    statement.close();
                    exists = true;
                }
            }
            catch (SQLException SQLexc) {
                SQLexc.printStackTrace();
                statement = null;
            }
        }
        return exists;
    }

    // Method to make a connection to the database.
    private void makeConnection() {
        FileInputStream input = null;

        try {
            // Getting the properties from the respective file.
            Properties properties = new Properties();
            input = new FileInputStream("resources/databaseProperties.txt");
            properties.load(input);
            input.close();

            String db_url = properties.getProperty("db_url");
            String db_params = properties.getProperty("db_params");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            System.out.println(db_url + username+ password);
            // Create a connection statement
            connection = DriverManager.getConnection(db_url /* + db_params */, username, password);

            // db_params is left out because when not specified in the dataProperties.txt file,
            // will generate a NullPointerException.

            System.out.println("Connection has been set up with the stored properties! YAHOO!");
        }
        catch (SQLException SQLexc) {
            System.err.println("Connection error occurred");
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            try {
                if(input != null) {
                    input.close();
                }
            }
            catch (Exception exc) {
                exc.printStackTrace();
                return;   // REFACTOR THIS STATEMENT
            }
        }
    }

    /**
     * Closes the connection.
     */
    public void close() {
        try {
            connection.close();

            uniqueInstance = null;
            connection = null;
        }
        catch (SQLException SQLexc) {
            SQLexc.printStackTrace();
        }
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

}