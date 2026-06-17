package com.example.auth_app_backend.credit.service;

import com.example.auth_app_backend.credit.entity.UserCredit;
import com.example.auth_app_backend.credit.repository.CreditRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditService {

    @Autowired
    private CreditRepo creditRepo;

    private static final long CHAR_PER_CREDIT = 500;

    public long calculateCredits(String input , String output) {
        int totalChars = input.length() + output.length();
        return (long) Math.ceil((double) totalChars / CHAR_PER_CREDIT);
    }

    public void checkEstimateCredit(String userId , String input) {
        UserCredit userCredit = creditRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("User Credit not found"));
        long availableCredits = userCredit.getTotalCredits() - userCredit.getUsedCredits();
        long creditsToDeduct = calculateCredits(input , "")+200; // adding buffer for response
        if (availableCredits < creditsToDeduct) {
            throw new RuntimeException("Insufficient credits");
        }
    }

    public void deductCredits(String userId, String input , String output) {
        UserCredit userCredit = creditRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("User Credit not found"));
        long creditsToDeduct = calculateCredits(input , output);
        long availableCredits = userCredit.getTotalCredits() - userCredit.getUsedCredits();
        if (availableCredits < creditsToDeduct) {
            throw new RuntimeException("Insufficient credits");
        }
        userCredit.setUsedCredits(userCredit.getUsedCredits() + creditsToDeduct);
        creditRepo.save(userCredit);
    }
}
