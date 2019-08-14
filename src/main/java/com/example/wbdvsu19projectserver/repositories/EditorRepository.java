package com.example.wbdvsu19projectserver.repositories;

import com.example.wbdvsu19projectserver.models.Editor;
import com.example.wbdvsu19projectserver.models.Person;
import com.example.wbdvsu19projectserver.models.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Xuan Kong
 * @Date 2019-07-27.
 */
@Repository
public interface EditorRepository extends CrudRepository<Editor,Integer> {

}

