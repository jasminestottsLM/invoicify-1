package com.theironyard.invoicify.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.Invoice;
import com.theironyard.invoicify.repositories.CompanyRepository;
import com.theironyard.invoicify.repositories.InvoiceRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private CompanyRepository companyRepository;
	
	public AdminController(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	@GetMapping("")
	public ModelAndView showAdminPage() {
		ModelAndView mv = new ModelAndView("admin/companies");
		List<Company> companies = companyRepository.findAll(sortByName());
		mv.addObject("companies", companies);
		return mv;
	}

	@PostMapping("create")
	public ModelAndView createCompany(Company company) {
		ModelAndView mv = new ModelAndView("redirect:/admin");
		companyRepository.save(company);
		return mv;
	}
	
	private Sort sortByName() {
		return new Sort(Sort.Direction.ASC, "name");
	}
}
