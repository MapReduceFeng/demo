package com.example.demo.menu.entity;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeNew {

    public List<Menu> menuCommon;
    public List<Menu> list = new ArrayList<>();

    public List<Menu> menuList(List<Menu> menu) {
        this.menuCommon = menu;
        for (Menu x : menu) {
            if (1 == x.getId()) {
                Menu treeNew = new Menu(x.getId(), x.getPid(), x.getUrl(), x.getPath(), x.getName(), menuChild(x.getId()));
                list.add(treeNew);
            }
        }
        return list;
    }

    private List<Menu> menuChild(int id) {
        List<Menu> lists = new ArrayList<>();
        for (Menu a : menuCommon) {
            if (a.getPid().equals(id + "")) {
                Menu treeNew = new Menu(a.getId(), a.getPid(), a.getUrl(), a.getPath(), a.getName(), menuChild(a.getId()));
                lists.add(treeNew);
            }
        }
        return lists;
    }

}
