package Leave.Management.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OtpRequest {
    private String Otp;
    private String Email;
}
