package br.com.fgalha.pocs.admintool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@ComponentScans(value = {@ComponentScan("br.com.b3.tar.admintool"), @ComponentScan("br.com.bvmf.cau.gatekeeper")})
public class AdminToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminToolApplication.class, args);
    }
}
