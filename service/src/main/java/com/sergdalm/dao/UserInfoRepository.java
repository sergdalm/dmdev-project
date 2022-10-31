package com.sergdalm.dao;

import com.sergdalm.entity.UserInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserInfoRepository extends RepositoryBase<UserInfo, Integer> {

    public UserInfoRepository(EntityManager entityManager) {
        super(UserInfo.class, entityManager);
    }
}
