package cn.pubud.pugenerator.mapper;

import cn.pubud.pugenerator.entity.Table;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/24 17:43
 * @Version 1.0
 */
@Mapper
@Component
public interface GeneratorMapper {

    /**
     * 根据表名模糊查询表信息
     * @param param
     * @return
     */
    List<Table> selectTableInfo(String param);
}
