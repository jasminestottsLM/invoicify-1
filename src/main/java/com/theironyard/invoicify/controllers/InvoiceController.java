package com.theironyard.invoicify.controllers;

import java.sql.Date; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.theironyard.invoicify.models.BillingRecord;
import com.theironyard.invoicify.models.Company;
import com.theironyard.invoicify.models.FlatFeeBillingRecord;
import com.theironyard.invoicify.models.Invoice;
import com.theironyard.invoicify.models.InvoiceLineItem;
import com.theironyard.invoicify.models.User;
import com.theironyard.invoicify.repositories.BillingRecordRepository;
import com.theironyard.invoicify.repositories.CompanyRepository;
import com.theironyard.invoicify.repositories.InvoiceLineItemRepository;
import com.theironyard.invoicify.repositories.InvoiceRepository;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

	private CompanyRepository companyRepository;
	private BillingRecordRepository recordsRepository;
	private InvoiceRepository invoiceRepository;
	private InvoiceLineItemRepository invoiceLineItemRepository;
	
	public InvoiceController(CompanyRepository companyRepository, InvoiceRepository invoiceRepository, BillingRecordRepository recordsRepository, InvoiceLineItemRepository invoiceLineItemRepository) {
		this.companyRepository = companyRepository; 
		this.invoiceRepository = invoiceRepository;
		this.recordsRepository = recordsRepository;
		this.invoiceLineItemRepository = invoiceLineItemRepository;
	}
	
	@GetMapping("home")
	public ModelAndView goHome() {
		ModelAndView mv = new ModelAndView("redirect:/home/default");
		return mv;
	}
	
	@GetMapping("") 
	public ModelAndView list(Authentication auth) {
		User user = (User) auth.getPrincipal();
		ModelAndView mv = new ModelAndView("invoices/list");
		List<Invoice> invoices = invoiceRepository.findAll();
		mv.addObject("user", user);
		mv.addObject("invoices", invoiceRepository.findAll());
		mv.addObject("showTable", invoices.size() > 0);
		return mv;  
	}
	
	@GetMapping("new")
	public ModelAndView newInvoice() {
		ModelAndView mv = new ModelAndView("invoices/step-1");
		mv.addObject("companies", companyRepository.findAll());
		return mv;
	} 
	
	@PostMapping("new")
	public ModelAndView selectRecords(long clientId) {
		Company client = companyRepository.findOne(clientId);
		
		ModelAndView mv = new ModelAndView("invoices/step-2"); 
		List<Invoice> invoices = invoiceRepository.findByCompanyId(clientId);
		mv.addObject("client", client.getName());
		mv.addObject("clientId", clientId);

		mv.addObject("records", recordsRepository.findByClientIdAndLineItemIsNull(clientId));
		return mv;
	}
	
	@PostMapping("create")
	public ModelAndView createInvoice(Invoice invoice, long clientId, long[] recordIds, Authentication auth) {
		User creator = (User) auth.getPrincipal();
		List<BillingRecord> records = recordsRepository.findByIdIn(recordIds);
		long nowTime = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(nowTime);
		ModelAndView mv = new ModelAndView("redirect:/invoices");
		mv.addObject("user", creator);
		
		List<InvoiceLineItem> items = new ArrayList<InvoiceLineItem>();
		for (BillingRecord record : records) {
			InvoiceLineItem lineItem = new InvoiceLineItem();
			lineItem.setBillingRecord(record);
			lineItem.setCreatedBy(creator);
			lineItem.setCreatedOn(now);
			lineItem.setInvoice(invoice);
			items.add(lineItem);
		} 
		
		invoice.setLineItems(items);
		invoice.setCreatedBy(creator);
		invoice.setCompany(companyRepository.findOne(clientId));
		invoice.setCreatedOn(now);
		invoice.setInvoiceNumber(invoice.getInvoiceNumber());
		
		invoiceRepository.save(invoice);
		
		return mv;
	}
	
}


 














