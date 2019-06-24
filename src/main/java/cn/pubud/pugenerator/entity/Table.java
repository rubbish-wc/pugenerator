package cn.pubud.pugenerator.entity;

import java.util.Date;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/24 17:31
 * @Version 1.0
 */
public class Table {

    private String tableName;

    private String engine;

    private Date createTime;

    private String tableCollation;

    private String  tableComment;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
}
