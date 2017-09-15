package com.mongodb.service;

import com.google.common.collect.Lists;
import com.mongodb.WriteResult;
import com.mongodb.indexId.MongodbId;
import com.mongodb.dao.UserMongodbRespository;
import com.mongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/15.
 */
@Service
public class UserInsertAndUpdateService {
    @Autowired
    private UserMongodbRespository userMongodbRespository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *批量插入User
     */
    public void insertAllUser(){
        List list = Lists.newArrayList();
        for(int i=0;i<5;i++){
            User user = new User();
            user.setId(MongodbId.getNextId(mongoTemplate,"user"));
            user.setAge(""+i);
            user.setName("helloworld"+i);
            user.setSex("不男不女");
            list.add(user);
        }
//        userMongodbRespository.insert(list);
        mongoTemplate.insertAll(list);
    }

    /**
     * 更新操作
     * 满足条件即更新
     * @return
     */
    public WriteResult update(){
        Query query = new Query(Criteria.where("sex").is("不男不女"));
        Update update = new Update().set("name", "刘浩源");
        WriteResult writeResult = mongoTemplate.updateMulti(query, update, User.class);
        return writeResult;
    }

    /**
     * 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值。
     * 如符合条件的就更新
     * @return
     */
    public WriteResult upsert(){
        WriteResult upsert = mongoTemplate.upsert(new Query(Criteria.where("id").is("59b8fde09c0fa0696ad62410")), new Update().set("sex", "男"), User.class);
        return upsert;
    }

    /**
     * findAndModify是原子操作
     * new FindAndModifyOptions().returnNew(true)是返回更新后的文档对象
     * @return
     */
    public User findAndModify(){
        User user = mongoTemplate.findAndModify(new Query(Criteria.where("id").is("59b8fde09c0fa0696ad624b9")),
                new Update().set("sex", "女"), new FindAndModifyOptions().returnNew(true), User.class);
        return user;
    }

    /**
     * 原子操作，
     * new FindAndModifyOptions().returnNew(true).upsert(true)
     * 先查询，如果没有符合条件的，会执行插入，插入的值是查询值 ＋ 更新值。
     * 如符合条件的就更新
     * 最后返回更新或插入的文档对象
     * @return
     */
    public User findAndModifyUpsert(){
        User user = mongoTemplate.findAndModify(new Query(Criteria.where("id").is("59b9fde09c0fa0696ad624b9")),
                new Update().set("sex", "男"), new FindAndModifyOptions().returnNew(true).upsert(true), User.class);
        return user;
    }
}
