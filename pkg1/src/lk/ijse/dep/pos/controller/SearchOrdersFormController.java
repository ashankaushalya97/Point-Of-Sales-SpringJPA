package lk.ijse.dep.pos.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.util.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class SearchOrdersFormController {
    public TextField txtSearch;
    public TableView<OrderTM> tblOrders;
    OrderBO orderBO = BOFactory.getInstance().getBO(BOTypes.ORDER);

    public void initialize() {
        // Let's map
        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrders.getItems().clear();
        ObservableList<OrderTM> olOrders = tblOrders.getItems();

        try {
            List<OrderDTO2> orderInfo = orderBO.getOrderInfo();
            for (OrderDTO2 orderDTO2 : orderInfo) {
                olOrders.add(new OrderTM(orderDTO2.getOrderId(),String.valueOf(orderDTO2.getOrderDate()),orderDTO2.getCustomerId(),orderDTO2.getCustomerName(),orderDTO2.getTotal()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*for (Order order : DB.orders) {
            String orderId = order.getOrderId();
            String orderDate = order.getOrderDate().toString();
            String customerId = order.getCustomerId();
            String customerName = null;

            for (CustomerTM customer : DB.customers) {
                if (customer.getId().equals(customerId)) {
                    customerName = customer.getName();
                    break;
                }
            }

            double total = 0.0;
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                double orderDetailTotal = orderDetail.getQty() * orderDetail.getUnitPrice();
                total += orderDetailTotal;
            }

            OrderTM orderTM = new OrderTM(orderId, orderDate, customerId, customerName, total);
            olOrders.add(orderTM);
        }*/


        ObservableList<OrderTM> olAllOrders =
                FXCollections.observableArrayList(olOrders);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String searchText = txtSearch.getText();

                if(searchText.equals(null) || searchText.equals("")){
                    initialize();
                    return;
                }

                ObservableList<OrderTM> tempOrders = tblOrders.getItems();

                List<OrderDTO2> orderInfo = null;
                try {
                    tblOrders.getItems().clear();
                    orderInfo = orderBO.getSearchInfo(searchText);
                    for (OrderDTO2 orderDTO2 : orderInfo) {
                        System.out.println("search");
                        tempOrders.add(new OrderTM(orderDTO2.getOrderId(),String.valueOf(orderDTO2.getOrderDate()),orderDTO2.getCustomerId(),orderDTO2.getCustomerName(),orderDTO2.getTotal()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                tblOrders.setItems(tempOrders);
            }
        });

    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/dep/pos/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.txtSearch.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void tblOrders_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        /*if (mouseEvent.getClickCount() == 2) {

            URL resource = this.getClass().getResource("/lk.ijse.dep.pos.view/PlaceOrderForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();
            Scene placeOrderScene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(placeOrderScene);
            secondaryStage.centerOnScreen();
            secondaryStage.setTitle("View Order");
            secondaryStage.setResizable(false);

            PlaceOrderFormController ctrl = fxmlLoader.getController();
            OrderTM selectedOrder = tblOrders.getSelectionModel().getSelectedItem();
            ctrl.initializeForSearchOrderForm(selectedOrder.getOrderId());

            secondaryStage.show();
        }*/
    }
}
