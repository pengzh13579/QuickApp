package cn.pzh.system.web.project.dao.first.entity.info;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.pzh.system.web.project.dao.CommonEntity;

import java.util.Date;
import java.math.BigDecimal;
@Data
@EqualsAndHashCode (callSuper = true)
public class InfoDishesEntity extends CommonEntity implements Serializable {
    private Integer id;
    private Integer dishesType;
    private String dishesName;
    private BigDecimal dishesPrice;
    private String dishesDiscount;
    private BigDecimal dishesPay;
    private String dishesImage;
    private Integer dishesTaste;
    private String dishesLevel;
    private Integer disFlag;
    private Date createDate;
    private Date updateDate;
    private String createUser;
    private String updateUser;
}