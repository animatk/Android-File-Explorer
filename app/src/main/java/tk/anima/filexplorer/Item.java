package tk.anima.filexplorer;

import java.io.File;

/**
 * Created by animatk on 4/14/17.
 */

public class Item {

    private int id;
    private String name;
    private String desc;
    private String path;
    private String size;
    private String type;

    public Item(int id, String name, String desc, String path, String size, String type) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.path = path;
        this.size = size;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
