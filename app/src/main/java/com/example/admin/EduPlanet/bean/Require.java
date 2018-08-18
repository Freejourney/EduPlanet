package com.example.admin.EduPlanet.bean;

public class Require {

    private String Institute;
    private String Type;
    private String Priority;

    public Require() {
        this.Institute = "计算机学院";
        this.Type = "课本教材";
        this.Priority = "最近发布";
    }

    public String getInstitute() {
        return Institute;
    }

    public void setInstitute(String institute) {
        Institute = institute;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }
}
