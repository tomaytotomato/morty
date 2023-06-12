package com.kapango.application.view.admin;

import java.util.List;

public interface AdminCrudView <D> {

    List<D> findAll();

    D upsert(D dto);

    void delete(D dto);



}
