package cn.pzh.system.web.project.common.model;

import java.util.List;
import lombok.Data;

@Data
public class MenuNode<T> {
    private T parent;
    private List<T> children;
}
