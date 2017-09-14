package com.theironyard.invoicify.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class InvoiceModelTest {
 
	private Invoice invoice;
	
	@Before
	public void setup() {
		invoice = new Invoice();
	}   
	 
	@Test
	public void test_getters_and_setters() {
		Configuration configuration = new ConfigurationBuilder()
			.ignoreProperty("createdOn")
			.build();
		new BeanTester().testBean(Invoice.class, configuration);
	}

	@Test 
	public void test_createdOn_is__null_by_default() {
		Date actual = invoice.getCreatedOn();
		
		assertThat(actual).isNull();
	}  
}
