package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.AddressRepository;
import com.sergdalm.entity.Address;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class AddressRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final AddressRepository addressRepository;

    @Test
    void test() {
        Address address = EntityUtil.getAddressNarvskaya();
        addressRepository.save(address);
        entityManager.flush();
        entityManager.clear();

        Optional<Address> optionalAddress = addressRepository.findById(address.getId());

        assertThat(optionalAddress).isPresent();
        assertEquals(address, optionalAddress.get());
    }

    @Test
    void saveAndFindById() {
        Address address = EntityUtil.getAddressNarvskaya();
        addressRepository.save(address);
        entityManager.flush();
        entityManager.clear();

        Optional<Address> actualOptionalAddress = addressRepository.findById(address.getId());

        assertThat(actualOptionalAddress).isPresent();
        assertEquals(address, actualOptionalAddress.get());
    }

    @Test
    void findAll() {
        Address address1 = EntityUtil.getAddressNarvskaya();
        Address address2 = Address.builder()
                .addressName("Malaya Morskaya 16")
                .description("Go out Admiralteyskaya subway station and go to the right")
                .build();
        Address address3 = Address.builder()
                .addressName("Ploshad Stachek 9")
                .description("Go out Narvsaky subway station and cross the street")
                .build();
        entityManager.persist(address1);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.flush();
        entityManager.clear();

        List<Address> actualAddresses = addressRepository.findAll();

        assertThat(actualAddresses).hasSize(3);
        assertThat(actualAddresses).contains(address1, address2, address3);
    }

    @Test
    void update() {
        Address address = EntityUtil.getAddressNarvskaya();
        entityManager.persist(address);
        entityManager.flush();
        entityManager.clear();

        String newDescription = "Nearby subway Gostinny drov, go to the right";
        address.setDescription(newDescription);
        addressRepository.save(address);
        entityManager.flush();
        entityManager.clear();
        Optional<Address> actualOptionalAddress = addressRepository.findById(address.getId());

        assertThat(actualOptionalAddress).isPresent();
        assertEquals(address, actualOptionalAddress.get());
        assertEquals(newDescription, actualOptionalAddress.get().getDescription());
    }

    @Test
    void delete() {
        Address address = EntityUtil.getAddressNarvskaya();
        addressRepository.save(address);

        addressRepository.delete(address);
        Optional<Address> actualOptionalAddress = addressRepository.findById(address.getId());

        assertThat(actualOptionalAddress).isNotPresent();
    }
}