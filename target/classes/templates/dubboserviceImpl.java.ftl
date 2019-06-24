package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import cn.pubud.pugenerator.commons.entity.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ${cfg.package_qo}.${entity}Qo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

   @Override
   public Wrapper<${entity}> conditionalConstructor(BaseEntity entityQo, Wrapper<${entity}> wrapper) {
       if (wrapper instanceof QueryWrapper) {
          QueryWrapper<${entity}> wrap = (QueryWrapper) wrapper;
          if (!BeanUtil.isEmpty(entityQo)) {
              ${entity}Qo qo = new ${entity}Qo();
              BeanUtil.copyProperties(entityQo,qo);
              /*
              if (ObjectUtil.isNotNull(qo.getId())){
                   wrap.eq("id", qo.getId());
              }
              if (ObjectUtil.isNotNull(qo.ascs())){
                   wrap.orderByAsc(qo.getAscs());
              }
              */
           }
          return super.conditionalConstructor(entityQo, wrap);
       }
       if (wrapper instanceof UpdateWrapper) {
          UpdateWrapper<${entity}> wrap = (UpdateWrapper<${entity}>) wrapper;
          return super.conditionalConstructor(entityQo, wrap);
       }
          return super.conditionalConstructor(entityQo, wrapper);
       }

}
</#if>
