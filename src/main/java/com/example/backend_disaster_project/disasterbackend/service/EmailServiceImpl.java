package com.example.backend_disaster_project.disasterbackend.service;

import com.example.backend_disaster_project.disasterbackend.entities.RescueHelper;


import com.example.backend_disaster_project.disasterbackend.properties.EmailProperties;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private Configuration configuration;

    @Autowired
    private  JavaMailSender javaMailSender;
    @Autowired
    private  EmailProperties emailProperties;

    @Value("${client.url}")
    String clientUrl;



    @Override
    @Async("threadPoolEmailTask")
    public void sendPasswordResetEmail(RescueHelper userDto, String token) {
        // prepare email meta data
        String recipientAddress = userDto.getEmail();
        String subject = emailProperties.getSubjectPasswordReset();
        // confirmationUrl - front end url, shows form for password reset.
        String confirmationUrl = emailProperties.getUrlPasswordReset() + token;
        String body = confirmationUrl;
        // send email
        sendEmail(recipientAddress, subject, body);
    }


    private void sendEmail(String recipientAddress, String subject, String body) {
        try {
            MimeMessage email = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email);
            helper.setFrom(emailProperties.getSenderAddress());
            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setText(body, true);

            javaMailSender.send(email);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

//    private String getEmailBody(String url, String name, String templateName) {
//        try {
//            Template template = configuration.getTemplate(templateName);
//            Map<String, String> map = new HashMap<>();
//            map.put("USER_ACTION_LINK", url);
//            map.put("NAME", name);
//            map.put("FRONT_END_URL", clientUrl);
//            return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }

}
