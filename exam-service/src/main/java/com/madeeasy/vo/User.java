package com.madeeasy.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> roles;

}
