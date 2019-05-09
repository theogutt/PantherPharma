package dal.DTO.MaybeUseless;

import java.util.List;

public interface IUser {
    int getUserId();

    void setUserId(int userId);

    String getUserName();

    void setUserName(String userName);

    List<String> getRoles();

    void setRoles(List<String> roles);

    void addRole(String role);

    boolean removeRole(String role);
}