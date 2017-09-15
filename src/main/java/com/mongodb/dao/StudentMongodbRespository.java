package com.mongodb.dao;

import com.mongodb.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by BFD-593 on 2017/9/14.
 */
@Repository
public interface StudentMongodbRespository extends MongoRepository<Student,String>{
    Student findById(String id);
}
