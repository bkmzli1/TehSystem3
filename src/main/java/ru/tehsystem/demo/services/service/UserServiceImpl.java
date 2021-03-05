package ru.tehsystem.demo.services.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tehsystem.demo.domain.Img;
import ru.tehsystem.demo.domain.Roles;
import ru.tehsystem.demo.domain.User;
import ru.tehsystem.demo.model.RoleServiceModel;
import ru.tehsystem.demo.model.UserRegisterBindingModel;
import ru.tehsystem.demo.repo.ImgRepo;
import ru.tehsystem.demo.repo.UserRepo;
import ru.tehsystem.demo.services.impl.RoleService;
import ru.tehsystem.demo.services.impl.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final ImgRepo imgRepository;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper, RoleService roleService, ImgRepo imgRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.imgRepository = imgRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong");
        }

        return user;
    }

    @Override
    public void create(UserRegisterBindingModel userServiceModel) {
        User userEntity = this.modelMapper.map(userServiceModel, User.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setAccountNonExpired(true);
        userEntity.setAccountNonLocked(true);
        userEntity.setCredentialsNonExpired(true);
        userEntity.setEnabled(true);
        userEntity.setPs(userEntity.getPassword());
        if (userServiceModel.getImg() == null) {
            Img img = new Img();
            img.setImg("static/user.png");
            img.setName("userImg");
            userEntity.setImg(img);

            imgRepository.save(img);

        }
        if (userServiceModel.getImg() == null) {
            Img imgFon = new Img();
            imgFon.setImg("static/vk.png");
            imgFon.setName("fon");
            userEntity.setImgFon(imgFon);
            imgRepository.save(imgFon);
        }

        Set<Roles> authorities = new HashSet<>();
        RoleServiceModel roleServiceModel = null;

        roleServiceModel = this.roleService.findByAuthority("USER");
        Roles roles = this.modelMapper.map(roleServiceModel, Roles.class);

        authorities.add(roles);

        if (userServiceModel.isAdmin()) {
            roleServiceModel = this.roleService.findByAuthority("ADMIN");
            Roles rolesAdmin = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesAdmin);
        }
        if (userServiceModel.isExecutor()) {
            roleServiceModel = this.roleService.findByAuthority("EXECUTOR");
            Roles rolesExecutor = this.modelMapper.map(roleServiceModel, Roles.class);

            authorities.add(rolesExecutor);
        }


        userEntity.setAuthorities(authorities);
        this.userRepository.save(userEntity);
    }

    @Override
    public void edit(User userServiceModele) {

    }

    @Override
    public boolean isUsernameTaken(String username) {
        return this.userRepository.findOneByUsername(username) != null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        return this.userRepository.findByEmail(email) != null;
    }

    @Override
    public UserRegisterBindingModel findByUsername(String username) {
        User userEntity = this.userRepository.findOneByUsername(username);
        UserRegisterBindingModel userModel = null;
        if (userEntity != null) {
            userModel = this.modelMapper.map(userEntity, UserRegisterBindingModel.class);
        }
        return userModel;
    }

    @Override
    public User findById(String id) {
        User userEntity = this.userRepository.findOneById(id);
        return userEntity;
    }


}