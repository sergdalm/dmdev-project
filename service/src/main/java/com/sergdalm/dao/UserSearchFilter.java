package com.sergdalm.dao;

import com.sergdalm.dao.filter.UserFilter;
import com.sergdalm.entity.User;

import java.util.List;

public interface UserSearchFilter {

    List<User> findAll(UserFilter userFilter);
}
