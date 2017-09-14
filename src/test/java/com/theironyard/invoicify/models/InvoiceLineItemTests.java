package com.theironyard.invoicify.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class InvoiceLineItemTests { 

	private InvoiceLineItem invoice;
	
	@Before
	public void setup() {
		invoice = new InvoiceLineItem();
	}    
	
	@Test
	public void test_getters_and_setters() {
		Configuration configuration = new ConfigurationBuilder()
			.ignoreProperty("createdOn")
			.ignoreProperty("billingRecord")
			.build();
		new BeanTester().testBean(InvoiceLineItem.class, configuration);
	}
	 
	@Test
	public void test_billing_records() {
		BillingRecord record = new RateBasedBillingRecord();
		invoice.setBillingRecord(record);
		
		assertThat(invoice.getBillingRecord()).isSameAs(record);
	}
	 
	@Test
	public void test_createdOn_gets_and_sets() {
		Date date = Date.valueOf("2017-02-02");
		invoice.setCreatedOn(date);
		
		Date actual = invoice.getCreatedOn();
		
		assertThat(actual).isEqualTo(date);
	}
} 
