package com.sergdalm.service;

import java.util.List;

public interface GeneralCrudService<ID, READ_DTO> {

    List<READ_DTO> findAll();

    boolean delete(ID id);
}
