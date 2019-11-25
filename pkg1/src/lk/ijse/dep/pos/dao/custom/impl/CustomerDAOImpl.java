package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.CrudUtil;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.entity.Customer;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl extends CrudDAOImpl<Customer,String>  implements CustomerDAO{

    @Override
    public String getLastCustomerId() throws Exception {
        return (String) entityManager.createNativeQuery("SELECT customer_id FROM Customer order by customer_id desc LIMIT 1 ").getSingleResult();
    }

}
