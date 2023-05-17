package com.example.lms;

public class Module {
    String moduleName;
    String degreeProgramme;
    String moduleDescription;
    String moduleLocation;
    String contactNumber;

    public String getDegreeProgramme() {
        return degreeProgramme;
    }

    public void setDegreeProgramme(String degreeProgramme) {
        this.degreeProgramme = degreeProgramme;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String shopName) {
        this.moduleName = shopName;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String shopDescription) {
        this.moduleDescription = shopDescription;
    }

    public String getModuleLocation() {
        return moduleLocation;
    }

    public void setModuleLocation(String shopLocation) {
        this.moduleLocation = shopLocation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber= contactNumber;
    }
}
