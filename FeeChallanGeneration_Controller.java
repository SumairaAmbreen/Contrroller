package com.Hostel.Hostel_Management_System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hostel.Hostel_Management_System.Model.Fee_Challan;
import com.Hostel.Hostel_Management_System.Service.Service_HMS;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/GenerateFeeChallan")
public class FeeChallanGeneration_Controller {
	
	@Autowired
	private Service_HMS serviceHms;
	
	@PostMapping( "/addFeechallan")
	@PreAuthorize("hasRole('STAFF')")
    public Fee_Challan addfeechallan(@RequestBody Fee_Challan feechallan) {
        return serviceHms.saveFeechallan(feechallan);
    }

    @PostMapping("/addfeechallans")
    @PreAuthorize("hasRole('STAFF')")
    public List<Fee_Challan> addAllfeechallans(@RequestBody List<Fee_Challan> feechallan) {
        return serviceHms.saveAllFeechallan(feechallan);
    }
}
