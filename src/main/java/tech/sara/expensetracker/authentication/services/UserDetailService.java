package tech.sara.expensetracker.authentication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.sara.expensetracker.jpa.entities.User;
import tech.sara.expensetracker.jpa.repository.UserRepo;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByPhoneNumber(username);
        if(user == null || !user.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getPhoneNumber(), user.get().getPassword(), new ArrayList<>());
    }
}
