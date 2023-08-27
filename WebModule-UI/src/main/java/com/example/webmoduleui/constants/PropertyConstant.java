package com.example.webmoduleui.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("PropertyConstant")
@Data
public class PropertyConstant {

    @Value("${system.verifyCode.prefix}")
    private String verifyPrefix;

    @Value("${system.file.upload.path}")
    private String uploadPath;

    @Value("${system.file.view.path}")
    private String viewPath;

}
