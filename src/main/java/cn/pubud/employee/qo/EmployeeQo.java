package cn.pubud.employee.qo;

import cn.pubud.employee.entity.Employee;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zdy
 * @since 2019-06-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class EmployeeQo extends Employee implements Serializable {

    private static final long serialVersionUID = 1L;

}
