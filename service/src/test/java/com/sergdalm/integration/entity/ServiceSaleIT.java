package com.sergdalm.integration.entity;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.entity.Address;
import com.sergdalm.entity.Service;
import com.sergdalm.entity.ServiceSale;
import com.sergdalm.entity.SpecialistService;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ServiceSaleIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final User specialist = EntityUtil.getUserSpecialist();
    private final Address address = EntityUtil.getAddress();
    private final Service service = EntityUtil.getService();
    private final SpecialistService specialistService = EntityUtil.getSpecialistService();
    private final ServiceSale serviceSale = EntityUtil.getServiceSale();

    @BeforeEach
    void saveEntities() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.persist(specialist);
        session.persist(address);
        session.persist(service);

        specialistService.setSpecialist(specialist);
        specialistService.setService(service);
        session.persist(specialistService);

        serviceSale.setSpecialistService(specialistService);
        serviceSale.setAddress(address);
        session.persist(serviceSale);

        session.getTransaction().commit();
    }

    @Test
    void shouldGetServiceSale() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ServiceSale actualServiceSale = session.get(
                ServiceSale.class, serviceSale.getId()
        );

        assertEquals(serviceSale, actualServiceSale);
        assertEquals(specialistService, actualServiceSale.getSpecialistService());
        assertEquals(address, actualServiceSale.getAddress());

        session.getTransaction().rollback();
    }

    @Test
    void shouldUpdateServiceSale() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        serviceSale.setSalePrice(900);
        session.update(serviceSale);

        session.flush();
        session.clear();

        ServiceSale actualServiceSale = session.get(
                ServiceSale.class, serviceSale.getId()
        );

        assertEquals(serviceSale, actualServiceSale);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteServiceSale() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(serviceSale);

        session.flush();
        session.clear();

        ServiceSale actualServiceSale =
                session.get(ServiceSale.class, serviceSale.getId());

        assertNull(actualServiceSale);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteServiceSaleWhenDeletingSpecialistService() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(specialistService);

        session.flush();
        session.clear();

        ServiceSale actualServiceSale =
                session.get(ServiceSale.class, serviceSale.getId());

        assertNull(actualServiceSale);

        session.getTransaction().rollback();
    }

    @Test
    void shouldDeleteServiceSaleWhenDeletingAddress() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.delete(address);

        session.flush();
        session.clear();

        ServiceSale actualServiceSale =
                session.get(ServiceSale.class, serviceSale.getId());

        assertNull(actualServiceSale);

        session.getTransaction().rollback();
    }

    @AfterEach
    void cleanDataBse() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.createSQLQuery("delete from service_sale")
                .executeUpdate();
        session.createSQLQuery("delete from specialist_service")
                .executeUpdate();
        session.createSQLQuery("delete from users")
                .executeUpdate();
        session.createSQLQuery("delete from service")
                .executeUpdate();
        session.createSQLQuery("delete from address")
                .executeUpdate();

        session.getTransaction().commit();
    }
}
