package com.wexinc.interview.challenge1.models;

public class ChangePassword {
    private String currentPassword;
    private String newPassword;
    private String verifyPassword;

    public ChangePassword(String currentPassword, String newPassword, String verifyPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.verifyPassword = verifyPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }
}
