package com.Hostel.Hostel_Management_System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hostel.Hostel_Management_System.Repository.Role_Repository;
import com.Hostel.Hostel_Management_System.Repository.User_Repository;
import com.Hostel.Hostel_Management_System.model1.PasswodChanger;
import com.Hostel.Hostel_Management_System.model1.User;


@RestController
@RequestMapping("/ChangePassword")
@CrossOrigin(origins = "*")
@Validated
public class ChangePassword_Controller {
	
	
	@Autowired
	User_Repository userRepository;

	@Autowired
	Role_Repository roleRepository;

	@Autowired
	PasswordEncoder Encoder;

	public User ChangePassword(String email, String oldPassword, String newPassword) {

      	User existing =userRepository.findByEmail(email).get();
  		if (Encoder.matches(oldPassword, existing.getPassword()) == true) {
  			existing.setPassword(Encoder.encode(newPassword));
  		} else {
  			System.out.println("Sorry invalid Password.");
  		}
  		userRepository.save(existing);
  		return existing;
  	}
	@PutMapping("/changePassword/{email}")
	@PreAuthorize("hasAnyRole('STAFF') or hasRole('ADMIN')")
	public User changePassword(@PathVariable String email,
							   @RequestBody PasswodChanger passwodChanger)
	{
		return ChangePassword(email, 
							  passwodChanger.getOldPassword(), 
							  passwodChanger.getNewPassword());
		
	}
}
