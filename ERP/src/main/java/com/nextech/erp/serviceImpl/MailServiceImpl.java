package com.nextech.erp.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.nextech.erp.dto.Mail;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.UserService;
@Service
public class MailServiceImpl  implements MailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	VelocityEngine velocityEngine;
	
	@Autowired
	UserService userService;

	@Async
	public void sendEmail( Mail mail,NotificationDTO notification) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

		       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	            mimeMessageHelper.setSubject(mail.getMailSubject());
	    //        mimeMessageHelper.setFrom(mail.getMailFrom());
	            String[] bccAddress = mail.getMailBcc().split(",");
	            mimeMessageHelper.setBcc(bccAddress);
	            
	            String[] toAddress = mail.getMailTo().split(",");
	            mimeMessageHelper.setTo(toAddress);
	            
	            String[] ccAddress = mail.getMailCc().split(",");
	            mimeMessageHelper.setCc(ccAddress);
	            
	            mail.setMailContent(geContentFromTemplate(mail.getModel(),notification));
	            mimeMessageHelper.setText(mail.getMailContent(), true);
	            	 FileSystemResource fileSystemResource = new FileSystemResource(mail.getAttachment());
	                 mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);


	            mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public String geContentFromTemplate(Map<String, Object> model,NotificationDTO notification) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, notification.getTemplate(), model));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	@Async
	public void sendEmailWithoutPdF( Mail mail,NotificationDTO notification) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

		       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	            mimeMessageHelper.setSubject(mail.getMailSubject());
	         //   mimeMessageHelper.setFrom(mail.getMailFrom());
	            
	            String[] bccAddress = mail.getMailBcc().split(",");
	            mimeMessageHelper.setBcc(bccAddress);
	            
	            String[] toAddress = mail.getMailTo().split(",");
	            mimeMessageHelper.setTo(toAddress);
	            
	            String[] ccAddress = mail.getMailCc().split(",");
	            mimeMessageHelper.setCc(ccAddress);
	            
	            mail.setMailContent(geContentFromTemplate(mail.getModel(),notification));
	            mimeMessageHelper.setText(mail.getMailContent(), true);
	            mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Mail setMailDetails(String firstName,String lastName,String email,NotificationDTO  notificationDTO) throws Exception {
		// TODO Auto-generated method stub
		 Mail mail = userService.emailNotification(notificationDTO);
		   mail.setMailSubject(notificationDTO.getSubject());
	        Map < String, Object > model = new HashMap < String, Object > ();
	        model.put("firstName", firstName);
	        model.put("lastName", lastName);
	        model.put("email", email);
	        model.put("location", "Pune");
	        model.put("signature", "www.NextechServices.in");
	        mail.setModel(model);
		return mail;
	}
}
