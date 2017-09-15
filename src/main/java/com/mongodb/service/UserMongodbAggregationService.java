package com.mongodb.service;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.dao.UserMongodbRespository;
import com.mongodb.model.User;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/14.
 * mongodb骤合查询
 * https://docs.mongodb.com/manual/reference/sql-aggregation-comparison/官方文档 查看sql对应的mongodb语句
 *
 */
@Service
public class UserMongodbAggregationService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserMongodbRespository userMongodbRespository;

    /**
     * 使用Mongo本身提供的AggregationOutput进行分组查询 骤合操作
     * select name,sum(age) as total from user group by name having total>=3 order by total desc
     * @return
     */
    public Iterable<DBObject> findByAgg1(){
        String groupStr = "{$group:{_id:'$name',total:{$sum:'$age'}}}";
        DBObject group = (DBObject) JSON.parse(groupStr);
        String mathStr = "{$match:{total:{$gte:3}}}";
        DBObject math = (DBObject) JSON.parse(mathStr);
        String sortStr = "{$sort:{total:-1}}";
        DBObject sort = (DBObject) JSON.parse(sortStr);
        //https://docs.mongodb.com/manual/reference/sql-aggregation-comparison/
        //math放group后面相当于having
        AggregationOutput out = mongoTemplate.getCollection("user").aggregate(group,math,sort);
        return out.results();
    }
    /**
     * 使用Mongo本身提供的AggregationOutput进行分组查询 骤合操作
     * select name,sex,sum(age) as total from user where age>=3 group by name,sex order by total desc
     * @return
     */
    public Iterable<DBObject> findByAgg2(){
        String groupStr = "{$group:{_id:{name:'$name',sex:'$sex'},total:{$sum:'$age'}}}";
        DBObject group = (DBObject) JSON.parse(groupStr);
        String mathStr = "{$match:{age:{$gte:3}}}";
        DBObject math = (DBObject) JSON.parse(mathStr);
        String sortStr = "{$sort:{total:-1}}";
        DBObject sort = (DBObject) JSON.parse(sortStr);
        AggregationOutput out = mongoTemplate.getCollection("user").aggregate(math, group,sort);
        return out.results();
    }

    /**
     * 使用spring data mongodb提供的聚合来查询
     * select name as NAME,sex as SEX,sum(age) as total from user group by name,sex having total >=3 order by total desc limit 0,10;
     * @return
     */
    public List<BasicDBObject> findByAgg3(){
        TypedAggregation<User> agg = Aggregation.newAggregation(
                User.class,
                Aggregation.project("name", "sex", "age"),//挑选本次操作所需的字段
                Aggregation.group("name", "sex").sum("age").as("total").
                        last("name").as("NAME").
                        last("sex").as("AGE"),//对name,sex分组后起别名,并计算age的合别名为total
                Aggregation.match(Criteria.where("total").gte(3)),//match放group前是where，放group后是having,当having total>=3时
                Aggregation.skip(0),
                Aggregation.limit(10),
                Aggregation.sort(Sort.Direction.DESC, "total")
        );
        AggregationResults<BasicDBObject> aggregate = mongoTemplate.aggregate(agg, BasicDBObject.class);
        List<BasicDBObject> mappedResults = aggregate.getMappedResults();
        return mappedResults;
    }

}
