package com.mongodb.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.service.UserMongodbAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/14.
 */
@Controller
@RequestMapping("/user")
public class UserMongodbAggregationController {
    @Autowired
    private UserMongodbAggregationService userMongodbAggregationService;

    @ResponseBody
    @RequestMapping(value = "/byagg1", produces = {"application/json;charset"}, method = RequestMethod.GET)
    public Iterable<DBObject> findByAgg1() {
        return userMongodbAggregationService.findByAgg1();
    }
    @ResponseBody
    @RequestMapping(value = "/byagg2", produces = {"application/json;charset"}, method = RequestMethod.GET)
    public Iterable<DBObject> findByAgg2() {
        return userMongodbAggregationService.findByAgg2();
    }
    @ResponseBody
    @RequestMapping(value = "/byagg3", produces = {"application/json;charset"}, method = RequestMethod.GET)
    public List<BasicDBObject> findByAgg3() {
        return userMongodbAggregationService.findByAgg3();
    }
}
