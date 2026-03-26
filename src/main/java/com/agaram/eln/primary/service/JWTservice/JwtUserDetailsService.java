package com.agaram.eln.primary.service.JWTservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agaram.eln.primary.model.jwt.UserDTO;
import com.agaram.eln.primary.model.usermanagement.LSMultisites;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.repository.usermanagement.LSMultisitesRepositery;
import com.agaram.eln.primary.repository.usermanagement.LSSiteMasterRepository;
import com.agaram.eln.primary.repository.usermanagement.LSuserMasterRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final LSuserMasterRepository userDao;
    private final PasswordEncoder bcryptEncoder;
    private final LSSiteMasterRepository lSSiteMasterRepository;
    private final LSMultisitesRepositery LSMultisitesRepositery;

    
    @Lazy
    public JwtUserDetailsService(LSuserMasterRepository userDao, PasswordEncoder bcryptEncoder,
                                 LSSiteMasterRepository lSSiteMasterRepository, 
                                 LSMultisitesRepositery LSMultisitesRepositery) {
        this.userDao = userDao;
        this.bcryptEncoder = bcryptEncoder;
        this.lSSiteMasterRepository = lSSiteMasterRepository;
        this.LSMultisitesRepositery = LSMultisitesRepositery;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int sitecodestartindex = username.lastIndexOf("[");
        String usernamevalue = username.substring(0, sitecodestartindex);
        String sitecodevalue = username.substring(sitecodestartindex + 1, username.length() - 1);

        LSSiteMaster objsite = lSSiteMasterRepository.findBysitecode(Integer.parseInt(sitecodevalue));
        
        LSuserMaster user = new LSuserMaster();
        List<LSMultisites> obj = LSMultisitesRepositery.findByLssiteMaster(objsite);
        List<Integer> usercode = obj.stream().map(LSMultisites::getUsercode).collect(Collectors.toList());
        List<LSuserMaster> userobj = userDao.findByUsernameIgnoreCaseAndUsercodeInAndUserretirestatusNot(usernamevalue, usercode, 1);
        
        if (!userobj.isEmpty()) {
            userobj.forEach(items -> {
                items.setLssitemaster(objsite);
            });
            user = userobj.get(0);
        }

        if (user==null || user.getUsercode() == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        if (user.getPassword() == null) {
            user.setPassword("admin");
        }

        String Tokenuser = user.getUsername() + "[" + user.getLssitemaster().getSitecode() + "]";
        return new org.springframework.security.core.userdetails.User(Tokenuser, user.getPassword(),
                new ArrayList<>());
    }

    public LSuserMaster save(UserDTO user) {
        LSuserMaster newUser = new LSuserMaster();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }
}

