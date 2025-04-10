package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.service.interfaces.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;

    @Value("${app.confirm-url-prefix}")
    private String confirmUrlPrefix;

    public EmailServiceImpl(JavaMailSender mailSender,
                            Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void sendConfirmationEmail(User user, String code) {
        try {
            String html = generateEmailHtml(user, code);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");


            helper.setFrom("admin@bio-marketplacet.de");
            helper.setTo(user.getEmail());
            helper.setSubject("BioMarketplace Confirm Registration");
            helper.setText(html, true);


            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email: " + e.getMessage());
        }
    }

    private String generateEmailHtml(User user, String code) {
        try {

            Template template = freemarkerConfig.getTemplate("confirm_email.ftlh");


            Map<String, Object> model = new HashMap<>();
            model.put("username", user.getUsername());


            String confirmLink = confirmUrlPrefix + code;
            model.put("confirmLink", confirmLink);

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        } catch (Exception e) {
            throw new RuntimeException("Error generating email text: " + e.getMessage());
        }
    }
}
