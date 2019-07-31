package com.rasmus.app.Library.model;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class LibraryUserTest {

	@Test
	public void test_create_LibraryUser_SUCCESS() {
		int expectedUserId = 1;
		LocalDate expectedRegDate = LocalDate.parse("2019-07-31");
		String expectedName = "Test Testsson";
		
		LibraryUser testUser = new LibraryUser(LocalDate.parse("2019-07-31"), "Test Testsson");
		
		assertEquals(expectedUserId, testUser.getUserId());
		assertEquals(expectedRegDate, testUser.getRegDate());
		assertEquals(expectedName, testUser.getName());
	}
	
}
