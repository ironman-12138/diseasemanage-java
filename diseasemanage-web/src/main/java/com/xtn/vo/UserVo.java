package com.xtn.vo;

import lombok.Data;

@Data
public class UserVo {

    private Long id;
    private String username;
    private String nickname;
    private String email;
    private Integer sex;
    private Long departmentId;
    private String departmentName;
    private String phoneNumber;
    private String birth;
}
