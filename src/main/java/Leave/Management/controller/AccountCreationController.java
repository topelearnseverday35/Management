package Leave.Management.controller;

import Leave.Management.dto.AccountCreationRequest;
import Leave.Management.dto.OtpRequest;
import Leave.Management.service.AccountCreation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Leave-Management")
@RequiredArgsConstructor
public class AccountCreationController {
    private final AccountCreation accountCreation;


    @Transactional
    @PostMapping("/verify-Email")
    public String verifyEmail(@RequestBody AccountCreationRequest accountCreationRequest) {

        return accountCreation.verifyEmail(accountCreationRequest);
    }
  @Transactional
    @PostMapping("/create-Account")
    public String createAccount(@RequestBody OtpRequest otpRequest) {
        return accountCreation.createAccount(otpRequest);
    }

    @Transactional
    @GetMapping("/LogIn")
    public String LogIn(@RequestBody AccountCreationRequest accountCreationRequest) {
        return accountCreation.LogIn(accountCreationRequest);
    }
}

