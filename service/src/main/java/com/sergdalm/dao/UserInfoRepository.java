package com.sergdalm.dao;

import com.sergdalm.entity.UserInfo;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoRepository extends RepositoryBase<UserInfo, Integer> {

    public UserInfoRepository(SessionFactory sessionFactory) {
        super(UserInfo.class, sessionFactory);
    }
}
