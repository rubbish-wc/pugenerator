package cn.pubud.pugenerator.commons.entity;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基础父类
 * </p>
 *
 * @author
 * @since
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
  private Long id;

}
