package com.theironyard.invoicify.models;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.meanbean.test.BeanTester;

public class UserRoleTests {

	@Test
	public void test_parameterized_constructor() {
		User user = new User();
		
		UserRole role = new UserRole("TESTER", user);
		
		assertThat(role.getId()).isNull();
		assertThat(role.getUser()).isEqualTo(user);
		assertThat(role.getName()).isEqualTo("TESTER");
	}
 
	@Test
	public void test_getters_and_setters() {
		new BeanTester().testBean(UserRole.class);
	}
	
}
