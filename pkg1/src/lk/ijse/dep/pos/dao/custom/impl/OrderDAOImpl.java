package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.entity.Orders;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDAOImpl extends CrudDAOImpl<Orders,Integer> implements OrderDAO {

    @Override
    public int getLastOrderId() throws Exception {
        return (int) entityManager.createNativeQuery("Select id FROM Orders Order BY id DESC LIMIT 1").getSingleResult();
    }

    @Override
    public boolean existsByCustomerId(String customerId) throws Exception {
        return entityManager.createNativeQuery("SELECT * FROM Orders WHERE customer_id=?1").setParameter(1,customerId).getResultList().size()>0;
    }

}
