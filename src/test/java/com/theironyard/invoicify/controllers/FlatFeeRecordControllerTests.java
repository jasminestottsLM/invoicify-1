package com.theironyard.invoicify.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.FlatFeeBillingRecord;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;

public class FlatFeeRecordControllerTests {

	private FlatFeeBillingRecordController controller; 
	private BillingRecordRepository recordRepository;
	private CompanyRepository companyRepository;
	private Authentication auth;
	private User user;
	
	@Before 
	public void setUp() {
		user = new User();
		auth = mock(Authentication.class);
		recordRepository = mock(BillingRecordRepository.class);
		companyRepository = mock(CompanyRepository.class);
		
		when(auth.getPrincipal()).thenReturn(user);
	}
	 
	@Test 
	public void test_create() {
		FlatFeeBillingRecord record = new FlatFeeBillingRecord();
		long clientId = 3l;	
		Company company = new Company();
		when(companyRepository.findOne(clientId)).thenReturn(company);
		 
		ModelAndView actual = controller.create(record, clientId, auth); 
		 
		assertThat(actual.getViewName()).isEqualTo("redirect:/billing-records");
	}
}
