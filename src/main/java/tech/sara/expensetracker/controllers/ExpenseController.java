package tech.sara.expensetracker.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tech.sara.expensetracker.domain.enums.ErrorMessage;
import tech.sara.expensetracker.domain.requests.*;
import org.slf4j.Logger;
import tech.sara.expensetracker.domain.responses.FailedResponse;

@RestController
@RequestMapping("expense/tracker")
public class ExpenseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ExpenseService expenseService;
    @PutMapping(value = "/update/monthly/income", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMonthlyIncome(@RequestParam Double amount){

        logger.info("Add monthly income request");
        //SecurityContextHolder was setted up in JwtRequestFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FailedResponse failedResponse = expenseService.updateMonthlyIncome(amount, userDetails.getUsername());
        if(failedResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);
        else return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "add/daily/expense", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addDailyExpense(@RequestBody AddDailyExpenseRequest addDailyExpenseRequest){
        logger.info("Add daily expense request: {}", addDailyExpenseRequest);
        //SecurityContextHolder was setted up in JwtRequestFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FailedResponse failedResponse = expenseService.addDailyExpense(addDailyExpenseRequest, userDetails.getUsername());
        if(failedResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);
        else if(failedResponse.getMessage().equals(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage())){
            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "add/monthly/expense", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMonthlyExpense(@RequestBody MonthlyExpenseRequest monthlyExpenseRequest){
        logger.info("Monthly expense request: {}", monthlyExpenseRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FailedResponse failedResponse =  expenseService.addMonthlyExpense(monthlyExpenseRequest, userDetails.getUsername());
        if(failedResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);
        else if(failedResponse.getMessage().equals(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage())){
            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @PostMapping(value = "update/monthly/expense", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> updateMonthlyExpense(@RequestBody MonthlyExpenseRequest monthlyExpenseRequest, @RequestParam(required = true) Long expenseId){
//        logger.info("Monthly expense update request: {}", monthlyExpenseRequest);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        FailedResponse failedResponse =  expenseService.updateMonthlyExpense(monthlyExpenseRequest, userDetails.getUsername(), expenseId);
//        if(failedResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);
//        else if(failedResponse.getMessage().equals(ErrorMessage.INVALID_USER.getMessage())){
//            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @DeleteMapping(value = "delete/expense", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteExpense(@RequestParam(required = true) Long expenseId){
        logger.info("Delete daily expense id: {}", expenseId);
        //SecurityContextHolder was setted up in JwtRequestFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FailedResponse failedResponse = expenseService.deleteExpense(expenseId, userDetails);
        if(failedResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);
        else if(failedResponse.getMessage().equals(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage())){
            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "add/expense/limit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addExpenseLimit(@RequestBody AddExpenseLimitForCategoryRequest addExpenseLimitRequest){
        logger.info("Add expense limit request: {}", addExpenseLimitRequest);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FailedResponse failedResponse =  expenseService.addExpenseLimit(addExpenseLimitRequest, userDetails.getUsername());
        if(failedResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);
        else if(failedResponse.getMessage().equals(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage())){
            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "delete/expense/limit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteExpenseLimit(@RequestParam Long expenseLimitId){
        logger.info("Delete expense limit request");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FailedResponse failedResponse =  expenseService.deleteExpenseLimit(expenseLimitId, userDetails.getUsername());
        if(failedResponse == null) return new ResponseEntity<>(null, HttpStatus.OK);
        else if(failedResponse.getMessage().equals(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage())){
            return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(failedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
