package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.config.BeanProvider;
import com.sergdalm.dao.AddressRepository;
import com.sergdalm.entity.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressRepositoryIT {

    private final SessionFactory sessionFactory = BeanProvider.getSessionFactory();
    private final AddressRepository addressRepository = BeanProvider.getAddressRepository();

    @Test
    void saveAndFindById() {
        Session session = sessionFactory.getCurrentSession();
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
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Address address1 = EntityUtil.getAddress();
        Address address2 = Address.builder()
                .addressName("Malaya Morskaya 16")
                .description("Go out Admiralteyskaya subway station and go to the right")
                .build();
        Address address3 = Address.builder()
                .addressName("Ploshad Stachek 9")
                .description("Go out Narvsaky subway station and cross the street")
                .build();
        session.save(address1);
        session.save(address2);
        session.save(address3);
        session.flush();
        session.clear();

        List<Address> actualAddresses = addressRepository.findAll();

        assertThat(actualAddresses).hasSize(3);
        assertThat(actualAddresses).contains(address1, address2, address3);

        session.getTransaction().rollback();
    }

    @Test
    void update() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Address address = EntityUtil.getAddress();
        session.save(address);
        session.flush();
        session.clear();

        String newDescription = "Nearby subway Gostinny drov, go to the right";
        address.setDescription(newDescription);
        addressRepository.update(address);
        session.flush();
        session.clear();
        Optional<Address> actualOptionalAddress = addressRepository.findById(address.getId());

        assertThat(actualOptionalAddress).isPresent();
        assertEquals(address, actualOptionalAddress.get());
        assertEquals(newDescription, actualOptionalAddress.get().getDescription());

        session.getTransaction().rollback();
    }

    @Test
    void SaveAndDelete() {
        Session session = sessionFactory.getCurrentSession();
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