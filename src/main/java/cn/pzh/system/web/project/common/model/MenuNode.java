package cn.pzh.system.web.project.common.model;

import java.util.List;
import lombok.Data;

@Data
public class MenuNode<T> {
    private T parent;
    private String isOpen;
    private String code;
    private String name;
    private List<T> children;
}
