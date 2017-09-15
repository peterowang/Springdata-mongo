package com.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by BFD-593 on 2017/9/15.
 */
@Document(collection = "sequence")
public class Sequence {
    @Id
    private String id;//objectId
    @Field("seqId")
    private Long seqId;//每次获取递增后的id
    @Field("collName")
    private String collName;//集合名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getCollName() {
        return collName;
    }

    public void setCollName(String collName) {
        this.collName = collName;
    }
}
