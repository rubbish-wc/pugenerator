package cn.pubud.employee.service.impl;

import cn.pubud.employee.entity.Employee;
import cn.pubud.employee.mapper.EmployeeMapper;
import cn.pubud.employee.service.IEmployeeService;
import cn.pubud.pugenerator.commons.service.impl.ServiceImpl;
import cn.pubud.pugenerator.commons.entity.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import cn.pubud.employee.qo.EmployeeQo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zdy
 * @since 2019-06-22
 */
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

   @Override
   public Wrapper<Employee> conditionalConstructor(BaseEntity entityQo, Wrapper<Employee> wrapper) {
       if (wrapper instanceof QueryWrapper) {
          QueryWrapper<Employee> wrap = (QueryWrapper) wrapper;
          if (!BeanUtil.isEmpty(entityQo)) {
              EmployeeQo qo = new EmployeeQo();
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
          UpdateWrapper<Employee> wrap = (UpdateWrapper<Employee>) wrapper;
          return super.conditionalConstructor(entityQo, wrap);
       }
          return super.conditionalConstructor(entityQo, wrapper);
       }

}
