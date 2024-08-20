package Leave.Management.repository;


import Leave.Management.entity.LeaveAccountCreationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LeaveAccountCreationRepo  extends JpaRepository<LeaveAccountCreationEntity,Long> {
   Optional<LeaveAccountCreationEntity> findByEmail(String email);

   @Modifying
   @Query(value = "UPDATE account_table set status = 'SUCCESSFUL' WHERE status = 'PENDING'",nativeQuery = true)
   void updateStatusToSuccessful();




}
