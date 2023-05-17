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
