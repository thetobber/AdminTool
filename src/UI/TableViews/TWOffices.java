package UI.TableViews;

import Domain.Office;
import Foundation.DAO.DBController;
import UI.Navigation.ActionBar;
import UI.Navigation.UIButton;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;

public class TWOffices
{
    public static TableView<Office> tableViewOffices;
    public static ObservableList<Office> offices;
    public static Office selected;

    public TWOffices()
    {

    }

    public BorderPane getView()
    {
        BorderPane subRoot = new BorderPane();
        subRoot.setPadding(new Insets(10, 20, 10, 20));

        subRoot.setCenter(tableViewOffices());

        ActionBar bb = new ActionBar(UIButton.officesButton.getText());
        subRoot.setBottom(bb.getBottomBar());

        return subRoot;
    }

    public TableView<Office> tableViewOffices()
    {
        tableViewOffices = new TableView<>();
        tableViewOffices.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Office, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Office, String> column2 = new TableColumn<>("Location");
        column2.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Office, String> column3 = new TableColumn<>("Capacity");
        column3.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        TableColumn<Office, String> column4 = new TableColumn<>("Consultants Connected");
        column4.setCellValueFactory(new PropertyValueFactory<>("consultantsConnected"));

        tableViewOffices.getColumns().addAll(column1, column2, column3, column4);

        // Fill TableView
        DBController controller = new DBController();
        offices = controller.getAllOffices();

        // Enable selecting an item
        tableViewOffices.setOnMouseClicked(event ->
        {
            if (tableViewOffices.getSelectionModel().getSelectedItem() != null)
            {
                System.out.println(tableViewOffices.getSelectionModel().getSelectedIndex());
                selected = tableViewOffices.getSelectionModel().getSelectedItem();
            }
            if (event.getButton().equals(MouseButton.PRIMARY))
            {
                if (event.getClickCount() == 2)
                {
                    UIButton.editButton.fire();
                }
            }
        });

        // Enable KEY bindings
        tableViewOffices.setOnKeyPressed(e ->
        {
            // Enable opening an item with ENTER key
            if (e.getCode() == KeyCode.ENTER)
            {
                selected = tableViewOffices.getSelectionModel().getSelectedItem();
                UIButton.editButton.fire();
            }
            // Enable adding an item with PLUS key
            if (e.getCode() == KeyCode.PLUS)
            {
                UIButton.addButton.fire();
            }
            if (e.getCode() == KeyCode.DELETE)
            {
                UIButton.deleteButton.fire();
            }
            if (e.isControlDown() && (e.getCode() == KeyCode.F)) {
                ActionBar.searchField.requestFocus();
            }
            if (e.isControlDown() && (e.getCode() == KeyCode.UP)) {
                UIButton.moveUpButton.fire();
            }
            if (e.isControlDown() && (e.getCode() == KeyCode.DOWN)) {
                UIButton.moveDownButton.fire();
            }
        });

        return tableViewOffices;
    }
}
