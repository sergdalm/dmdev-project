package com.sergdalm.integration.query;

import com.sergdalm.query.HqlQuery;
import com.sergdalm.util.HibernateTestUtil;
import com.sergdalm.util.TestDataImporter;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HqlQueryIT {

    private final HqlQuery hqlQuery = new HqlQuery();

    private static final SessionFactory SESSION_FACTORY = HibernateTestUtil.buildSessionFactory();

    @BeforeAll
    public static void initDb() {
        TestDataImporter.importData(SESSION_FACTORY);
    }

    @AfterAll
    public static void finish() {
        SESSION_FACTORY.close();
    }

    @Test
    void getAmountEarnedBySpecialist() {
        @Cleanup Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();

        Integer actualAmount = hqlQuery.getAmountEarnedBySpecialist(session,
                TestDataImporter.specialistDmitry.getId());
        Assertions.assertEquals(2000, actualAmount);

        session.getTransaction().commit();
    }
}
