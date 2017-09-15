package com.mongodb.indexId;

import com.mongodb.model.Sequence;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


/**
 * Created by BFD-593 on 2017/9/15.
 * 获取下一个ID的值（实现ID自增）or 也可以继承AbstractMongoEventListener，使用注解形式，
 * 由于我没有测试成功，所以先放弃了
 */
public class MongodbId{
    public static long getNextId(MongoTemplate mongoTemplate,String collName){
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        Sequence seq = mongoTemplate.findAndModify(query, update, options, Sequence.class,"sequence");

        return seq.getSeqId();
    }
}
