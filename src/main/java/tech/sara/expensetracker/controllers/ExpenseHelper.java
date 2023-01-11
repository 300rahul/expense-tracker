package tech.sara.expensetracker.controllers;

import org.springframework.stereotype.Controller;

import java.text.DecimalFormat;

@Controller
public class ExpenseHelper {
    public Double roundUptoTwoDecimalPlace(Double number){
        String result = new DecimalFormat("0.00").format(number);
        return Double.parseDouble(result);
    }
}
