package cn.pubud.pugenerator.commons.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.pubud.pugenerator.commons.entity.BaseEntity;
import cn.pubud.pugenerator.commons.service.IService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName: ServiceImpl
 * @Description: mybatis查询
 * @Author:
 **/
public class ServiceImpl<M extends BaseMapper<T>, T> implements IService<T> {
    protected Log log = LogFactory.getLog(getClass());

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }


    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    public boolean save(T entity) {
        return retBool(baseMapper.insert(entity));
    }
    @Override
    public T saveBack(T entity) {
        if(save(entity)) return entity;
        return null;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        return SqlHelper.delBool(baseMapper.deleteById(id));
    }

    @Override
    public boolean removeByEntity(T entity) {

        Assert.notEmpty( BeanUtil.beanToMap(entity), "error: columnMap must not be empty");
        return SqlHelper.delBool(baseMapper.deleteByMap( BeanUtil.beanToMap(entity)));    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return SqlHelper.delBool(baseMapper.deleteBatchIds(idList));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(T entity) {
        return retBool(baseMapper.updateById(entity));
    }


    @Override
    public boolean update(T entity, BaseEntity qo) {
/*
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<T>();
        updateWrapper.apply(wrapper.getSqlSegment(),wrapper.getValuePairs());*/

        return retBool(baseMapper.update(entity, conditionalConstructor(qo,new UpdateWrapper<T>())));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
            return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
        }
        return false;    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectById(id);
    }


    @Override
    public T getOne(BaseEntity qo, boolean throwEx) {
     /*   QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        queryWrapper.apply(wrapper.getSqlSegment(),wrapper.getValuePairs());*/
        if (throwEx) {
            return baseMapper.selectOne(conditionalConstructor(qo,new QueryWrapper<T>()));
        }
        return SqlHelper.getObject(baseMapper.selectList(conditionalConstructor(qo,new QueryWrapper<T>())));
    }

    @Override
    public Collection<T> listByMap(Map<String, Object> columnMap) {
        return baseMapper.selectByMap(columnMap);
    }

    @Override
    public int count(BaseEntity qo) {
     /*   QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        queryWrapper.apply(wrapper.getSqlSegment(),wrapper.getValuePairs());*/
        return SqlHelper.retCount(baseMapper.selectCount(conditionalConstructor(qo,new QueryWrapper<T>())));
    }

    @Override
    public List<T> list(BaseEntity qo) {
/*        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        queryWrapper.apply(wrapper.getSqlSegment(),wrapper.getValuePairs());*/
        return baseMapper.selectList(conditionalConstructor(qo,new QueryWrapper<T>()));
    }
    @Override
    public IPage<T> page(IPage<T> page, BaseEntity qo) {
      /*  QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        queryWrapper.apply(wrapper.getSqlSegment(),wrapper.getValuePairs());*/
        return baseMapper.selectPage(page, conditionalConstructor(qo,new QueryWrapper<T>()));
    }

    @Override
    public Wrapper<T> conditionalConstructor(BaseEntity qo, Wrapper<T> wrapper) {
        return wrapper;
    }

}
