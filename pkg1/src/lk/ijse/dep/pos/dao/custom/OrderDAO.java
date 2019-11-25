package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.entity.Orders;

public interface OrderDAO extends CrudDAO<Orders, Integer> {

    int getLastOrderId() throws Exception;

    boolean existsByCustomerId(String customerId) throws Exception;

}
