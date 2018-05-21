import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;

import java.awt.event.ActionEvent;
import java.util.Observable;

public class TableController  {
    //db connection class
    private DBmanager dBmanager;
    private XYChart.Series<String, Number> series = new XYChart.Series<>();

    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;
    @FXML private LineChart lineChart;
    @FXML private ToggleButton ConnectB;
    //constructor
    public TableController(){
        dBmanager = new DBmanager();
        init();

    }

    private void init(){
        series.setName("heartbeat");
        series.getData().add(new XYChart.Data<String, Number>("0",0));

    }

    //handler if the switch to table button is pressed
    public void tableBpressed(){
        System.out.println("hallo");
        tester();
    }

    //button to press if want to get data from DB
    public void connectB(){
        if(ConnectB.isSelected()){
            ConnectB.setText("Disconnect");
            if(!dBmanager.dbExists()) {
                System.out.println("Creating connection was impossible try again later");
            }
            else {
                System.out.println("Successfully connected");
            }
        }
        else{
            ConnectB.setText("Connect");
            System.out.println("Disconnected");
        }
    }


    private void tester(){
        series.getData().add(new XYChart.Data<String, Number>("5",60));

    }
}
