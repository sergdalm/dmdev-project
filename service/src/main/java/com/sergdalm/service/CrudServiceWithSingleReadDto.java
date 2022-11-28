package com.sergdalm.service;

import java.util.Optional;

public interface CrudServiceWithSingleReadDto<ID, CREATE_DTO, READ_DTO> extends GeneralCrudService<ID, READ_DTO> {

    Optional<READ_DTO> findById(ID id);

    READ_DTO create(CREATE_DTO dto);

    Optional<READ_DTO> update(ID id, CREATE_DTO dto);
}
