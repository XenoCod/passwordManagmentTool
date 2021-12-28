package com.epam.passwordmanagementrest.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccountDTO {

    @Pattern(regexp = "(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper,numeric}!")
    private String userName;

    @Pattern(regexp = "^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper,lower,numeric}!")
    private String password;

    @Pattern(regexp = "((([A-Za-z]{3,9}:(?:\\/\\/)?)" +
            "(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]" +
            "+|(?:www.|[-;:&=\\+\\$,\\w]+@)" +
            "[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)" +
            "?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)", message = "Please enter valid url-{http://www.epam.com}!")
    private String url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Size(min = 5, max = 100, message = "Size:{5, 20} required!")
    private String message;

    @Pattern(regexp = "(?=.*[A-Z])(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper}!")
    private String groupName;

    public AccountDTO() {

    }

    public AccountDTO(String userName, String password, String url, String message) {
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", message='" + message + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
