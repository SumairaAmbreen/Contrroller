package com.Hostel.Hostel_Management_System.Controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Hostel.Hostel_Management_System.Security.JWT.JWTUtils;
import com.Hostel.Hostel_Management_System.Service.UserDetailsImpl;
import com.Hostel.Hostel_Management_System.PayLoad.JwtResponse;
import com.Hostel.Hostel_Management_System.PayLoad.LoginRequest;
import com.Hostel.Hostel_Management_System.Repository.Role_Repository;
import com.Hostel.Hostel_Management_System.Repository.User_Repository;

@RestController
@RequestMapping("/Login")
@CrossOrigin(origins = "*")
public class Login_Controller {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	User_Repository userRepository;

	@Autowired
	Role_Repository roleRepository;
	
	@Autowired
	JWTUtils jwtUtils;


	@PostMapping("/Login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody 
												LoginRequest loginRequest) 
	{

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), 
													loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwtToken, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	} 
	
}
