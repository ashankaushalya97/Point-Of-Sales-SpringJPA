package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudUtil;
import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    private EntityManager entityManager;


    @Override
    public List<CustomEntity> getSearchInfo(String searchText) throws Exception {
//        String search = "%"+searchText+"%";
//        NativeQuery nativeQuery = entityManager.createNativeQuery("SELECT O.id as orderId, C.customer_id as customerId, C.name as customerName, O.date as orderDate, SUM(OD.qty*OD.unit_price) as orderTotal FROM Customer C Inner JOIN Orders O ON  C.customer_id = O.customer_id INNER JOIN OrderDetail OD on O.id = OD.order_id WHERE O.id LIKE ?1 OR C.customer_id LIKE ?2 OR C.name LIKE ?3 OR O.date LIKE ?4 GROUP BY  O.id ")
//                .setParameter(1,search).setParameter(2,search).setParameter(3,search).setParameter(4,search);
//        Query<CustomEntity> query = nativeQuery.
//                setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
//        List<CustomEntity> list = query.list();
//        System.out.println(list.toString());
//        return list;
        return null;
    }

    @Override
    public List<CustomEntity> getOrdersInfo() throws Exception {
//        NativeQuery nativeQuery = session.createNativeQuery("SELECT O.id as orderId, C.customer_id as customerId, C.name as customerName, O.date as orderDate, SUM(OD.qty * OD.unit_price) AS orderTotal  FROM Customer C INNER JOIN `Orders` O ON C.customer_id=O.customer_id INNER JOIN OrderDetail OD on O.id = OD.order_id GROUP BY O.id");
//        Query<CustomEntity> query = nativeQuery.
//                setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
//        List<CustomEntity> list = query.list();
//        System.out.println(list.toString());
//
//        return list;
        return null;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {

    }
}
