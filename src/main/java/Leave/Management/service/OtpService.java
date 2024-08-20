package Leave.Management.service;

import Leave.Management.dto.AccountCreationRequest;
import Leave.Management.dto.OtpRequest;
import Leave.Management.entity.OtpEntity;
import Leave.Management.entity.Status;
import Leave.Management.repository.OtpRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;



@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OtpService {
    private final OtpRepo otpRepo;
    private final AccountCreationRequest request;
    private final OtpRequest otpRequest;


    OtpEntity otpEntity = new OtpEntity();

    public int generateOtp() {
        try {
            SecureRandom rand = new SecureRandom();
            int max = 9999;
            int min = 1000;
            int randomNumber = rand.nextInt((max - min) + 1) + min;
            log.info("Generates OTP {}", randomNumber);
            String otpCode = String.valueOf(randomNumber);
            return randomNumber;


        } catch (Exception e) {
            log.error("Error Generating OTP : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }


}


