package com.nextech.erp.serviceImpl;

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
import com.nextech.erp.model.Notification;
import com.nextech.erp.service.MailService;
@Service
public class MailServiceImpl extends CRUDServiceImpl<Notification> implements MailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	VelocityEngine velocityEngine;

	@Async
	public void sendEmail( Mail mail,Notification notification) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

		       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	            mimeMessageHelper.setSubject(mail.getMailSubject());
	    //        mimeMessageHelper.setFrom(mail.getMailFrom());
	            mimeMessageHelper.setBcc(mail.getMailBcc());
	            mimeMessageHelper.setCc(mail.getMailCc());
	            mimeMessageHelper.setTo(mail.getMailTo());
	            mail.setMailContent(geContentFromTemplate(mail.getModel(),notification));
	            mimeMessageHelper.setText(mail.getMailContent(), true);
	          //  FileSystemResource fileSystemResource = new FileSystemResource("");
	            	 FileSystemResource fileSystemResource = new FileSystemResource(mail.getAttachment());
	                 mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);


	            mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public String geContentFromTemplate(Map<String, Object> model,Notification notification) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, notification.getTemplate(), model));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	@Async
	public void sendEmailWithoutPdF( Mail mail,Notification notification) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

		       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	            mimeMessageHelper.setSubject(mail.getMailSubject());
	         //   mimeMessageHelper.setFrom(mail.getMailFrom());
	            if(mail.getMailBcc() != null)
	            	mimeMessageHelper.setBcc(mail.getMailBcc());
	            if(mail.getMailCc() != null)
	            	mimeMessageHelper.setCc(mail.getMailCc());
	            if(mail.getMailTo() != null)
	            	mimeMessageHelper.setTo(mail.getMailTo());
	            mail.setMailContent(geContentFromTemplate(mail.getModel(),notification));
	            mimeMessageHelper.setText(mail.getMailContent(), true);
	            mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Async
	@Override
	public void sendEmailVendor(Mail mail) {
		// TODO Auto-generated method stub
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		try {

		       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

	            mimeMessageHelper.setSubject(mail.getMailSubject());
	            mimeMessageHelper.setFrom(mail.getMailFrom());
	            mimeMessageHelper.setTo(mail.getMailTo());
	            mail.setMailContent(geContentFromTemplate(mail.getModel()));
	            mimeMessageHelper.setText(mail.getMailContent(), true);
	            mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	   @SuppressWarnings("deprecation")
		public String geContentFromTemplate(Map < String, Object > model) {
	        StringBuffer content = new StringBuffer();
	        try {
	            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/vendor-email-template.vm", model));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return content.toString();
	    }


}