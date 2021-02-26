package com.Hostel.Hostel_Management_System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.Hostel.Hostel_Management_System.Model.Fee_Challan;
import com.Hostel.Hostel_Management_System.Model.Hostel;
import com.Hostel.Hostel_Management_System.Model.Room;
import com.Hostel.Hostel_Management_System.Model.Room_Status;
import com.Hostel.Hostel_Management_System.Model.Student;
import com.Hostel.Hostel_Management_System.Model.Student_Account;
import com.Hostel.Hostel_Management_System.Model.Student_Room;
import com.Hostel.Hostel_Management_System.Model.Student_Status;
import com.Hostel.Hostel_Management_System.Model.User_Status;
import com.Hostel.Hostel_Management_System.Model.Warden;
import com.Hostel.Hostel_Management_System.Repository.User_Repository;
import com.Hostel.Hostel_Management_System.Service.Service_HMS;
import com.Hostel.Hostel_Management_System.model1.Role;
import com.Hostel.Hostel_Management_System.model1.User;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/HMS")
public class Controller_HMS {

		@Autowired
		private Service_HMS serviceHms;
//_____________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR FEE_CHALLAN-ENTITY----------------------------------------
//_____________________________________________________________________________________________________________

	    @GetMapping("/getfeechallans")
	    @PreAuthorize("hasRole('STAFF')")
	    public List<Fee_Challan> findAllFeechallans() {
	        return serviceHms.getAllfeechallans();
	    }

	    @GetMapping("/getFeechallan/{id}")
	    @PreAuthorize("hasRole('STAFF')")
	    public Fee_Challan findFeechallanById(@PathVariable int id) {
	        return serviceHms.getfeechallanById(id);
	    }

	    @PutMapping("/updateFeechallan")
	    @PreAuthorize("hasRole('STAFF')")
	    public Fee_Challan updateFeechallan(@RequestBody Fee_Challan feechallan) {
	        return serviceHms.updateFeeChallan(feechallan);
	    }
	    
	    @DeleteMapping("/deleteFeechallan/{id}")
	    @PreAuthorize("hasRole('STAFF')")
	    public String deleteFeechallan(@PathVariable int id) {
	        return serviceHms.deleteFeechallan(id);
	    }
	    
 //________________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR HOSTEL-ENTITY-------------------------------------------------
//_________________________________________________________________________________________________________________
	
		@PostMapping("/addHostel")
		@PreAuthorize("hasRole('ADMIN')")
	    public Hostel addHostel(@RequestBody Hostel hostel) {
	        return serviceHms.saveHostel(hostel);
	    }

	    @PostMapping("/addHostels")
	    @PreAuthorize("hasRole('ADMIN')")
	    public List<Hostel> addAllHostels(@RequestBody List<Hostel> hostel) {
	        return serviceHms.saveAllHostel(hostel);
	    }

	    @GetMapping("/getHostels")
	    @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	    public List<Hostel> findAllHostels() {
	        return serviceHms.getAllhostels();
	    }
	
	    @GetMapping(path="{id}")
	    @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	    public Hostel findHostelById(@PathVariable Integer id) {
	        return serviceHms.getAllhostels().stream().filter(hostel->id.equals(hostel.getHostel_id()))
	        		.findFirst().orElseThrow(()->new IllegalStateException("Hostel not Founds"));
	    }
	    @PutMapping("/updateHostel")
	    @PreAuthorize("hasRole('ADMIN')")
	    public Hostel updateHostels(@RequestBody Hostel hostel) {
	        return serviceHms.updateHostel(hostel);
	    }
	    
	    @DeleteMapping("/deleteHostel/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public String deleteHostel(@PathVariable int id) {
	        return serviceHms.deleteHostel(id);
	    }
	    
//________________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR ROLE-ENTITY-------------------------------------------------
//_________________________________________________________________________________________________________________
	    @PostMapping("/addRole")
	    @PreAuthorize("hasRole('ADMIN')")
	    public Role addRole(@RequestBody Role role) {
	        return serviceHms.saveRole(role);
	    }

	    @PostMapping("/addRoles")
	    @PreAuthorize("hasRole('ADMIN')")
	    public List<Role> addAllRoles(@RequestBody List<Role> role) {
	        return serviceHms.saveAllRoles(role);
	    }

