package tech.sara.expensetracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.sara.expensetracker.domain.requests.SignUpRequest;

@RestController
@RequestMapping("expense/tracker/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    UserService userHelper;
//    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> signUp(@Validated SignUpRequest signUpRequest){
//        logger.info("sign up request: {}", signUpRequest);
//        return userHelper.signUp(signUpRequest);
//
//    }
}
