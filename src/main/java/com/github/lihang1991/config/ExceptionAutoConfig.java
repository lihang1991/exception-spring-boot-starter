package com.github.lihang1991.config;

import com.github.lihang1991.handle.ExceptionHandler;
import com.github.lihang1991.notice.EmailNoticeComponent;
import com.github.lihang1991.property.ExceptionEmailProperties;
import com.github.lihang1991.property.ExceptionSettingProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;


/**
 *  自动装配
 * @author lih
 * @date 2019-04-20-9:31.
 */
@Configuration
@EnableConfigurationProperties({ ExceptionEmailProperties.class, ExceptionSettingProperties.class})
@ConditionalOnBean(name = { "exceptionAspect" })
public class ExceptionAutoConfig {

    @Bean
    @ConditionalOnBean(name = { "exceptionAspect" })
    public EmailNoticeComponent emailNoticeComponent(MailSender mailSender, MailProperties mailProperties, ExceptionEmailProperties exceptionEmailProperties) {
        return new EmailNoticeComponent(mailSender, mailProperties, exceptionEmailProperties);
    }

    @Bean
    @ConditionalOnBean(name = { "exceptionAspect" })
    public ExceptionHandler exceptionHandler(EmailNoticeComponent emailNoticeComponent, ExceptionSettingProperties exceptionSettingProperties) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(emailNoticeComponent, exceptionSettingProperties);
        return exceptionHandler;
    }
}
