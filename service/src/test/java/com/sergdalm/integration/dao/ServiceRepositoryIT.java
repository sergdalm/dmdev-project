package com.sergdalm.integration.dao;

import com.sergdalm.EntityUtil;
import com.sergdalm.dao.ServiceRepository;
import com.sergdalm.entity.Service;
import com.sergdalm.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class ServiceRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final ServiceRepository serviceRepository;

    @Test
    void SaveAndFindById() {
        Service service = EntityUtil.getServiceClassicMassage();
        serviceRepository.save(service);
        entityManager.flush();
        entityManager.clear();

        Optional<Service> actualOptionalService = serviceRepository.findById(service.getId());

        assertThat(actualOptionalService).isPresent();
        assertEquals(service, actualOptionalService.get());
    }

    @Test
    void findAll() {
        Service service = EntityUtil.getServiceClassicMassage();
        serviceRepository.save(service);
        entityManager.flush();
        entityManager.clear();

        List<Service> actualServiceList = serviceRepository.findAll();

        assertThat(actualServiceList).hasSize(1);
        assertThat(actualServiceList).contains(service);
    }

    @Test
    void saveAndUpdate() {
        Service service = EntityUtil.getServiceClassicMassage();
        serviceRepository.save(service);
        entityManager.flush();
        entityManager.clear();
        String newDescription = "Very good for health";
        service.setDescription(newDescription);
        serviceRepository.save(service);
        entityManager.flush();
        entityManager.clear();

        Optional<Service> actualOptionalService = serviceRepository.findById(service.getId());

        assertThat(actualOptionalService).isPresent();
        assertEquals(service, actualOptionalService.get());
        assertEquals(newDescription, actualOptionalService.get().getDescription());
    }

    @Test
    void saveAndDelete() {
        Service service = EntityUtil.getServiceClassicMassage();
        serviceRepository.save(service);

        serviceRepository.delete(service);
        Optional<Service> actualOptionalService = serviceRepository.findById(service.getId());

        assertThat(actualOptionalService).isNotPresent();
    }
}