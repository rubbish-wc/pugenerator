package cn.pubud.pugenerator.entity;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/24 15:21
 * @Version 1.0
 */
public class Generator {


    @NotEmpty
    private String codePath;

    @NotEmpty
    private String codePackage;

    @NotEmpty
    private String tableName;

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public String getCodePackage() {
        return codePackage;
    }

    public void setCodePackage(String codePackage) {
        this.codePackage = codePackage;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
