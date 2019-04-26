package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.util.Date;
import java.math.BigDecimal;
@Data
@EqualsAndHashCode (callSuper = true)
public class InfoPayEntity extends CommonEntity implements Serializable {
    private Integer id;
    private Integer payState;
    private Integer payType;
    private BigDecimal payAmount;
    private String tableNumber;
    private Date createDate;
}