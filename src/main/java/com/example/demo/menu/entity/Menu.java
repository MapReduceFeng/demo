package com.example.demo.menu.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 1185507250426827286L;

    @Id
    private Integer id;
    private String pid;
    private String url;
    private String path;
    private String name;
    private List<Menu> children;

    public Menu() {
    }

    public Menu(Integer id, String pid, String vuePath, String menuPath, String menuName, List<Menu> children) {
        this.id = id;
        this.pid = pid;
        this.url = vuePath;
        this.path = menuPath;
        this.name = menuName;
        this.children = children;
    }
}
