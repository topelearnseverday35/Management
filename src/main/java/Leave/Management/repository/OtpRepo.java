package Leave.Management.repository;

import Leave.Management.entity.OtpEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpRepo extends JpaRepository<OtpEntity, Integer> {
    Optional<OtpEntity> findByOtp(String otp);
    Optional<OtpEntity>findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE otp_table set status = 'SUCCESSFUL' WHERE status = 'pending'",nativeQuery = true)
    void updateStatusToSuccessful();

    @Modifying
    @Transactional
    @Query(value = "UPDATE otp_table set status = 'FAILED' WHERE status = 'pending'",nativeQuery = true)
    void updateStatusToFailed();

}
