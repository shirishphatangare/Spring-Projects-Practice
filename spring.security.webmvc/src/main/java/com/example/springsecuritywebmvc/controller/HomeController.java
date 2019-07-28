package com.example.springsecuritywebmvc.controller;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping(value = "/home")
public class HomeController {
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	//String text;
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView showHomePage() {
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("text", "Hello World Shirish");
		return mav;
	}
	
	@RequestMapping(value = "/CTC", method = RequestMethod.GET)
	public ModelAndView showCTCTime() {
		ModelAndView mav = new ModelAndView("time");
		mav.addObject("time", "CTC Time is: " + ZonedDateTime.now());
		return mav;
	}
	
	@RequestMapping(value = "/IST", method = RequestMethod.GET)
	public ModelAndView showISTTime() {
		ModelAndView mav = new ModelAndView("time");
		mav.addObject("time", "IST Time is: " + ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
		return mav;
	}
	
	@RequestMapping({"/login","/"})
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping({"/accessDeniedPage"})
	public String showAccessDeniedPage() {
		return "accessDeniedPage";
	}
	
	// For custom logout filter to work , below method is necessary
	@RequestMapping(value="/customlogout", method = RequestMethod.POST)
    public void logOut(){
    }

}
