package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.db.HibernateUtil;
import org.hibernate.Session;

class OrderDetailDAOImplTest {

    public static void main(String[] args) throws Exception {
        new OrderDetailDAOImplTest().existsByItemCode();
    }

    void existsByItemCode() throws Exception {
        OrderDetailDAOImpl itemDAO = new OrderDetailDAOImpl();
        boolean i001=false;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            itemDAO.setSession(session);
            session.beginTransaction();

            i001 = itemDAO.existsByItemCode("I001");
            session.getTransaction().commit();
        }

        System.out.println(i001);
    }

    void testFindAll() {

    }
}