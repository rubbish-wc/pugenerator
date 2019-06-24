package cn.pubud.pugenerator.service;

import cn.pubud.pugenerator.entity.Generator;
import cn.pubud.pugenerator.entity.Table;

import java.util.List;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/24 15:30
 * @Version 1.0
 */
public interface IGeneratorService {


    /**
     * 自动生成代码业务
     * @throws Exception
     * @param generator
     */
    void generate(Generator generator) throws Exception;

    /**
     * 根据表名模糊查询表信息
     */
    List<Table> getTables(String tableName);

}
