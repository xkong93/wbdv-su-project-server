package com.example.wbdvsu19projectserver.repositories;

import com.example.wbdvsu19projectserver.models.Editor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
@Repository
public interface EditorRepository extends CrudRepository<Editor,Integer> {
}
