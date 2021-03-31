package ar.com.pagatutti.mailapi.services;

import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ar.com.pagatutti.mailapi.beans.SendMailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


@Service
public class EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration freeMarkerConfig;
	
	//Could throw TemplateNotFoundException, MalformedTemplateNameException, MessagingException
	public void sendMail(SendMailRequest mailRequest) throws Exception{
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);	
		String htmlMessage = null;
		
		Template temp = freeMarkerConfig.getTemplate(mailRequest.getTemplate());
		htmlMessage = FreeMarkerTemplateUtils.processTemplateIntoString(temp, mailRequest.getData());
			
		helper.setTo(mailRequest.getTo());
		helper.setText(htmlMessage, true);
		helper.setSubject(mailRequest.getSubject());
		helper.setFrom(mailRequest.getFrom());
		sender.send(message);
	}
}
