package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.SuperDAO;
import lk.ijse.dep.pos.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {

    List<CustomEntity> getSearchInfo(String searchText) throws Exception;
    /**
     *
     * @param query customerId, customerName, orderId, orderDate
     * @return
     * @throws Exception
     */
    List<CustomEntity> getOrdersInfo() throws Exception;

}
