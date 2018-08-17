package net.adsService.service;

import net.adsService.form.UserRequestForm;
import net.adsService.model.User;

public interface UserService {

    void save(UserRequestForm userForm);

    boolean existsByUsername(String username);

    void update(User user);

    void deleteById(int id);
}
