package com.example.creatingcontainer.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.creatingcontainer.Model.InternalDataModel;
import com.example.creatingcontainer.Service.InternalDataService;


@Controller
public class DashboardWebController {

	@Autowired
	InternalDataService internalDataService;
	
	@GetMapping("")
	public String index(Model model) {
		return "redirect:index";
	}
	
	@GetMapping("/index")
	public String configure(Model model) {
		model.addAttribute("internalData", internalDataService.getInternalDetails());
		System.out.println(internalDataService.getInternalDetails().getDeploymentId());
		return "index";
	}
 
	@GetMapping("/settings")
	public String settings(Model model) {
		InternalDataModel internalData = new InternalDataModel();
		model.addAttribute("internalData", internalData);
		return "settings";
	}
 
	@PostMapping("/settings")
	public String saveSettings(@ModelAttribute("internalData") InternalDataModel internalData) {
		internalDataService.saveInternalDataModel(internalData);
		return "redirect:index";
	}
	
	


}
