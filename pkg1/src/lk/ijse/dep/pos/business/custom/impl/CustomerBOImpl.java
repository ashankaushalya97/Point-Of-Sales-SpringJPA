package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.CustomerBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.db.JPAUtil;
import lk.ijse.dep.pos.dto.CustomerDTO;
import lk.ijse.dep.pos.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerBOImpl implements CustomerBO {

    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private OrderDAO orderDAO;

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();
        customerDAO.save(new Customer(customer.getId(),customer.getName(),customer.getAddress()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();
        customerDAO.update(new Customer(customer.getId(),customer.getName(),customer.getAddress()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {

        EntityManager em= JPAUtil.getEmf().createEntityManager();
        customerDAO.setEntityManager(em);
        orderDAO.setEntityManager(em);

        em.getTransaction().begin();
        if (orderDAO.existsByCustomerId(customerId)){
            throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
        }
        customerDAO.delete(customerId);

        em.getTransaction().commit();
        em.close();

    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {

        EntityManager em= JPAUtil.getEmf().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();

        List<Customer> alCustomers = customerDAO.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : alCustomers) {
            dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
        }

        em.getTransaction().commit();
        em.close();
        return dtos;

    }

    @Override
    public String getLastCustomerId() throws Exception {
        String customerId=null;
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();
        customerId = customerDAO.getLastCustomerId();
        em.getTransaction().commit();
        em.close();
        return customerId;
    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {

        Customer customer;
        EntityManager em= JPAUtil.getEmf().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();

        customer = customerDAO.find(customerId);

        em.getTransaction().commit();
        em.close();

        return new CustomerDTO(customer.getCustomerId(),
                customer.getName(), customer.getAddress());
    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        List<String> ids;

        EntityManager em= JPAUtil.getEmf().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();

        List<Customer> customers = customerDAO.findAll();
        ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getCustomerId());
        }

        em.getTransaction().commit();
        em.close();

        return ids;
    }
}
