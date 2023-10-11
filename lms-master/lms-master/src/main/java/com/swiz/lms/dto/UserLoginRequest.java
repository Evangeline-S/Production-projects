package com.swiz.lms.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserLoginRequest {

    private String email;
    private String password;

}
