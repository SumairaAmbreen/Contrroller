package com.Hostel.Hostel_Management_System.Controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hostel.Hostel_Management_System.PayLoad.MessageResponse;
import com.Hostel.Hostel_Management_System.PayLoad.RegistrationRequest;
import com.Hostel.Hostel_Management_System.Repository.Role_Repository;
import com.Hostel.Hostel_Management_System.Repository.User_Repository;
import com.Hostel.Hostel_Management_System.Service.Service_HMS;
import com.Hostel.Hostel_Management_System.model1.ERole;
import com.Hostel.Hostel_Management_System.model1.Role;
import com.Hostel.Hostel_Management_System.model1.User;


@RestController
@RequestMapping("/SignUp")
@CrossOrigin(origins = "*")
@Validated
public class SignUp_Controller {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	User_Repository userRepository;

	@Autowired
	Role_Repository roleRepository;
	
	@Autowired
	private PasswordEncoder Encoder;
	
	@Autowired
	private Service_HMS serviceHms;
	
	@PostMapping("/Online")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest Request) {
		if (userRepository.existsByUsername(Request.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: User Name is already taken!"));
		}

		if (userRepository.existsByEmail(Request.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		// To Create new user's account
		User user = new User (Request.getUsername(),
				Request.getEmail(),
				Encoder.encode(Request.getPassword()));
				

		Set<String> strRoles = Request.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error:Student Role is not found."));
			roles.add(studentRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "student":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error:Admin Role is not found."));
					roles.add(adminRole);
				break;
				default:
					Role staffRole = roleRepository.findByName(ERole.ROLE_STUDENT)
							.orElseThrow(() -> new RuntimeException("Error:Student Role is not found."));
					roles.add(staffRole);
				}
			});
		}
		user.setRole_id(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User SignUp successfully"));
	}

	@PostMapping("/FromPanel")  
	@PreAuthorize("hasRole('ADMIN')")
	   public ResponseEntity<?>addUser(@RequestBody User user) {
		   if (userRepository.existsByUsername(user.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: User Name is already taken!"));
			}

			if (userRepository.existsByEmail(user.getEmail())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Email is already in use!"));
			}
		    serviceHms.saveUser(user);
		   return ResponseEntity.ok(new MessageResponse("User SignUp successfully"));
	   }
}