	    @GetMapping("/getRoles")
	    @PreAuthorize("hasRole('ADMIN')")
	    public List<Role> findAllRoles() {
	        return serviceHms.getAllroles();
	    }

	    @GetMapping("/getRole/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public Role findRoleById(@PathVariable int id) {
	        return serviceHms.getRoleById(id);
	    }

		@PutMapping("/updateRole") 
		 @PreAuthorize("hasRole('ADMIN')")
		public Role updateRoles(@RequestBody Role role) {
			return serviceHms.updateRole(role); 
		}
		 
	    
	    @DeleteMapping("/deleteRole/{id}")
	    @PreAuthorize("hasRole('ADMIN')")
	    public String deleteRole(@PathVariable int id) {
	        return serviceHms.deleteRole(id);
	    }
//_______________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR ROOM_STATUS-ENTITY--------------------------------------------------
//________________________________________________________________________________________________________________
	   @PostMapping("/addRoomStatus")
	   @PreAuthorize("hasRole('ADMIN')")
	   public Room_Status addRoomStatus(@RequestBody Room_Status roomstatus) {
		   return serviceHms.saveRoomStatus(roomstatus);
	   }

	   @PostMapping("/addRoomStatuses")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<Room_Status> addAllRoomStatus(@RequestBody List<Room_Status> roomstatus) {
		   return serviceHms.saveAllroomStatus(roomstatus);
	   }

	   @GetMapping("/getRoomStatuses")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public List<Room_Status> findAllRoomStatuses() {
		   return serviceHms.getAllroomStatus();
	   }

	   @GetMapping("/getRoomStatus/{id}")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public Room_Status findRoomStatusById(@PathVariable int id) {
		   return serviceHms.getRoomStatusById(id);
	   }

	   @PutMapping("/updateRoomStatus")
	   @PreAuthorize("hasRole('ADMIN')  or hasRole('STAFF')")
	   public Room_Status updateRoomStatus(@RequestBody Room_Status roomstatus) {
		   return serviceHms.updateRoomStatus(roomstatus);
	   }    
	   @DeleteMapping("/deleteRoomStatus/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public String deleteRoomStatus(@PathVariable int id) {
		   return serviceHms.deleteRoomStatus(id);
	   }
//_______________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR ROOM-ENTITY--------------------------------------------------
//________________________________________________________________________________________________________________
	   @PostMapping("/addRoom")
	   @PreAuthorize("hasRole('ADMIN')")
	   public Room addRoom(@RequestBody Room room) {
		   return serviceHms.SaveRoom(room);
	   }

	   @PostMapping("/addRooms")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<Room> addAllRooms(@RequestBody List<Room> rooms) {
		   return serviceHms.saveAllRooms(rooms);
	   }

	   @GetMapping("/getRooms")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public List<Room> findAllRooms() {
		   return serviceHms.getAllRooms();
	   }

	   @GetMapping("/getRoom/{id}")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public Room findRoomById(@PathVariable int id) {
		   return serviceHms.getRoomById(id);
	   }

	   @PutMapping("/updateRoom")
	   @PreAuthorize("hasRole('ADMIN')")
	   public Room updateRoom(@RequestBody Room room) {
		   return serviceHms.updateRoom(room);
	   }    
	   @DeleteMapping("/deleteRoom/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public String deleteRoom(@PathVariable int id) {
		   return serviceHms.deleteRoom(id);
	   }
//_______________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR STUDENT_ACCOUNT-ENTITY--------------------------------------------------
//________________________________________________________________________________________________________________
	   @PostMapping("/addStudentAccount")
	   @PreAuthorize("hasRole('ADMIN')  or hasRole('STAFF')")
	   public Student_Account addStudentAccount(@RequestBody Student_Account studentAccount) {
		   return serviceHms.saveStudentAccount(studentAccount);
	   }

	   @PostMapping("/addStudentAccounts")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	   public List<Student_Account> addAllStudentAccounts(@RequestBody List<Student_Account> studentAccount) {
		   return serviceHms.saveAllStudentAccount(studentAccount);
	   }

	   @GetMapping("/getStudentAccounts")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public List<Student_Account> findAllStudentAccounts() {
		   return serviceHms.getAllStudentAccounts();
	   }

