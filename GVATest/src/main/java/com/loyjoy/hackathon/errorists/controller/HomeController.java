/**
 * 
 */
package com.loyjoy.hackathon.errorists.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author AnkitA
 *
 */
@Controller
public class HomeController {

	@RequestMapping("/voodoo")
	public String fetchResults() {
		return "/jsp/upload.jsp";
	}
}
