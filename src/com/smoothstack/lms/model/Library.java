package com.smoothstack.lms.model;

public class Library {
    private String branchId;
    private String branchName;
    private String branchAddress;

    public Library(String branchId, String branchName, String branchAddress) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.branchAddress = branchAddress;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }
}
