package com.learning3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.learning3.dao.UserDAO;
import com.learning3.model.Region;
import com.learning3.model.User;
import com.learning3.model.UsersReport;

@Controller
@RequestMapping("/reports")
public class ReportsController {
	
	@Autowired
	private UserDAO userDAO;	
		
	@RequestMapping(value = "/usersreport", method = RequestMethod.GET)
	public ModelAndView usersReport() {
		System.out.println("In usersReport");
		
		UsersReport usersReport = this.userDAO.prepareUsersReport();
		ModelAndView model = new ModelAndView("usersreport");
		model.addObject("usersreport", usersReport);		
		return model;
	}
	
	@RequestMapping(value = "/testreport", method = RequestMethod.GET)
	public ModelAndView testReport() {
		System.out.println("In usersReport");
		
		this.userDAO.preparePartialReport();
		ModelAndView model = new ModelAndView("usersreport");
		model.addObject("usersreport", new UsersReport());		
		return model;
	}

}


