package cn.pubud.pugenerator.commons.service;


import cn.pubud.pugenerator.commons.entity.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IService
 * @Description: mybatis 基础服务
 **/
public interface  IService<T> {

    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapper<T> getBaseMapper();

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    boolean save(T entity);

    /**
     * 插入一条记录（选择字段，策略插入）返回对象
     *
     * @param entity 实体对象
     */
    T saveBack(T entity);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatch(Collection<T> entityList) {
        return saveBatch(entityList, 1000);
    }
    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    boolean saveBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    boolean removeById(Serializable id);

    /**
     * 根据 entity 条件，删除记录
     *
     * @param entity 表字段 entity 对象
     */
    boolean removeByEntity(T entity);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    boolean removeByIds(Collection<? extends Serializable> idList);
    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    boolean updateById(T entity);

    boolean update(T entity, BaseEntity qo);


    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     */

    boolean saveOrUpdate(T entity);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    T getById(Serializable id);



    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param qo 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default T getOne(BaseEntity qo) {
        return getOne(qo, true);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param qo 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    T getOne(BaseEntity qo, boolean throwEx);


    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    Collection<T> listByMap(Map<String, Object> columnMap);


    /**
     * 查询总记录数
     *
     * @see Wrappers#emptyWrapper()
     */
    default int count() {
        return count(null);
    }

    /**
     * 根据 queryEntity 条件，查询总记录数
     *
     * @param qo 实体对象封装操作类
     */
    int count(BaseEntity qo);

    /**
     * 查询列表
     *
     * @param qo 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    List<T> list(BaseEntity qo);

    /**
     * 查询所有
     *
     * @see Wrappers#emptyWrapper()
     */
    default List<T> list() {
        return list(new BaseEntity());
    }


    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param qo
     */
    IPage<T> page(IPage<T> page, BaseEntity qo);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default IPage<T> page(IPage<T> page) {
        return page(page,null);
    }

    public Wrapper<T> conditionalConstructor(BaseEntity qo, Wrapper<T> wrapper) ;

}
