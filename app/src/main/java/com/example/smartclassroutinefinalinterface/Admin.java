package com.example.smartclassroutinefinalinterface;

public class Admin {
    String name,email,phone,password,userName,dept,userType,presentAddress,permanentAddress,id;

    public Admin(String name,String userName,String id , String email, String phone, String password, String department,String userType,String presentAddress, String permanentAddress) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.userName = userName;
        this.dept = department;
        this.userType = userType;
        this.id = id;
        this.presentAddress = presentAddress;
        this.permanentAddress = permanentAddress;
    }

    public Admin (){}

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return dept;
    }

    public void setDepartment(String department) {
        this.dept = department;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
