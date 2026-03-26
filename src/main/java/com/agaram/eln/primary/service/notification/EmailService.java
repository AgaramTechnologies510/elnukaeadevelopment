package com.agaram.eln.primary.service.notification;

import java.net.URL;
import java.util.Properties;
import java.util.Random;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.URLDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.agaram.eln.primary.controller.usermanagement.UserController;
import com.agaram.eln.primary.model.notification.Email;
import com.agaram.eln.primary.repository.notification.EmailRepository;

@Service
@EnableJpaRepositories(basePackageClasses = EmailRepository.class)
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private UserController UserController;
	@Autowired
	private EmailRepository EmailRepository;

	@Autowired
	private org.springframework.core.env.Environment env;

	public Email sendPlainTextEmail(Email email) throws MessagingException {

		String from = env.getProperty("spring.mail.username");
		String to = email.getMailto();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setSubject("This is an OTP verification email");
		helper.setFrom(from);
		helper.setTo(to);

		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		String ch = String.format("%06d", number);
		number = Integer.parseInt(ch);

		// email.setOptcode(number);

		boolean html = true;
		helper.setText("<b>Dear Customer</b>,<br><i>use code <b>" + number
				+ "</b> to login our account Never share your OTP with anyone</i>", html);

		mailSender.send(message);

		EmailRepository.save(email);

		// model.addAttribute("message", "A plain text email has been sent");
		return email;

	}

	public Email sendusernamepassemail(Email email) throws MessagingException {
//		boolean valid = true;
		String from = env.getProperty("spring.mail.username");
		String to = email.getMailto();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setSubject("Username and Password");
		helper.setFrom(from);
		helper.setTo(to);

		// lower limit for LowerCase Letters
		int lowerLimit = 97;

		// lower limit for LowerCase Letters
		int upperLimit = 122;

		Random random = new Random();
		int n = 6;
		// Create a StringBuffer to store the result
		StringBuffer r = new StringBuffer(n);

		for (int i = 0; i < n; i++) {
			int nextRandomChar = lowerLimit + (int) (random.nextFloat() * (upperLimit - lowerLimit + 1));
			r.append((char) nextRandomChar);
		}
		String pass = r.toString();
		// return the resultant string
		System.out.println(pass);

//		String passwordtenant = AESEncryption.encrypt(pass);
		// email.setPassword(passwordtenant);

//		boolean html = true;
		// helper.setText("<b>Dear Customer</b>,<br><i>This is for your username and
		// password</i><br><b>UserName:\t\t"+email.getTenantid()+"</b><br><b>Password:\t\t"+pass+"</b>",
		// html);

		mailSender.send(message);

		// String username=email.getTenantid();
		// DataSourceConfigRepository.setverifiedemailandtenantpassword(valid,passwordtenant,username);

		// EmailRepository.setpasswordBytenantid(email.getPassword(),email.getTenantid());
		return email;
	}

	public Email sendEmail(Email email) throws MessagingException {

		try {
			String to = email.getMailto();
			String from = env.getProperty("spring.mail.username");
			String password = env.getProperty("spring.mail.password");
			String host = env.getProperty("spring.mail.host");
			String port = env.getProperty("spring.mail.port");

			// ✅ FIX 1: Properties BEFORE JavaMailSender
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true"); // CRITICAL
			props.put("mail.smtp.starttls.required", "true");
			props.put("mail.smtp.ssl.protocols", "TLSv1.2");
			props.put("mail.debug", "true");

			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
			mailSender.setJavaMailProperties(props); // Apply BEFORE host/port
			mailSender.setUsername(from);
			mailSender.setPassword(password);

			// ✅ FIX 2: Use mailSender consistently (not mailSender)
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(email.getSubject());
			helper.setText(email.getMailcontent(), true);

			mailSender.send(message); // ✅ Fixed variable name
			EmailRepository.save(email);
			return email;
		} catch (Exception e) {
			e.printStackTrace();
			return email;
		}

// 	public Email sendEmail(Email email) throws MessagingException {
// 		UserController.sendTestMail();
// 	     String to = email.getMailto();
// 	     String from = env.getProperty("spring.mail.username");
//        JavaMailSenderImpl mailSender1 = new JavaMailSenderImpl();
//        mailSender1.setHost("smtp.office365.com");
//        mailSender1.setPort(587);
//        mailSender1.setUsername("santhoshkumar.s@agaramtech.com");
//        mailSender1.setPassword("kdnmcsswzsqvkxjm");
// //       mailSender1.setHost(env.getProperty("spring.mail.host"));
// //       int port = Integer.parseInt(env.getProperty("spring.mail.port"));
// //       mailSender1.setPort(port);
// //       mailSender1.setUsername(env.getProperty("spring.mail.username"));
// //       mailSender1.setPassword(env.getProperty("spring.mail.password"));
//        String htmlText = email.getMailcontent();
//        Properties props1 = mailSender1.getJavaMailProperties();
//        props1.put("mail.transport.protocol", "smtp");
//        props1.put("mail.smtp.auth", "true");
//        props1.put("mail.smtp.starttls.enable", "true");
//        props1.put("mail.smtp.starttls.required", "true");
//        props1.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        props1.put("mail.debug", "true");
//        MimeMessage message1 = mailSender1.createMimeMessage();
//        MimeMessageHelper helper1 =
//                new MimeMessageHelper(message1, true, "UTF-8");
//        helper1.setFrom(from);
//        helper1.setTo(to);
//        helper1.setSubject("email saas test");
//        helper1.setText("""
// 	                    <h2>Mail Test Successful ✅</h2>
// 	                    <p>This mail was sent using inline SMTP config.</p>
// 	                    """, true);
//        mailSender.send(message1);
//        EmailRepository.save(email);
// 		 return email;
//		String from = env.getProperty("spring.mail.username");
//		String to = email.getMailto();
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//
//		helper.setSubject(email.getSubject());
//		helper.setFrom(from);
//		helper.setTo(to);
//
//		// Set Subject: header field
//		message.setSubject(email.getSubject());
//
//		// This mail has 2 part, the BODY and the embedded image
//		MimeMultipart multipart = new MimeMultipart("related");
//
//		// first part (the html)
//		BodyPart messageBodyPart = new MimeBodyPart();
//
//		String htmlText = email.getMailcontent();
//
//		messageBodyPart.setContent(htmlText, "text/html");
//		// add it
//		multipart.addBodyPart(messageBodyPart);
//
//		/*
//		 * messageBodyPart = new MimeBodyPart(); String userDirectory = new
//		 * File("").getAbsolutePath(); DataSource fds1 = new
//		 * FileDataSource(userDirectory +
//		 * "/src/main/resources/images/Logilab ELN_vertical.jpg");
//		 * messageBodyPart.setDataHandler(new DataHandler(fds1));
//		 * messageBodyPart.addHeader("Content-ID", "<image>");
//		 * multipart.addBodyPart(messageBodyPart);
//		 */
//
//		// second part (the image)
//		/*
//		 * messageBodyPart = new MimeBodyPart(); DataSource fds = new FileDataSource(
//		 * userDirectory +
//		 * "/src/main/resources/images/AgaramTechnologies_vertical.jpg");
//		 * 
//		 * messageBodyPart.setDataHandler(new DataHandler(fds));
//		 * messageBodyPart.setHeader("Content-ID", "<seconimage>");
//		 * 
//		 * // add image to the multipart multipart.addBodyPart(messageBodyPart);
//		 */
//
//		// put everything together
//		message.setContent(multipart);
//		// Send message
//		mailSender.send(message);
//		EmailRepository.save(email);
//		
//		
//		
//
//		return email;
	}

	public Email sendmailOPT(Email email) throws MessagingException {

		String from = env.getProperty("spring.mail.username");
		String to = email.getMailto();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setSubject(email.getSubject());
		helper.setFrom(from);
		helper.setTo(to);

		boolean html = true;

		helper.setText(email.getMailcontent(), html);

		mailSender.send(message);

		EmailRepository.save(email);

		return email;

	}

	public Email sendEmailelnLite(Email email) throws MessagingException {
		String from = env.getProperty("spring.mail.username");
//		String cc = env.getProperty("spring.mail.mailcc");
		String to = email.getMailto();
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

		helper.setSubject(email.getSubject());
		helper.setFrom(from);
		helper.setTo(to);
//        helper.setCc(cc);
		// Create the multipart for mixed content (text and images)
		MimeMultipart multipart = new MimeMultipart("related");

		// Part 1: HTML content
		BodyPart htmlPart = new MimeBodyPart();
		String htmlText = email.getMailcontent(); // Ensure this HTML has correct cid references
		htmlPart.setContent(htmlText, "text/html; charset=utf-8"); // Set HTML content
		multipart.addBodyPart(htmlPart);

		// Attach inline images
		attachInlineImage(multipart, "images/eln_lite_logo.png", "<image1>");
		attachInlineImage(multipart, "images/ag_logo.png", "<image2>");
		attachInlineImage(multipart, "images/Agaram_Technologies_horizontal.png", "<image3>");

		// Set the multipart as the message content
		message.setContent(multipart);

		// Send the message
		mailSender.send(message);

		// Optionally, save the email to repository
		EmailRepository.save(email);

		return email;

	}

	public Email sendEmailelnLiteCopy(Email email) throws MessagingException {
		String from = env.getProperty("spring.mail.username");
		String copy = env.getProperty("spring.mail.mailcopy");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart message

		helper.setSubject(email.getSubject());
		helper.setFrom(from);
		helper.setTo(copy);

		// Create the multipart for mixed content (text and images)
		MimeMultipart multipart = new MimeMultipart("related");

		// Part 1: HTML content
		BodyPart htmlPart = new MimeBodyPart();
		String htmlText = email.getMailcontent(); // Ensure this HTML has correct cid references
		htmlPart.setContent(htmlText, "text/html; charset=utf-8"); // Set HTML content
		multipart.addBodyPart(htmlPart);

		// Attach inline images
		attachInlineImage(multipart, "images/eln_lite_logo.png", "<image1>");
		attachInlineImage(multipart, "images/ag_logo.png", "<image2>");
		attachInlineImage(multipart, "images/Agaram_Technologies_horizontal.png", "<image3>");

		// Set the multipart as the message content
		message.setContent(multipart);

		// Send the message
		mailSender.send(message);

		// Optionally, save the email to repository
		EmailRepository.save(email);

		return email;

	}

	private void attachInlineImage(MimeMultipart multipart, String imagePath, String contentId)
			throws MessagingException {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			MimeBodyPart imagePart = new MimeBodyPart();
			URL imageUrl = classLoader.getResource(imagePath);
			DataSource fds = new URLDataSource(imageUrl);
			imagePart.setDataHandler(new DataHandler(fds));
			imagePart.setHeader("Content-ID", contentId);
			imagePart.setDisposition(MimeBodyPart.INLINE); // Set Content-Disposition to inline
			multipart.addBodyPart(imagePart);
		} catch (Exception e) {
			throw new MessagingException("Failed to attach inline image: " + imagePath, e);
		}
	}

}