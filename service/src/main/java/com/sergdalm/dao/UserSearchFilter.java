package com.sergdalm.dao;

import com.sergdalm.dao.filter.UserFilter;
import com.sergdalm.dto.UserWithInfoDto;

import java.util.List;

public interface UserSearchFilter {

    List<UserWithInfoDto> findAll(UserFilter userFilter);
}
