package com.kapango.application.usecase;

import java.util.List;
import java.util.Optional;

/**
 * Defines a base level of functionality for Finding, Creating, Updating, Deleting entities within the domain
 * @param <M> Model class (this should not be a DTO or Entity, otherwise it violates the architecture tests)
 */
public interface CrudUseCase<M>{

    List<M> findAll();

    Optional<M> findById(Integer id);

    M upsert(M model);

    void delete(M model);

}
