package com.example.lms;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session_student";
    String FULLNAME = "";
    String DEGREE = "";
    String USERNAME = "";
    String PHONE = "";
    String EMAIL = "";
    String PASSWORD = "";
    int STUDENT_ID;

    String MODULE_NAME = "";
    String MODULE_DESCRIPTION = "";

    String LECTURE_NAME = "";
    String LECTURE_USERNAME = "";
    String LECTURE_EMAIL = "";
    String LECTURE_PHONE = "";
    String LECTURE_PASSWORD = "";
    String DEGREE_NAME = "";
    int LECTURE_ID;

    public void saveLecture(Lecturer lecture) {
        LECTURE_NAME = lecture.getFullname();
        LECTURE_USERNAME = lecture.getUsername();
        LECTURE_EMAIL = lecture.getEmail();
        LECTURE_PHONE = lecture.getPhone();
        LECTURE_PASSWORD = lecture.getPassword();
        DEGREE_NAME = lecture.getDegree();
        LECTURE_ID = lecture.getLecturer_id();

        editor.putString("lecture_name", LECTURE_NAME).commit();
        editor.putString("lecture_username", LECTURE_USERNAME).commit();
        editor.putString("lecture_email", LECTURE_EMAIL).commit();
        editor.putString("lecture_phone", LECTURE_PHONE).commit();
        editor.putString("lecture_password", LECTURE_PASSWORD).commit();
        editor.putString("degree_name", DEGREE_NAME).commit();
        editor.putInt("lecture_id", LECTURE_ID).commit();
    }

    public void saveModuleName(Module module) {
        MODULE_NAME = module.getModuleName();

        editor.putString("module_name", MODULE_NAME).commit();
    }

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(Student student) {
        USERNAME = student.getUsername();
        PHONE = student.getPhone();
        EMAIL = student.getEmail();
        PASSWORD = student.getPassword();
        FULLNAME = student.getFullname();
        DEGREE = student.getDegree();
        STUDENT_ID = student.getStudent_id();

        editor.putString("username", USERNAME).commit();
        editor.putString("phone", PHONE).commit();
        editor.putString("email", EMAIL).commit();
        editor.putString("password", PASSWORD).commit();
        editor.putString("fullname", FULLNAME).commit();
        editor.putString("degree", DEGREE).commit();
        editor.putInt("student_id", STUDENT_ID).commit();
    }

    public void setUSERNAME(String USERNAME) {
        editor.putString("username", USERNAME).commit();
    }

    public void setPHONE(String PHONE) {
        editor.putString("phone", PHONE).commit();
    }

    public void setEMAIL(String EMAIL) {
        editor.putString("email", EMAIL).commit();
    }

    public String getUsername() {
        return sharedPreferences.getString("username", null);
    }

    public String getPhone() {
        return sharedPreferences.getString("phone", null);
    }

    public String getEmail() {
        return sharedPreferences.getString("email", null);
    }

    public String getPassword() {
        return sharedPreferences.getString("password", null);
    }

    public void logout() {
        editor.clear().commit();
    }

    public int getStudentId() {
        return sharedPreferences.getInt("student_id", 0);
    }

    public int getLectureId() {
        return sharedPreferences.getInt("lecture_id", 0);
    }

    public String getFullName() {
        return sharedPreferences.getString("fullname", null);
    }

    public String getDegree() {
        return sharedPreferences.getString("degree", null);
    }

    public String getModuleName() {
        return sharedPreferences.getString("module_name", null);
    }

}
