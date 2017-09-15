package com.mongodb.controller;

import com.mongodb.WriteResult;
import com.mongodb.model.User;
import com.mongodb.service.UserInsertAndUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by BFD-593 on 2017/9/15.
 */
@RestController
@RequestMapping("/user")
public class UesrMongodbInsertAndUpdateController {
    @Autowired
    UserInsertAndUpdateService userInsertAndUpdateService;

    @RequestMapping(value = "/insertAll", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public void insertAll() {
        userInsertAndUpdateService.insertAllUser();
    }
    @RequestMapping(value = "/update1", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public WriteResult update() {
        return userInsertAndUpdateService.update();
    }
    @RequestMapping(value = "/upsert", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public WriteResult upsert() {
        return userInsertAndUpdateService.upsert();
    }
    @RequestMapping(value = "/modify", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public User modify() {
        return userInsertAndUpdateService.findAndModify();
    }
    @RequestMapping(value = "/modifyUpsert", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public User modifyUpsert() {
        return userInsertAndUpdateService.findAndModifyUpsert();
    }
}
