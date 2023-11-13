package org.polytech.covidapi.payload.request;

import jakarta.validation.constraints.*;

public class UpdateUserPasswordRequest {
    @NotBlank
    @Size(min = 6, max = 40)
    private String oldPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
}
