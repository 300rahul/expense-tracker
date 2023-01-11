package tech.sara.expensetracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.sara.expensetracker.domain.enums.ErrorMessage;
import tech.sara.expensetracker.domain.requests.TotalMonthlyIncomeRequest;
import tech.sara.expensetracker.domain.responses.FailedResponse;
import tech.sara.expensetracker.jpa.entities.User;
import tech.sara.expensetracker.jpa.repository.UserRepo;

import java.util.Optional;


@Service
public class ExpenseService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserRepo userRepo;
    @Autowired
    ExpenseHelper expenseHelper;
    public ResponseEntity<?> updateMonthlyIncome(TotalMonthlyIncomeRequest totalMonthlyIncomeRequest) {
        FailedResponse failedResponse = new FailedResponse();
        try{
            Optional<User> optionalUser = userRepo.findById(Long.valueOf(totalMonthlyIncomeRequest.getUserId()));
            if(optionalUser == null || !optionalUser.isPresent()){
                failedResponse.setMessage(ErrorMessage.USER_DOES_NOT_EXIST.getMessage());
                failedResponse.setCode(ErrorMessage.USER_DOES_NOT_EXIST.getCode());
                return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
            }
            User user = optionalUser.get();
            Double amountRoundedUptoTwoDecimalPlace = expenseHelper.roundUptoTwoDecimalPlace(totalMonthlyIncomeRequest.getAmount());
            user.setMonthlyIncome(amountRoundedUptoTwoDecimalPlace);
            userRepo.save(user);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Failed to update monthly income for userId: {}", totalMonthlyIncomeRequest.getUserId(), e);
            failedResponse.setMessage(ErrorMessage.FAILED_TO_UPDATE_MONTHLY_INCOME.getMessage());
            failedResponse.setCode(ErrorMessage.FAILED_TO_UPDATE_MONTHLY_INCOME.getCode());
            return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
