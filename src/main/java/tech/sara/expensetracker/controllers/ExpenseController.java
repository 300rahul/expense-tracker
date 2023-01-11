package tech.sara.expensetracker.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.sara.expensetracker.domain.requests.TotalMonthlyIncomeRequest;
import org.slf4j.Logger;

@RestController
@RequestMapping("expense/tracker")
public class ExpenseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ExpenseService expenseService;
    @PutMapping(value = "/update/monthly/income", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMonthlyIncome(@RequestBody TotalMonthlyIncomeRequest totalMonthlyIncomeRequest){

        logger.info("Add monthly income request: {}", totalMonthlyIncomeRequest);
        return expenseService.updateMonthlyIncome(totalMonthlyIncomeRequest);
    }

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(){
        return "hello";
    }
}
