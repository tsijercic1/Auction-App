package com.github.tsijercic1.auctionapi.security;

import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /**
     *
     * @param usernameOrEmail
     * username or email of the user
     * @return
     * returns the details of the user such as name, surname, roles used for access control
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }

    /**
     * This method is used by JWTAuthenticationFilter
     * @param id
     * user id in the database
     * @return
     * returns the details of the user such as name, surname, roles used for access control
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}