	   @GetMapping("/getStudentAccount/{id}")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public Student_Account findStudentAccountById(@PathVariable int id) {
		   return serviceHms.getAstudentAccountById(id);
	   }

	   @PutMapping("/updateStudentAccount")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	   public Student_Account updateRoom(@RequestBody Student_Account studentAccount) {
		   return serviceHms.updateStudentAccount(studentAccount);
	   }    
	   @DeleteMapping("/deleteStudentAccount/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public String deleteStudentAccount(@PathVariable int id) {
		   return serviceHms.deleteStudentAccount(id);
	   }
//_______________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR STUDENT_ROOM-ENTITY--------------------------------------------------
//________________________________________________________________________________________________________________
	   @PostMapping("/addStudentRoom")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	   public Student_Room addStudentRoom(@RequestBody Student_Room studentRoom) {
		   return serviceHms.saveStudentRoom(studentRoom);
	   }

	   @PostMapping("/addStudentRooms")
	   @PreAuthorize("hasRole('ADMIN')  or hasRole('STAFF')")
	   public List<Student_Room> addAllStudentRooms(@RequestBody List<Student_Room> studentRoom) {
		   return serviceHms.saveAllStudentRoom(studentRoom);
	   }

	   @GetMapping("/getStudentRooms")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public List<Student_Room> findAllStudentRooms() {
		   return serviceHms.getAllStudentRooms();
	   }

	   @GetMapping("/getStudentRoom/{id}")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public Student_Room findStudentRoomById(@PathVariable int id) {
		   return serviceHms.getAstudentRoomById(id);
	   }
		 // @PutMapping("/updateStudentRoom") public Student_Room updateRoom(@RequestBody
		  //Student_Room studentRoom) { return serviceHms.updateStudentRoom(studentRoom);
		  //}
		     
	   @DeleteMapping("/deleteStudentRoom/{id}")
	   @PreAuthorize("hasAnyRole('ADMIN')")
	   public String deleteStudentRoom(@PathVariable int id) {
		   return serviceHms.deleteStudentRoom(id);
	   }
//_______________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR STUDENT_STATUS-ENTITY--------------------------------------------------
//________________________________________________________________________________________________________________
	   @PostMapping("/addStudentStatus")
	   @PreAuthorize("hasAnyRole('STAFF')")
	   public Student_Status addStudentStatus(@RequestBody Student_Status studentStatus) {
		   return serviceHms.saveStudentStatus(studentStatus);
	   }

	   @PostMapping("/addStudentStatuses")
	   @PreAuthorize("hasAnyRole('STAFF')")
	   public List<Student_Status> addAllStudentStatus(@RequestBody List<Student_Status> studentStatus) {
		   return serviceHms.saveAllStudentStatus(studentStatus);
	   }

	   @GetMapping("/getStudentStatus")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public List<Student_Status> findAllStudentStatus() {
		   return serviceHms.getAllStudentStatus();
	   }

	  @GetMapping("/getStudentStatus/{id}"  )
	  @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public Student_Status findStudentStatusById(@PathVariable int id ) {
		   return serviceHms.getstudentStatusById(id);
	   }

	   @PutMapping("/updateStudentStatus")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	   public Student_Status updateStudentStatus(@RequestBody Student_Status studentStatus) {
		   return serviceHms.updateStudentStatus(studentStatus);
	   }    
	   @DeleteMapping("/deleteStudentStatus/{id}")
	   @PreAuthorize("hasRole('ADMIN')  or hasRole('STAFF')")
	   public String deleteStudentStatus(@PathVariable int id) {
		   return serviceHms.deleteStudentStatus(id);
	   }
//_______________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR STUDENT-ENTITY----------------------------------------------
//________________________________________________________________________________________________________________

	   @GetMapping("/getStudents")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public List<Student> findAllStudent() {
		   return serviceHms.getAllStudent();
	   }

	   @GetMapping("/getStudent/{id}")
	   @PreAuthorize("hasRole('ADMIN') or hasRole('WARDEN')  or hasRole('STAFF')")
	   public Student findStudentById(@PathVariable int id) {
		   return serviceHms.getstudentById(id);
	   }

