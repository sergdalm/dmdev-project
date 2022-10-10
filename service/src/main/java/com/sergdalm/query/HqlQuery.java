package com.sergdalm.query;

import com.sergdalm.entity.Appointment;
import com.sergdalm.entity.AppointmentStatus;
import com.sergdalm.entity.User;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;

import java.util.Map;

public class HqlQuery {

    public Integer getAmountEarnedBySpecialist(Session session, Integer userId) {
        RootGraph<User> userGraph = session.createEntityGraph(User.class);
        userGraph.addAttributeNodes("specialistAppointments");
        SubGraph<Appointment> specialistAppointmentSubGraph = userGraph.addSubgraph(
                "specialistAppointments", Appointment.class
        );
        specialistAppointmentSubGraph.addAttributeNodes("price");
        specialistAppointmentSubGraph.addAttributeNodes("status");

        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(),
                userGraph
        );

        User specialist = session.find(User.class, userId, properties);
        return specialist.getSpecialistAppointments().
                stream()
                .filter(a -> a.getStatus().equals(AppointmentStatus.COMPLETED_PAID))
                .mapToInt(Appointment::getPrice)
                .sum();
    }
}
