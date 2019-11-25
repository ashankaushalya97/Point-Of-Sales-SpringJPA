package lk.ijse.dep.pos.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Orders implements SuperEntity{
    @Id
    private int id;
    private Date date;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "orders", cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    private
    List<OrderDetail> orderDetails = new ArrayList<>();

    public Orders() {
    }

    public Orders(int id, Date date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", customer=" + customer +
                '}';
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetail orderDetails) {
        this.orderDetails.add(orderDetails);
    }
}
