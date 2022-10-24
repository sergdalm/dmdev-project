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
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanProvider {

    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

    public static SessionFactory getSessionFactory() {
        return context.getBean(SessionFactory.class);
    }

    public static AddressRepository getAddressRepository() {
        return context.getBean(AddressRepository.class);
    }

    public static AppointmentRepository getAppointmentRepository() {
        return context.getBean(AppointmentRepository.class);
    }

    public static ReviewRepository getReviewRepository() {
        return context.getBean(ReviewRepository.class);
    }

    public static ServiceRepository getServiceRepository() {
        return context.getBean(ServiceRepository.class);
    }

    public static ServiceSaleRepository getServiceSaleRepository() {
        return context.getBean(ServiceSaleRepository.class);
    }

    public static SpecialistServiceRepository getSpecialistServiceRepository() {
        return context.getBean(SpecialistServiceRepository.class);
    }

    public static SpecialistAvailableTimeRepository getSpecialistAvailableTimeRepository() {
        return context.getBean(SpecialistAvailableTimeRepository.class);
    }

    public static UserRepository getUserRepository() {
        return context.getBean(UserRepository.class);
    }

    public static UserInfoRepository getUserInfoRepository() {
        return context.getBean(UserInfoRepository.class);
    }
}
