package com.example.datamoduleserve.dto;

import lombok.Data;

/**
 * @author sdx2009
 * @date 2023/4/10 15:22
 */
@Data
public class InputUser {

    private String userid;
    private String password;
    private String VerifyCode;

    public InputUser(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
