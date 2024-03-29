package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.ItemBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.db.JPAUtil;
import lk.ijse.dep.pos.dto.ItemDTO;
import lk.ijse.dep.pos.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemBOImpl implements ItemBO {

    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        itemDAO.setEntityManager(em);
        em.getTransaction().begin();
        itemDAO.save(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        itemDAO.setEntityManager(em);
        em.getTransaction().begin();
        itemDAO.update(new Item(item.getCode(),
                item.getDescription(), item.getUnitPrice(), item.getQtyOnHand()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteItem(String itemCode) throws Exception {
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        itemDAO.setEntityManager(em);
        orderDetailDAO.setEntityManager(em);
        em.getTransaction().begin();
        if (orderDetailDAO.existsByItemCode(itemCode)){
            throw new AlreadyExistsInOrderException("Item already exists in an order, hence unable to delete");
        }
        System.out.println("Record not not exist in orderDetails");
        itemDAO.delete(itemCode);

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
            EntityManager em= JPAUtil.getEmf().createEntityManager();
            itemDAO.setEntityManager(em);
            em.getTransaction().begin();
            List<Item> allItems = itemDAO.findAll();
            List<ItemDTO> dtos = new ArrayList<>();
            for (Item item : allItems) {
                dtos.add(new ItemDTO(item.getCode(),
                        item.getDescription(),
                        item.getQtyOnHand(),
                        item.getUnitPrice()));
            }

            em.getTransaction().commit();
            em.close();
            return dtos;


    }

    @Override
    public String getLastItemCode() throws Exception {
        String itemCode;

        EntityManager em= JPAUtil.getEmf().createEntityManager();
        itemDAO.setEntityManager(em);
        em.getTransaction().begin();
        itemCode= itemDAO.getLastItemCode();

        em.getTransaction().commit();
        em.close();

        return itemCode;
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        Item item;
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        itemDAO.setEntityManager(em);
        em.getTransaction().begin();
        item = itemDAO.find(itemCode);

        em.getTransaction().commit();
        em.close();
        return new ItemDTO(item.getCode(),
                item.getDescription(),
                item.getQtyOnHand(),
                item.getUnitPrice());
    }

    @Override
    public List<String> getAllItemCodes() throws Exception {
        List<Item> allItems;
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        itemDAO.setEntityManager(em);
        em.getTransaction().begin();
        allItems = itemDAO.findAll();
        em.getTransaction().commit();
        em.close();
        List<String> codes = new ArrayList<>();
        for (Item item : allItems) {
            codes.add(item.getCode());
        }
        return codes;
    }
}
