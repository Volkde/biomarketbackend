package de.aittr.bio_marketplace.service;

import de.aittr.bio_marketplace.domain.entity.User;
import de.aittr.bio_marketplace.security.config.JwtProperties; // если нужно, уберите
import de.aittr.bio_marketplace.service.interfaces.ConfirmationService;
import de.aittr.bio_marketplace.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
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

    private final JavaMailSender sender;
    private final Configuration mailConfig;
    private final ConfirmationService confirmationService;

    //Подставьте ваш URL, по которому будет переходить пользователь
    @Value("${app.confirm.url:http://localhost:8080/api/register/confirm}")
    private String confirmUrl;

    public EmailServiceImpl(JavaMailSender sender,
                            Configuration mailConfig,
                            ConfirmationService confirmationService) {
        this.sender = sender;
        this.mailConfig = mailConfig;
        this.confirmationService = confirmationService;
        mailConfig.setDefaultEncoding("UTF-8");
        mailConfig.setTemplateLoader(new ClassTemplateLoader(EmailServiceImpl.class, "/mail/"));
        // /mail/ => папка в resources, где лежит шаблон
    }

    @Override
    public void sendConfirmationEmail(User user) {
        try {
            //Генерим код и сохраняем в БД
            String code = confirmationService.generateConfirmationCode(user);

            //Формируем письмо
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom("example@mail.com"); // замените на ваш email
            helper.setTo(user.getEmail());
            helper.setSubject("Confirm your registration");

            //Подставляем в шаблон параметры
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");
            Map<String, Object> params = new HashMap<>();
            params.put("username", user.getUsername());
            params.put("confirmLink", confirmUrl + "/" + code);

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
            helper.setText(html, true);

            //Отправляем
            sender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error sending confirmation email: " + e.getMessage());
        }
    }
}
