



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 * The type DataTableDAO.
 */
public class DataTableDAO {

    // ObservableList and TableView objects.
    private ObservableList<ObservableList> data = FXCollections.observableArrayList();


    private static DataTableDAO uniqueInstance = null;
    private static Connection connection = null;

    /**
     * Instantiates a new DataTableDAO.
     *
     * @param db the db
     */
    protected DataTableDAO(DBmanager db) {     // with as pre-condition: dbExists();

        if( (connection = db.getConnection() ) == null) {
            System.err.println(">>> DataTableDAO: The database doesn't exist...");
        }
    }

    /**
     * Gets unique instance.
     *
     * @param db the db
     * @return the unique instance
     */
    public static synchronized DataTableDAO getUniqueInstance(DBmanager db) {
        if(uniqueInstance == null) {
            uniqueInstance = new DataTableDAO(db);
        }
        return uniqueInstance;
    }


    /**
     * Build and process the data received from the database.
     * The TableView dynamically adds table columns.
     * Finally, the data gets put in the dynamic TableView.
     */
    protected ResultSet buildData(long begin, long end) {
        ResultSet resultSet;
        PreparedStatement preparedStatement = null;

        try {
            // SQL for selecting all of the database 'World'
            String SQL = "SELECT * from Temperature";
            // Object initialization for the ResultSet.
            preparedStatement = connection.prepareStatement(SQL);
            // Execute the query.
            resultSet = preparedStatement.executeQuery();

            // Dynamically add the table columns proportionally and corresponding to the metadata of the database table columns.


            // Add the data to the ObservableList.

            // Add the contents of ObservableList 'data' to TableView 'tableView'.
            return resultSet;
        }
        catch(Exception exc) {
            //exc.printStackTrace();
            System.err.println("Error on building/processing data");
        }
      return null;
    }

    /**
     * Gets tableView.
     *
     * @return the tableView
     */



}
