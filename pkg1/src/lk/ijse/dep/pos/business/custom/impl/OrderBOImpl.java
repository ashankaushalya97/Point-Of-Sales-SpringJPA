package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.*;

import lk.ijse.dep.pos.db.JPAUtil;
import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;
import lk.ijse.dep.pos.entity.*;
import lk.ijse.dep.pos.db.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);
    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    private QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);
    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);

    @Override
    public int getLastOrderId() throws Exception {
        int orderId;
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        orderDAO.setEntityManager(em);
        em.getTransaction().begin();
        orderId=orderDAO.getLastOrderId();

        em.getTransaction().commit();
        em.close();
        return orderId;
    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {

        EntityManager em= JPAUtil.getEmf().createEntityManager();
        orderDAO.setEntityManager(em);
        itemDAO.setEntityManager(em);
        customerDAO.setEntityManager(em);
        orderDetailDAO.setEntityManager(em);
        em.getTransaction().begin();

        int oId = order.getId();
        orderDAO.save(new Orders(oId,new java.sql.Date(new Date().getTime()),em.getReference(Customer.class,order.getCustomerId())));

        for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
            orderDetailDAO.save(new OrderDetail(oId,orderDetail.getCode(),orderDetail.getQty(),orderDetail.getUnitPrice()));

            Item item = itemDAO.find(orderDetail.getCode());
            item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
            itemDAO.update(item);

        }
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<OrderDTO2> getOrderInfo() throws Exception {
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        queryDAO.setEntityManager(em);
        em.getTransaction().begin();
        List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo();
        List<OrderDTO2> dtos = new ArrayList<>();
        for (CustomEntity info : ordersInfo) {
            dtos.add(new OrderDTO2(info.getOrderId(),
                    info.getOrderDate(),info.getCustomerId(),info.getCustomerName(),info.getOrderTotal()));
        }

        em.getTransaction().commit();
        em.close();
        return dtos;
    }

    @Override
    public List<OrderDTO2> getSearchInfo(String searchText) throws Exception {
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        queryDAO.setEntityManager(em);
        em.getTransaction().begin();
        List<CustomEntity> ordersInfo = queryDAO.getSearchInfo(searchText);
        List<OrderDTO2> dtos = new ArrayList<>();
        for (CustomEntity info : ordersInfo) {
            dtos.add(new OrderDTO2(info.getOrderId(),
                    info.getOrderDate(),info.getCustomerId(),info.getCustomerName(),info.getOrderTotal()));
        }

        em.getTransaction().commit();
        em.close();
        return dtos;
    }
}
