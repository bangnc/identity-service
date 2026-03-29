package com.bangnc.identity_service.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationRequest {
    @Size(min = 3, message = "UserName must be at least 8 character")
    private String userName;

    @Size(min=8, message = "Password must be at least 8 character")
    private String passWord;
    private String firstName;
    private String lastName;
    private LocalDate dob;

}
