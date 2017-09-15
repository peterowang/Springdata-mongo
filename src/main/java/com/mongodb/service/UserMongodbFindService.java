package com.mongodb.service;

import com.google.common.collect.Lists;
import com.mongodb.*;
import com.mongodb.dao.UserMongodbRespository;
import com.mongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by BFD-593 on 2017/9/13.
 * mongodb简单查询
 */
@Service
public class UserMongodbFindService {
    @Autowired
    private UserMongodbRespository userMongodbRespository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 按自定义方法名查询(按name)
     * @param name
     * @return
     */
    public User findUserByName(String name) {
        return userMongodbRespository.findByName(name);
    }

    /**
     * 按自定义方法名查询(按age和name)
     * age>=#{age} and name=#{name}
     * @param age1
     * @param name
     * @return
     */
    public List<User> findUserByAge(int age1,String name){
        return userMongodbRespository.findByAgeGreaterThanEqualAndNameEquals(age1, name);
    }

    /**
     * 使用mongotemplate查询所有
     * @return
     */
    public List<User> findAll(){
        return userMongodbRespository.findAll();
    }

    /**
     * 使用mongotemplate保存对象
     * @param user
     */
    public void saveUser(User user){
        mongoTemplate.save(user);
    }

    /**
     * 使用mongotemplate查询指定id的User
     * @param id
     * @return
     */
    public User findUser(String id){
        Query query = new Query(Criteria.where("id").is(id));
        return  mongoTemplate.findOne(query, User.class);
    }

    /**
     * 使用mongotemplate更新指定的user
     * @param user
     */
    public WriteResult updateUser(User user){
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("name", user.getName()).set("age", user.getAge()).set("sex", user.getSex());
        return  mongoTemplate.updateMulti(query, update, User.class);
    }

    /**
     * 使用mongotemplate删除所有符合条件的并返回删除成功的
     * Pattern.CASE_INSENSITIVE是指不区分大小写
     * @param name
     * @return
     */
    public List<User> delete(String name){
        //完全匹配
        Pattern pattern1 = Pattern.compile("^" + name + "$",Pattern.CASE_INSENSITIVE);
        //右匹配
        Pattern pattern2 = Pattern.compile("^.*" + name + "$", Pattern.CASE_INSENSITIVE);
        //左匹配
        Pattern pattern3 = Pattern.compile("^" + name + ".*$", Pattern.CASE_INSENSITIVE);
        //模糊匹配
        Pattern pattern4 = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern4));
        //删除所有符合条件的并返回删除成功的
        List<User> allAndRemove = mongoTemplate.findAllAndRemove(query, User.class);
        //删除符合条件中的一个，并返回
        //mongoTemplate.findAndRemove(query, User.class);
        return allAndRemove;
    }

    /**
     * 使用mongotemplate多条件查询
     * @param user
     * @return
     */
    public List<User> findMoreIf(User user){
        //select * from user where name = #{name} and sex = #{sex}
        /*Query query = new Query(new Criteria().andOperator(Criteria.where("name").is(user.getName()),
                Criteria.where("sex").is(user.getSex())));*/

        //select * from user where name=#{name} and age = #{age} or sex=#{sex}
        Query query = new Query(new Criteria().orOperator(Criteria.where("name").is(user.getName()).and("age").is(user.getAge()),
                Criteria.where("sex").is(user.getSex())));
        //查询时指定它的集合名字
        return mongoTemplate.find(query, User.class, "user");
    }

    /**
     * 使用BasicDBObject查询
     * name=#{name} and age=#{age}
     * @param name
     * @return
     */
    public List<User> findByDB(String name, int age) {
        BasicDBObject obj = new BasicDBObject();
        obj.put("name", name);
        obj.put("age", age);
        Query query = new BasicQuery(obj);
        return mongoTemplate.find(query, User.class, "user");
    }

    /**
     * 使用BasicDBObject查询
     * name=#{name} and age=#{age} or age=#{age}
     * BasicQuery查询语句可以指定返回字段，构造函数
     * BasicQuery(DBObject queryObject, DBObject fieldsObject)
     * fieldsObject 这个字段可以指定返回字段
     * fieldsObject.put(key,value)
     * key：字段
     * value：
     * 说明：
     * 1或者true表示返回字段
     * 0或者false表示不返回该字段
     * _id:默认就是1，没指定返回该字段时，默认会返回，除非设置为0是，就不会返回该字段。
     * 指定返回字段，有时文档字段多并数据大时，我们指定返回我们需要的字段，这样既节省传输数据量，
     * 减少了内存消耗，提高了性能，在数据大时，性能很明显的。
     * @param name
     * @param age
     * @return
     */
    public List<User> findByDB1(String name,int age){
        BasicDBList list = new BasicDBList();
        BasicDBObject bd = new BasicDBObject();
        bd.put("name", name);
        bd.put("age", age);
        list.add(bd);
        list.add(new BasicDBObject("age", age));
        BasicDBObject obj = new BasicDBObject();
        obj.put("$or", list);
        BasicDBObject fields = new BasicDBObject();
        fields.put("id", 0);//不显示id字段
        fields.put("name",1);//只显示name字段
        Query query = new BasicQuery(obj, fields);
        return mongoTemplate.find(query, User.class, "user");
    }

    /**
     * 使用text文件索引查询，要求查询的字段必须是text索引
     * db.user.find({$text:{$search:"wang"}})
     * @return
     */
    public List<User> findByQb(String name){
        QueryBuilder qb = new QueryBuilder();
        qb.text(name);
        Query query = new BasicQuery(qb.get());
        return mongoTemplate.find(query, User.class);
    }

    /**
     * 利用QueryBuilder进行查询
     * name = #{name} and age in #{age}
     * @param name
     * @return
     */
    public List<User> findByQb1(String name){
        QueryBuilder qb = new QueryBuilder();
        List list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        qb.put("name").is(name).and("age").in(list);
        Query query = new BasicQuery(qb.get());
        return mongoTemplate.find(query, User.class, "user");
    }
    public List<User> findByQb2(){
        QueryBuilder qb = new QueryBuilder();
        qb.and(new BasicDBObject("name", "wang"), new BasicDBObject("age", 2));
        BasicDBObject qdb = new BasicDBObject();
        qdb.put("name", 1);
        Query qr = new BasicQuery(qb.get(), qdb);//只显示id和name
        return mongoTemplate.find(qr, User.class);
    }
}
