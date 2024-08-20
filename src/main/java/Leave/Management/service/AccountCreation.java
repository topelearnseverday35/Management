package Leave.Management.service;

import Leave.Management.dto.AccountCreationRequest;
import Leave.Management.dto.OtpRequest;
import Leave.Management.entity.LeaveAccountCreationEntity;
import Leave.Management.entity.OtpEntity;
import Leave.Management.entity.Status;
import Leave.Management.repository.LeaveAccountCreationRepo;
import Leave.Management.repository.OtpRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountCreation {
    private final LeaveAccountCreationRepo accountCreationRepo;
    private final MailService mailService;
    private final OtpService otpService;
    private final OtpRepo otpRepo;



    LeaveAccountCreationEntity accountCreation = new LeaveAccountCreationEntity();


    private String generateUniqueOtp() {
        String otpCode;
        boolean isUnique;
        do {
            otpCode = String.valueOf(otpService.generateOtp());
            isUnique = otpRepo.findByOtp(otpCode).isEmpty();
        } while (!isUnique);
        return otpCode;
    }

    private void sendEmail(AccountCreationRequest accountCreationRequest, String otpCode) {
        mailService.sendMail(accountCreationRequest.getEmail(),


                "[Leave Management System] Please Verify Your Email",
                "Hey " + accountCreation.getFirstname() + "In Order to Create An Account With Us ,\n" +
                        "You need to verify your Email \n" + "Your Otp is " + otpCode + "\n Note : It Expires In 3 MINUTES");


    }


    private void UpdateAccount(LeaveAccountCreationEntity accountCreation, AccountCreationRequest accountCreationRequest) {
        accountCreation.setFirstname(accountCreationRequest.getFirstname());
        accountCreation.setLastname(accountCreationRequest.getLastname());
        accountCreation.setEmail(accountCreationRequest.getEmail());
        accountCreation.setPassword(accountCreationRequest.getPassword());
        accountCreation.setAddress(accountCreationRequest.getAddress());
        accountCreation.setStatus(Status.PENDING);
        accountCreation.setPhonenumber(accountCreationRequest.getPhonenumber());
        accountCreationRepo.save(accountCreation);
    }

    private void UpdateOtp(LeaveAccountCreationEntity accountCreation, String otpCode) {
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setOtp(otpCode);
        otpEntity.setGeneratedDate(LocalDateTime.now());
        otpEntity.setExpiryDate(LocalDateTime.now().plusMinutes(3));
        otpEntity.setEmail(accountCreation.getEmail());
        otpEntity.setStatus(Status.PENDING);
        otpRepo.save(otpEntity);

    }


    @Transactional
    public String verifyEmail(AccountCreationRequest accountCreationRequest) {
        Optional<LeaveAccountCreationEntity> emailCheck = accountCreationRepo.findByEmail(accountCreationRequest.getEmail());

        if (emailCheck.isPresent()) {
            LeaveAccountCreationEntity existingEntity = emailCheck.get();
            if (existingEntity.getStatus().equals(Status.SUCCESSFUL)) {
                return "An account with this email already exists";
            }

            if (existingEntity.getStatus().equals(Status.PENDING)) {
                String OtpCode = generateUniqueOtp();
                sendEmail(accountCreationRequest, OtpCode);
                UpdateAccount(existingEntity, accountCreationRequest);
                return "An Email with the Otp has been verified successfully";
            }
        }

        String OtpCode = generateUniqueOtp();
        sendEmail(accountCreationRequest, OtpCode);

        LeaveAccountCreationEntity newEntity = new LeaveAccountCreationEntity();
        UpdateAccount(newEntity, accountCreationRequest);
        UpdateOtp(newEntity, OtpCode);
        return "An Email With an OTP has been sent to the provided email";

    }


    @Transactional
    public String createAccount(OtpRequest otpRequest) {
        Optional<LeaveAccountCreationEntity> emailCheck = accountCreationRepo.findByEmail(otpRequest.getEmail());
        if (emailCheck.isPresent()) {
            LeaveAccountCreationEntity existingEntity5 = emailCheck.get();


            Optional<OtpEntity> existingEntity1 = otpRepo.findByEmail(otpRequest.getEmail());
            if (existingEntity1.isPresent()) {

                OtpEntity existingEntity3 = existingEntity1.get();
                if (existingEntity3.getEmail().equals(otpRequest.getEmail())) {

                    if (existingEntity3.getOtp().equals(otpRequest.getOtp())) {

                        if (existingEntity3.getStatus().equals(Status.PENDING)) {

                            if (existingEntity3.getExpiryDate().isAfter(LocalDateTime.now())) {
                                existingEntity3.setStatus(Status.SUCCESSFUL);
                                otpRepo.save(existingEntity3);
                                existingEntity5.setStatus(Status.SUCCESSFUL);
                                return "OTP Validated Successfully";
                            } else {
                                existingEntity3.setStatus(Status.FAILED);
                                otpRepo.save(existingEntity3);
                                return "OTP is Expired";
                            }
                        } else {
                            return "Otp Does Not Exist";
                        }

                    }
                    return "This is not the Account Linked with this Otp";
                }
                return "This Email Does Not Exist";

            }
            return "This Account Linked with this Email Does Not Exist.Please Verify Your Email ";
        }
        return "This Email Does Not Exist.Please Verify Your Email ";

    }

    @Transactional
    public String LogIn(AccountCreationRequest accountCreationRequest) {
        Optional<LeaveAccountCreationEntity> accountCheck = accountCreationRepo.findByEmail(accountCreationRequest.getEmail());
        if (accountCheck.isPresent()) {
            LeaveAccountCreationEntity existingAccount = accountCheck.get();
            if (existingAccount.getStatus().equals(Status.SUCCESSFUL)) {
                if (existingAccount.getEmail().equals(accountCreationRequest.getEmail())) {
                    if (existingAccount.getPassword().equals(accountCreationRequest.getPassword())) {
                        return "Logged in Successfully";
                    }
                    return "Wrong Password";
                }
                return "Wrong Email";
            }
            return "Account Has Not Yet Been Verified";
        }
        return "Account Does Not Exist.Please Verify Your Email ";
    }
}



//    private String updateProfile(LeaveAccountCreationEntity accountCreation, AccountCreationRequest accountCreationRequest) {
//        if()
//        accountCreation.setFirstname(accountCreationRequest.getFirstname());
//        accountCreation.setLastname(accountCreationRequest.getLastname());
//        accountCreation.setEmail(accountCreationRequest.getEmail());
//        accountCreation.setPassword(accountCreationRequest.getPassword());
//        accountCreation.setAddress(accountCreationRequest.getAddress());
//        accountCreation.setStatus(Status.PENDING);
//        accountCreation.setPhonenumber(accountCreationRequest.getPhonenumber());
//        accountCreationRepo.save(accountCreation);
//        return "Profile Updated Successfully";
//    }







