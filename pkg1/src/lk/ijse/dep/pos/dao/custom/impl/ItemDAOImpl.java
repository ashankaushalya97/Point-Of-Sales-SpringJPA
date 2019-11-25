package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.CrudUtil;
import lk.ijse.dep.pos.dao.custom.ItemDAO;
import lk.ijse.dep.pos.entity.Item;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {


    @Override
    public String getLastItemCode() throws Exception {
        return (String) entityManager.createNativeQuery("SELECT code FROM Item ORDER BY code DESC LIMIT 1").getSingleResult();
    }

}
