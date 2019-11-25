package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.CrudUtil;
import lk.ijse.dep.pos.dao.custom.OrderDetailDAO;
import lk.ijse.dep.pos.entity.OrderDetail;
import lk.ijse.dep.pos.entity.OrderDetailPK;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail,OrderDetailPK> implements OrderDetailDAO {

    @Override
    public boolean existsByItemCode(String itemCode) throws Exception {
        System.out.println(itemCode);
        return entityManager.createNativeQuery("SELECT  * FROM OrderDetail Where item_code=?1").setParameter(1,itemCode).getResultList().size()>0;
    }

}
