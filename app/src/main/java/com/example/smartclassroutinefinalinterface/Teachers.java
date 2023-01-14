package com.example.smartclassroutinefinalinterface;

public class Teachers {
    String dept,name,id;
    String phone,email,password,userName,education,presentAddress,permanentAddress,roomNo,userType,designation;

    public Teachers(){

    }
    public Teachers(String dept, String name, String id) {
        this.dept = dept;
        this.name = name;
        this.id = id;
    }

    public Teachers(String dept,String designation, String name, String id, String phone, String email, String password, String userName, String education, String presentAddress,String permanentAddress, String roomNo, String userType) {
        this.dept = dept;
        this.designation = designation;
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.education = education;
        this.presentAddress = presentAddress;
        this.permanentAddress = permanentAddress;
        this.roomNo = roomNo;
        this.userType = userType;
    }

    public String getDept() {
        return dept;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String address) {
        this.presentAddress = address;
    }
    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String address) {
        this.permanentAddress = address;
    }


    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
