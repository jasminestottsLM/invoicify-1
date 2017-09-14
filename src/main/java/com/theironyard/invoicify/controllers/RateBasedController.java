package com.theironyard.invoicify.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.RateBasedBillingRecord;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;

@Repository
@RequestMapping("/billing-records/rate-based")
public class RateBasedController {

	private BillingRecordRepository recordRepository;
	private CompanyRepository companyRepository;
	
	public RateBasedController(BillingRecordRepository recordRepository, CompanyRepository companyRepository) {
		this.recordRepository = recordRepository;
		this.companyRepository = companyRepository;
	}
	
	@PostMapping("")
	public ModelAndView create(RateBasedBillingRecord record, long clientId, Authentication auth) {
		User user = (User) auth.getPrincipal();
		Company client =companyRepository.findOne(clientId);
		record.setClient(client);
		record.setCreatedBy(user);
		recordRepository.save(record);
		return new ModelAndView("redirect:/billing-records");
	}
}