	   @PutMapping("/updateStudent")
	   @PreAuthorize("hasRole('ADMIN')")
	   public Student updateStudent(@RequestBody Student student) {
		   return serviceHms.updateStudent(student);
	   }    
	   @DeleteMapping("/deleteStudent/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public String deleteStudent(@PathVariable int id) {
		   return serviceHms.deleteStudent(id);
	   }
//_______________________________________________________________________________________________________________
	    
//------------------------------------CONTROLLER FOR USER-ENTITY--------------------------------------------------
//________________________________________________________________________________________________________________
	   @Autowired
	   User_Repository userRepository;
	   
	   @PostMapping("/addUsers")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<User> addAllUsers(@RequestBody List<User> user) {
		   return serviceHms.saveAllUser(user);
	   }

	   @GetMapping("/getUsers")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<User> findAllUser() {
		   return serviceHms.getAllUser();
	   }
	   @GetMapping("/getUser/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public User findUserById(@PathVariable long id) {
		   return serviceHms.getUserById(id);
	   }
		
	   @PutMapping("/updateUser") 
	   @PreAuthorize("hasRole('ADMIN')")
	   public User updateUser(@RequestBody User user) 
	   {
		  return serviceHms.updateUser(user); 
	   }
		 

	   @DeleteMapping("/deleteUser/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public String deleteUser(@PathVariable long id) {
		   return serviceHms.deleteUser(id);	
	   }
//_______________________________________________________________________________________________________________
	 	    
//------------------------------------CONTROLLER FOR USER_Status-ENTITY--------------------------------------------------
//________________________________________________________________________________________________________________
	   @PostMapping("/addUserStatus")
	   @PreAuthorize("hasRole('ADMIN')")
	   public User_Status addUserStatus(@RequestBody User_Status userStatus) {
		   return serviceHms.saveUserStatus(userStatus);
	   }

	   @PostMapping("/addUserStatuses")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<User_Status> addAllUserStatuses(@RequestBody List<User_Status> userStatus) {
		   return serviceHms.saveAllUserStatus(userStatus);
	   }

	   @GetMapping("/getUserStatuses")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<User_Status> findAllUserStatus() {
		   return serviceHms.getAllUserStatus();
	   }
	   @GetMapping("/getUserStatus/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public User_Status findUserStatusById(@PathVariable int id) {
		   return serviceHms.getUserStatusById( id);
	   }

	   @PutMapping("/updateUserStatus")
	   @PreAuthorize("hasRole('ADMIN')")
	   public User_Status updateUserStatus(@RequestBody User_Status userStatus) {
		   return serviceHms.updateUserStatus(userStatus);
	   }    
	   @DeleteMapping("/deleteUserStatus/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public String deleteUserStatus(@PathVariable int id) {
		   return serviceHms.deleteUserStatus(id);	
	   }
	 //_______________________________________________________________________________________________________________
	    
	 //------------------------------------CONTROLLER FOR USER_Status-ENTITY-------------------------------------------
	 //________________________________________________________________________________________________________________
	   @PostMapping("/addWarden")
	   @PreAuthorize("hasRole('ADMIN')")
	   public Warden addWarden (@RequestBody Warden warden) 
	   {
		   return serviceHms.saveWarden(warden);
	   }
	   
	   @PostMapping("/addWardens")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<Warden> addAllWarden(@RequestBody List<Warden> warden) {
		   return serviceHms.saveAllWardens(warden);
	   } 
	   @GetMapping("/getWardens")
	   @PreAuthorize("hasRole('ADMIN')")
	   public List<Warden> getWardens() {
		   
		   return serviceHms.getAllWarden();
	   }
	   @GetMapping("/getWarden/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public Warden getWardens(@PathVariable int id) {
		   
		   return serviceHms.getWardenById(id);
	   }
	   @PutMapping("/updateWarden")
	   @PreAuthorize("hasRole('ADMIN')")
	   public Warden UpdateWarden(@RequestBody Warden warden ) {
		   return serviceHms.updateWarden(warden);
	   }
	   @DeleteMapping("/deleteWarden/{id}")
	   @PreAuthorize("hasRole('ADMIN')")
	   public String deleteWarden(@PathVariable int id) {
		   return serviceHms.deleteWarden(id);
	   }
	   
}
