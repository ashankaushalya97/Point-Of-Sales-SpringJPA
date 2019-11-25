package lk.ijse.dep.pos.db;


import lk.ijse.dep.crypto.DEPCrypt;
import lk.ijse.dep.pos.entity.Customer;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Orders;
import lk.ijse.dep.pos.entity.OrderDetail;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static String username;
    private static String password;
    private static String host;
    private static String port;
    private static String database;


    private static SessionFactory buildSessionFactory() {

        //File propFile  = new File("src/application.properties");
        Properties properties = new Properties();

        try (InputStream fis = HibernateUtil.class.getResourceAsStream("/application.properties")){
            properties.load(fis);
        }catch (Exception e){
            Logger.getLogger("lk.ijse.dep.pos.Hibernate.HibernateUtil").log(Level.SEVERE,null,e);
            System.exit(2);
        }
        username = DEPCrypt.decode(properties.getProperty("hibernate.connection.username"),"dep4");
        password = DEPCrypt.decode(properties.getProperty("hibernate.connection.password"),"dep4");
        host = properties.getProperty("ijse.dep.ip");
        port = properties.getProperty("ijse.dep.port");
        database = properties.getProperty("ijse.dep.db");

        // (1)
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("application.properties")
                .applySetting("hibernate.connection.username", DEPCrypt.decode(properties.getProperty("hibernate.connection.username"),"dep4"))
                .applySetting("hibernate.connection.password",password)
                .build();

        // (2)
        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Orders.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(OrderDetail.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        // (3)
        return metadata.getSessionFactoryBuilder()
                .build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getHost() {
        return host;
    }

    public static String getPort() {
        return port;
    }

    public static String getDatabase() {
        return database;
    }


}
