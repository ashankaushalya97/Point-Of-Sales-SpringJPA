package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.entity.Customer;

import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, String> {

    String getLastCustomerId() throws Exception;

}
