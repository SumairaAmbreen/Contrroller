package com.Hostel.Hostel_Management_System.Controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hostel.Hostel_Management_System.Repository.Role_Repository;
import com.Hostel.Hostel_Management_System.Repository.User_Repository;
import com.Hostel.Hostel_Management_System.Service.MailService;
import com.Hostel.Hostel_Management_System.model1.PasswodChanger2;
import com.Hostel.Hostel_Management_System.model1.User;

@RestController
@RequestMapping("/ForgotPassword")
@CrossOrigin(origins = "*")
@Validated
public class ForgottonPassword_Controller {
	
	@Autowired
	User_Repository userRepository;

	@Autowired
	Role_Repository roleRepository;

	@Autowired
	PasswordEncoder Encoder;

	@Autowired
	private MailService emailService;

	@PostMapping("/forgotPassword/{userEmail}")
	public String processForgotPasswordForm(@PathVariable String userEmail, 
										HttpServletRequest request) 
	{
		if (!userRepository.findByEmail(userEmail).isPresent()) 
		{
			return "ErrorMessage:Oops! We didn't find an account for that e-mail address.";
		} 
		else 
		{
			User user = userRepository.findByEmail(userEmail).get();
			user.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userRepository.save(user);
			
			String appUrl = request.getScheme() 
							+"://" 
							+ request.getServerName()
							+ ":"
							+ request.getLocalPort();

			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("sumairaambreen15@gmail.com");
			passwordResetEmail.setTo(user.getEmail());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText(
										"To reset your password, click the link below:\n" 
										+ appUrl
										+ "/ForgotPassword/resetPassword/" 
										+ user.getResetToken());

			emailService.sendEmail(passwordResetEmail);

			return "successMessageA password reset link has been sent to\n" 
			+ userEmail;
		}

	} 
	public void ResetPassword(String token,String newPassword) 
	{

  	User existing =userRepository.findUserByResetToken(token).get();
  	existing.setPassword(null);
  	existing.setResetToken(null);
  	existing.setPassword(Encoder.encode(newPassword));
		userRepository.save(existing);
	}
	
	@PutMapping("/resetPassword/{token}")
	public String setNewPassword( @PathVariable String token , 
			@RequestBody PasswodChanger2 passwodChanger  ) {

			 ResetPassword(token, passwodChanger.getNewPassword());
			 return "Your Password and Token Reset Successfully!";
   }
}
