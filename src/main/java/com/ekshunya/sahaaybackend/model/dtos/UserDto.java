package com.ekshunya.sahaaybackend.model.dtos;

import lombok.Value;

@Value
public class UserDto  {
     String userType;
     String userName;
     String userId;
     String avatarUrl;
}