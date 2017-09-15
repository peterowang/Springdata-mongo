package com.mongodb.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.model.Student;
import com.mongodb.service.StudentMongodbAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/14.
 */
@RestController
@RequestMapping("/student")
public class StudentMongodbAggregationController {
    @Autowired
    private StudentMongodbAggregationService studentMongodbAggregationService;
    @RequestMapping(value="byid")
    public Student findById(String id){
        return studentMongodbAggregationService.findById(id);
    }
    @RequestMapping(value="byagg")
    public List<BasicDBObject> findById(){
        return studentMongodbAggregationService.findByAgg();
    }
}
