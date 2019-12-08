package org.bfh.dms.service;

import org.bfh.dms.domain.User;
//import org.bfh.dms.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements UserDetailsService{

    /*@Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        User user = Optional.of(userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user = getUserByUsername(username);
        User user = loadMockUserbyUsername(username);
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(), user.isAdmin() ? authorityListAdmin : authorityListUser);
    }

    public User loadMockUserbyUsername(String username) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String password = bcrypt.encode("123");
        return new User("fca", password, true);
    }
}
