package chatApp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImplementation implements EmailSenderService{
    private final JavaMailSender mailSender;
    public EmailSenderServiceImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(String destinationEmail, String verificationCode, int id) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("lera38lera@gmail.com");
        simpleMailMessage.setTo(destinationEmail);
        simpleMailMessage.setSubject("Verification email for chat-app");
        simpleMailMessage.setText(verificationCode + " \n Go to: \n" + "<a href = \"http://localhost:9000/pages/confirmation.html?id="+id+">Verification page</a>");
        this.mailSender.send(simpleMailMessage);
    }
}