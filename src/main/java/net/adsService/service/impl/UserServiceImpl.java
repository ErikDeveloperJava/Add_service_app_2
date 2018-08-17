package net.adsService.service.impl;

import net.adsService.form.UserRequestForm;
import net.adsService.model.User;
import net.adsService.model.UserRole;
import net.adsService.repository.UserRepository;
import net.adsService.service.UserService;
import net.adsService.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageUtil imageUtil;

    @Transactional(rollbackFor = Exception.class)
    public void save(UserRequestForm userForm) {
        User user = User.builder()
                .name(userForm.getName())
                .surname(userForm.getSurname())
                .username(userForm.getUsername())
                .password(passwordEncoder.encode(userForm.getPassword()))
                .image(" ")
                .telephone(userForm.getTelephone())
                .role(UserRole.USER)
                .city(userForm.getCity())
                .registerDate(new Date())
                .build();
        user = userRepository.save(user);
        String fileName = user.getUsername() + user.getId() + ".jpg";
        try {
            imageUtil.save(user.getUsername(),fileName,userForm.getImage());
            user.setImage(user.getUsername() + "/" + fileName);
        }catch (Exception e){
            imageUtil.deleteAll(user.getUsername());
            throw e;
        }
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        LOGGER.debug("user data updated");
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
        LOGGER.debug("user deleted");
    }
}
