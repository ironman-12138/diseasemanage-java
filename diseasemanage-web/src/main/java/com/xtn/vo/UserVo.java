package com.xtn.vo;

import com.xtn.domain.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserVo {

    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private Integer sex;
    private Long departmentId;
    private String departmentName;
    private String phoneNumber;
    private String birth;
    private List<Long> roleIdList;
}
