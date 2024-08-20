package Leave.Management.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AccountCreationRequest {
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String phonenumber;
    private String address;
}
