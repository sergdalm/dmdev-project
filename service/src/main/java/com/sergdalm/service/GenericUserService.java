package com.sergdalm.service;


import java.util.Optional;

public interface GenericUserService<ID, C, R, W> extends GenericService<ID, C, R> {

    Optional<W> findByIdWithUserInfo(ID id);

    W createWithUserInfo(C dto);

    Optional<W> updateWithUserInfo(ID id, C dto);
}
