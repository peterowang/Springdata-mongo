package com.mongodb.controller;

import com.mongodb.WriteResult;
import com.mongodb.model.User;
import com.mongodb.service.UserMongodbFindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by BFD-593 on 2017/9/13.
 */
@RestController
@RequestMapping("/user")
public class UserMongodbFindController {
    @Autowired
    private UserMongodbFindService UserMongodbFindService;

    @RequestMapping(value = "/user", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public User findUser(@RequestParam("name") String name) {
        return UserMongodbFindService.findUserByName(name);
    }

    @RequestMapping(value = "/allUser", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findAll() {
        return UserMongodbFindService.findAll();
    }

    @RequestMapping(value = "/save", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public void save(User user) {
        UserMongodbFindService.saveUser(user);
    }

    @RequestMapping(value = "/find", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public User find(@RequestParam("id") String id) {
        return UserMongodbFindService.findUser(id);
    }

    @RequestMapping(value = "/update", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public WriteResult update(User user) {
        WriteResult writeResult = UserMongodbFindService.updateUser(user);
        return writeResult;
    }

    @RequestMapping(value = "/delete", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> update(@RequestParam(value="name",required = false) String name) {
        return UserMongodbFindService.delete(name);
    }

    @RequestMapping(value = "/findMoreIf", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findMoreIf(User user) {
        return UserMongodbFindService.findMoreIf(user);
    }

    @RequestMapping(value = "/byAge", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findUserByAge(@RequestParam(value = "age1", required = false) int age1, @RequestParam(value = "name", required = false) String age2) {
        return UserMongodbFindService.findUserByAge(age1, age2);
    }

    @RequestMapping(value = "/bydb", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findUserByDB(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "age", required = false) int age) {
        return UserMongodbFindService.findByDB(name, age);
    }
    @RequestMapping(value = "/bydb1", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findUserByDB1(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "age", required = false) int age) {
        return UserMongodbFindService.findByDB1(name, age);
    }
    @RequestMapping(value = "/byqb", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findUserByQB(@RequestParam("name")String name) {
        return UserMongodbFindService.findByQb(name);
    }
    @RequestMapping(value = "/byqb1", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findUserByQB1(@RequestParam("name")String name) {
        return UserMongodbFindService.findByQb1(name);
    }
    @RequestMapping(value = "/byqb2", produces = {"application/json;charset=utf-8"}, method = RequestMethod.GET)
    public List<User> findUserByQB2() {
        return UserMongodbFindService.findByQb2();
    }
}