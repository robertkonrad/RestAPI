package com.roberkonrad.restapi.service;

import com.roberkonrad.restapi.model.User;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public User getUser() {
        Yaml yaml = new Yaml(new Constructor(User.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("user.yaml");
        User user = yaml.load(inputStream);
        return user;
    }
}
