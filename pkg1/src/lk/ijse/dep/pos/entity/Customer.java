package lk.ijse.dep.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements SuperEntity{
    @Id
    @Column(name = "customer_id")
    private String customerId;
    private String name;
    private String address;
//    private Gender gender;
    @OneToMany(mappedBy = "customer")
    private List<Orders> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String customerId, String name, String address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

//    public Customer(String customerId, String name, String address, Gender gender) {
//        this.customerId = customerId;
//        this.name = name;
//        this.address = address;
//        this.setGender(gender);
//    }

    //    public Customer(String customerId, String name, String address) {
//        this.customerId = customerId;
//        this.name = name;
//        this.address = address;
//    }



    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
//                ", gender=" + gender +
                '}';
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void addOrders(Orders orders) {
        orders.setCustomer(this);
        this.orders.add(orders);
    }
    public void removeOrder(Orders orders){
        if(orders.getCustomer()!=this){
            throw new RuntimeException("Something went wrong!");
        }
        orders.setCustomer(null);
        this.orders.remove(orders);
    }
}
