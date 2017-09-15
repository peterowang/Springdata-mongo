package com.mongodb.service;

import com.mongodb.BasicDBObject;
import com.mongodb.dao.StudentMongodbRespository;
import com.mongodb.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/14.
 * $unwind - 可以将一个包含数组的文档切分成多个,
 * 比如你的文档有 中有个数组字段 A, A中有10个元素, 那么
 * 经过 $unwind处理后会产生10个文档，这些文档只有 字段 A不同
 *
 * 统计每个省各科平均成绩(各科成绩是一个集合[])
 * 主要处理步骤如下：
 * 1. 先用$unwind 拆数组
 * 2. 按照 province, subject 分租并求各科目平均分
 */
@Service
public class StudentMongodbAggregationService {
    @Autowired
    private StudentMongodbRespository studentMongodbRespository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Student findById(String id){
        return studentMongodbRespository.findById(id);
    }
    //统计每个省各科平均成绩(各科成绩是一个集合[])
    public List<BasicDBObject> findByAgg(){
        TypedAggregation<Student> agg = Aggregation.newAggregation(
                Student.class,
                Aggregation.project("province","subjects"),
                Aggregation.unwind("subjects"),
                Aggregation.group("province","subjects.name").avg("subjects.score").as("avgScore")
        );
        AggregationResults<BasicDBObject> aggregate = mongoTemplate.aggregate(agg, BasicDBObject.class);
        return aggregate.getMappedResults();
    }

}
