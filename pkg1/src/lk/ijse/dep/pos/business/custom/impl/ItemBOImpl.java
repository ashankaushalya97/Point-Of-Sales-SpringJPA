package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.db.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAIL);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);

    @Override
    public void saveItem(ItemDTO item) throws Exception {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            itemDAO.setSession(session);
            session.beginTransaction();
            itemDAO.save(new Item(item.getCode(),
                    item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            itemDAO.setSession(session);
            session.beginTransaction();
            itemDAO.update(new Item(item.getCode(),
                    item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteItem(String itemCode) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            itemDAO.setSession(session);
            orderDetailDAO.setSession(session);
            session.beginTransaction();
            if (orderDetailDAO.existsByItemCode(itemCode)){
                throw new AlreadyExistsInOrderException("Item already exists in an order, hence unable to delete");
            }
            System.out.println("Record not not exist in orderDetails");
            itemDAO.delete(itemCode);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();
            List<Item> allItems = itemDAO.findAll();
            List<ItemDTO> dtos = new ArrayList<>();
            for (Item item : allItems) {
                dtos.add(new ItemDTO(item.getCode(),
                        item.getDescription(),
                        item.getQtyOnHand(),
                        item.getUnitPrice()));
            }
            session.getTransaction().commit();
            return dtos;
        }
    }

    @Override
    public String getLastItemCode() throws Exception {
        String itemCode;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            itemDAO.setSession(session);
            session.beginTransaction();
            itemCode= itemDAO.getLastItemCode();

            session.getTransaction().commit();
        }

        return itemCode;
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        Item item;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            itemDAO.setSession(session);
            session.beginTransaction();
            item = itemDAO.find(itemCode);

            session.getTransaction().commit();
        }
        return new ItemDTO(item.getCode(),
                item.getDescription(),
                item.getQtyOnHand(),
                item.getUnitPrice());
    }

    @Override
    public List<String> getAllItemCodes() throws Exception {
        List<Item> allItems;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            itemDAO.setSession(session);
            session.beginTransaction();
            allItems = itemDAO.findAll();

            session.getTransaction().commit();
        }
        List<String> codes = new ArrayList<>();
        for (Item item : allItems) {
            codes.add(item.getCode());
        }
        return codes;
    }
}
