package com.zerobase.bakingin_project.config;

import com.zerobase.bakingin_project.BakingInProjectApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BakingInProjectApplication.class);
    }

}
