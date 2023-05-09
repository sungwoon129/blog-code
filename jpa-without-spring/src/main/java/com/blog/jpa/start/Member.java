package com.blog.jpa.start;


import javax.persistence.*;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    @Column(name = "AGE")
    private Integer age;


    public Member(String id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public Member() {

    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
