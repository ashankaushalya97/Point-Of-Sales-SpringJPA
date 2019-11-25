package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import org.hibernate.Session;

import java.util.List;

class QueryDAOImplTest {

    public static void main(String[] args) throws Exception {
        new QueryDAOImplTest().getOrdersInfo();
    }



    void getOrderInfo() throws Exception {
        List<CustomEntity> ordersInfo = new QueryDAOImpl().getOrdersInfo();
        for (CustomEntity customEntity : ordersInfo) {
            System.out.println(customEntity.getOrderId());
            System.out.println(customEntity.getCustomerName());
            System.out.println(customEntity.getOrderTotal());
        }

    }

    void testGetOrderInfo() {
    }

    void getOrdersInfo() throws Exception {
        QueryDAO queryDAO = new QueryDAOImpl();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            queryDAO.setSession(session);
            session.beginTransaction();

            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo();
            System.out.println(ordersInfo.toString());

            session.getTransaction().commit();
        }
    }
}
