package com.sergdalm.service;

import java.util.Optional;

// Вопрос: Денис, скажи пожалуйста, допустимо давать дженерикам такие длинные названия для ясности их назначения?
public interface CrudServiceWithDoubleReadDto<ID, CREATE_DTO, READ_SIMPLE_DTO, READ_EXTENDED_DTO> extends GeneralCrudService<ID, READ_SIMPLE_DTO> {

    Optional<READ_EXTENDED_DTO> findById(ID id);

    READ_EXTENDED_DTO create(CREATE_DTO dto);

    Optional<READ_EXTENDED_DTO> update(ID id, CREATE_DTO dto);

}
