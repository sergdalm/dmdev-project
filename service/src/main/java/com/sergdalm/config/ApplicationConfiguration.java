package com.sergdalm.config;

import com.sergdalm.dao.AddressRepository;
import com.sergdalm.dao.AppointmentRepository;
import com.sergdalm.dao.ReviewRepository;
import com.sergdalm.dao.ServiceRepository;
import com.sergdalm.dao.ServiceSaleRepository;
import com.sergdalm.dao.SpecialistAvailableTimeRepository;
import com.sergdalm.dao.SpecialistServiceRepository;
import com.sergdalm.dao.UserInfoRepository;
import com.sergdalm.dao.UserRepository;
import com.sergdalm.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
@ComponentScan(basePackages = "com.sergdalm.dao")
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    public AddressRepository addressRepository() {
        return new AddressRepository(sessionFactory());
    }

    @Bean
    public AppointmentRepository appointmentRepository() {
        return new AppointmentRepository(sessionFactory());
    }

    @Bean
    public ReviewRepository reviewRepository() {
        return new ReviewRepository(sessionFactory());
    }

    @Bean
    public ServiceRepository serviceRepository() {
        return new ServiceRepository(sessionFactory());
    }

    @Bean
    public ServiceSaleRepository serviceSaleRepository() {
        return new ServiceSaleRepository(sessionFactory());
    }

    @Bean
    public SpecialistAvailableTimeRepository specialistAvailableTimeRepository() {
        return new SpecialistAvailableTimeRepository(sessionFactory());
    }

    @Bean
    public SpecialistServiceRepository specialistServiceRepository() {
        return new SpecialistServiceRepository(sessionFactory());
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(sessionFactory());
    }

    @Bean
    public UserInfoRepository userInfoRepository() {
        return new UserInfoRepository(sessionFactory());
    }

    @PreDestroy
    void closeSessionFactory() {
        sessionFactory().close();
    }
}
