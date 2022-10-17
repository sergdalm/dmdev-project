package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.AddressRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressRepositoryIT {

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();
    private final AddressRepository addressRepository = new AddressRepository(SESSION_FACTORY);

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }


    @Test
    void saveAndFindById() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Address address = EntityUtil.getAddress();
        addressRepository.save(address);
        session.flush();
        session.clear();

        Optional<Address> actualOptionalAddress = addressRepository.findById(address.getId());

        assertThat(actualOptionalAddress).isPresent();
        assertEquals(address, actualOptionalAddress.get());

        session.getTransaction().rollback();
    }

    @Test
    void findAll() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        List<Address> actualAddresses = addressRepository.findAll();
        List<String> actualAddressNames = actualAddresses.stream()
                .map(Address::getAddressName)
                .toList();

        assertThat(actualAddressNames).hasSize(2);
        assertThat(actualAddressNames).contains("Malaya Morskaya 16", "Ploshad Stachek 9");

        session.getTransaction().rollback();
    }

    @Test
    void saveAndUpdate() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Address address = EntityUtil.getAddress();
        addressRepository.save(address);
        session.flush();
        session.clear();
        address.setDescription("Nearby subway Gostinny drov, go to the right");
        addressRepository.update(address);
        session.flush();
        session.clear();
        Optional<Address> actualOptionalAddress = addressRepository.findById(address.getId());

        assertThat(actualOptionalAddress).isPresent();
        assertEquals(address, actualOptionalAddress.get());

        session.getTransaction().rollback();
    }

    @Test
    void SaveAndDelete() {
        Session session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();

        Address address = EntityUtil.getAddress();
        addressRepository.save(address);
        session.flush();
        session.clear();
        addressRepository.delete(address);
        Optional<Address> actualOptionalAddress = addressRepository.findById(address.getId());

        assertThat(actualOptionalAddress).isNotPresent();

        session.getTransaction().rollback();
    }

}