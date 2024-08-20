package Leave.Management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "Otp_Table")
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String otp;
    private LocalDateTime generatedDate = LocalDateTime.now();
    private LocalDateTime expiryDate = generatedDate.plusMinutes(3);
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
}
