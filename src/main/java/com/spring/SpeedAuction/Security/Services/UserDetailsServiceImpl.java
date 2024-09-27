package com.spring.SpeedAuction.Security.Services;

import com.spring.SpeedAuction.Models.UserModels;
import com.spring.SpeedAuction.Repository.UserInterfaces.FindUserFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final FindUserFilter findUserFilter;

    public UserDetailsServiceImpl(FindUserFilter findUserFilter) {
        this.findUserFilter = findUserFilter;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModels user = findUserFilter.findByUsername(username)
                .orElseThrow(()  -> new UsernameNotFoundException("User not found with username " + username));

        return UserDetailImpl.build(user);
    }
}
