package com.xzbd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.xzbd.*.dao")
@SpringBootApplication
@EnableCaching
public class XwMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(XwMailApplication.class, args);
        System.out.println("\n" +
                        " __    _ _   _   _     __   __            _\n"+
                        " \\ \\` / | | | | | |'. / _\\_/_ \\  __ _    | |\n"+
                        "  \\.\\/ /| | | | | |  / / \\ / \\ \\/ _` |(_)| |\n"+
                        "'./ .\\ \\\\ \\_| |_/ /__| | | | | | (_| /| || |_\n"+
                        " /_/  \\_\\\\_\\_V_/_/   |_| |_| |_|\\__,_)|_||_ _)\n"+
                        " :: XW-Mail  ::        (v1.0.0)\n"+
                "\n ヾ(◍°∇°◍)ﾉﾞ    xw-mail启动成功      ヾ(◍°∇°◍)ﾉﾞ"
                );
    }
}
