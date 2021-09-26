package com.breakbooking.eventbookingapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;


@Service
public class EmailSenderService {


    private JavaMailSender mailSender;

    @Autowired
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingConfirmationEmail(String toEmail,
                                             String body,
                                             String subject){

     //   SimpleMailMessage message=new SimpleMailMessage();

        try{

            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom("shwetalishine@gmail.com");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.setSubject(subject);

            mailSender.send(mimeMessage);

        }catch (MessagingException e){
        e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Mail Sent...");
    }



    public void sendBookingConfirmationEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        String attachment){

        try{
           MimeMessage mimeMessage=mailSender.createMimeMessage();
           MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
           mimeMessageHelper.setFrom("shwetalishine@gmail.com");
           mimeMessageHelper.setTo(toEmail);
           mimeMessageHelper.setText(body);
           mimeMessageHelper.setSubject(subject);

           FileSystemResource fileSystemResource=new FileSystemResource(new File(attachment));
           mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);

           mailSender.send(mimeMessage);

       }catch (MessagingException e){
        e.printStackTrace();
       }catch (FileSystemNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
           e.printStackTrace();
       }

        System.out.println("Mail sent..");
    }

}
