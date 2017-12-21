package com.poi.model;

import com.poi.annotation.ExcelResources;

/**
 * 测试模型
 */
public class User {
    private int id;
    private String username;
    private String nickname;
    private int age;

    /**
     * @ExcelResources(title="用户标识",order=1)
     * @ExcelResources是我们自定义的Annotation title 就是我们自定义Annotation中的String title()用于标识其名称
     * order=1 就是我们自定义Annotation中的 int order() 用于标识属于的次序 1就会在第一个
     **/
    @ExcelResources(title = "用户标识", order = 1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ExcelResources(title = "用户名称", order = 2)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ExcelResources(title = "用户昵称", order = 3)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @ExcelResources(title = "用户年龄", order = 4)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User() {
        // TODO Auto-generated constructor stub
    }

    public User(int id, String username, String nickname, int age) {
        super();
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", nickname="
                + nickname + ", age=" + age + "]";
    }
}
