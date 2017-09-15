package com.mongodb.dao;

import com.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/13.
 */
@Repository
public interface UserMongodbRespository extends MongoRepository<User,String>{
    /**
     * 方法名查询（spring data jpa）根据name
     * @param name
     * @return
     */
    User findByName(String name);

    /**
     * 方法名查询 根据age
     * @param age1
     * @param age2
     * @return
     */
    List<User> findByAgeGreaterThanEqualAndNameEquals(int age1, String name);
}
