package com.Hostel.Hostel_Management_System.Controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Hostel.Hostel_Management_System.Model.Student;
import com.Hostel.Hostel_Management_System.Service.Service_HMS;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/StudentRegistration")
public class StudentRegistration_Controller {
	
	
	@Autowired
	private Service_HMS serviceHms;
	 @PostMapping(value = "RegisterStudent"  ,consumes = {
				MediaType.APPLICATION_JSON_VALUE,
				MediaType.MULTIPART_FORM_DATA_VALUE} )
	 @PreAuthorize("hasRole('STUDENT')")
	 public Student save(@RequestPart  (value = "studentData",
	   						required = false) 
	   						Student student,  
	   						@RequestPart(value = "file")  
	   						MultipartFile file) throws IOException 
	   {
		   String destinationfilename="./StudentPicture/" 
							+new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ "."
							+file.getOriginalFilename()
							.substring(file.getOriginalFilename()
							.lastIndexOf(".") + 1);
		   	String studentPicture=new SimpleDateFormat("yyyyMMddHHmmssSSS")
	       					.format(new Date())
	       					+ "."
	       					+file.getOriginalFilename()
	       					.substring(file.getOriginalFilename()
	       					.lastIndexOf(".") + 1);		
		   	if (!file.isEmpty()) {
		   		student.setStudent_picture(studentPicture);;
		   	}
		   	try 
		   	{
		   		Files.copy(file.getInputStream(),
		   		Path.of(destinationfilename),
		   		StandardCopyOption.REPLACE_EXISTING);
		   	} catch (IOException e) 
		   	{
		   		throw new RuntimeException(e);
		   	}
		   		Student st =serviceHms.saveStudent(student);
		   		return st;
	   }
}
