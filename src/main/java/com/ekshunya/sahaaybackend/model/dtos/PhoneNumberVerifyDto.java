package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

@Value
public class PhoneNumberVerifyDto  {
     String userId;
     String verificationCode;
}
