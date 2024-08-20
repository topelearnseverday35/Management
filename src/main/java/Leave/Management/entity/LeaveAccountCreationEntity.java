package Leave.Management.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "Account_Table")
public class LeaveAccountCreationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    @Column(unique=true)
    private String phonenumber;
    @NotNull
    private String address;
    @NotNull
    @Column(unique = true, nullable = false)
    private String email;
    @NotNull
    private String password;
   @Enumerated(EnumType.STRING)
    private Status status;


}
