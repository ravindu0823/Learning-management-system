package com.example.lms;

public class Student {
    private String username;
    private String email;
    private String password;
    private String phone;
    private String fullname;
    private String degree;
    private int student_id;

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Student(String username, String email, String password, String phone, String fullname, String degree, int student_id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.fullname = fullname;
        this.degree = degree;
        this.student_id = student_id;
    }

    public Student() {
    }
}
