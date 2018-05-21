


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
        System.out.println("Hallo");
//        if(!dbExists()) {
//            System.err.println(">>> DBManager: The database doesn't exist...");
//        }
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

    public Boolean dbExists() {
        Boolean exists = false;
        if(connection == null) {
           exists = makeConnection();
        }
        return exists;
    }

    // Method to make a connection to the database.
    private Boolean makeConnection() {
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
            return true;

        }
        catch (SQLException SQLexc) {
            System.err.println("Connection error occurred");
            return false;
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return false;
    }

    /**
     * Closes the connection.
     */
    public void close() {
        try {
            connection.close();

        }
        catch (SQLException SQLexc) {
            SQLexc.printStackTrace();
        }
    }

    public void Open() {
        try {
            connection.close();

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