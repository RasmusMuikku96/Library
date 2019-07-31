package com.rasmus.app.Library.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoanTest {
	
	private LibraryUser loanTaker;
	private Book book;
	private Loan testLoan;
	private long loanId;
	
	@Before
	public void init(){
		loanTaker = new LibraryUser(LocalDate.parse("2019-07-31"), "Test Testsson");
		book = new Book("Test Book", 30, BigDecimal.valueOf(10), "Test Description");
		testLoan = new Loan(loanTaker, book, LocalDate.parse("2019-07-31"));
		loanId = testLoan.getLoanId();
	}
	
	
	@Test
	public void test_create_loan_SUCCESS(){
		assertEquals(loanId, testLoan.getLoanId());
		assertEquals(book, testLoan.getBook());
		assertEquals(LocalDate.parse("2019-07-31"), testLoan.getLoanDate());
		assertEquals(loanTaker, testLoan.getLoanTaker());
		assertFalse(testLoan.getBook().isAvailable());
		assertFalse(testLoan.isTerminated());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_add_unavailable_book_throws_illegalarg(){
		Book unavailableBook = new Book("OCP", 30, BigDecimal.valueOf(10), "Boring Book");
		unavailableBook.setAvailable(false);
		
		new Loan(loanTaker, unavailableBook, LocalDate.now());
		}
	
	@Test
	public void test_loan_is_not_overdue(){
		assertFalse(testLoan.isOverDue());
	}
	
	@Test
	public void test_loan_is_overdue(){
		Book book = new Book("OCP", 30, BigDecimal.valueOf(10), "Boring Book");
		Loan testLoan = new Loan(loanTaker, book, LocalDate.now().minusDays(31));

		assertTrue(testLoan.isOverDue());
	}
	
	@Test
	public void test_total_fine_is_50(){
		BigDecimal expectedFine = BigDecimal.valueOf(50);
		Book overDueBook = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring Book");
		
		LocalDate daysago = LocalDate.now().minusDays(20);
		
		Loan testLoan = new Loan(loanTaker, overDueBook, daysago);
		
		assertEquals(expectedFine, testLoan.getFine());
	}
	
	//@Test
	public void test_fine_is_0(){
		BigDecimal expectedFine = BigDecimal.ZERO;
		Book book = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring Book");
		
		LocalDate daysago = LocalDate.now().minusDays(15);
		
		Loan testLoan = new Loan(loanTaker, book, daysago);
		
		assertEquals(expectedFine, testLoan.getFine());
		
	}
	
	@Test
	public void test_extendLoan_return_true(){
		Book book = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring Book");
		Loan testLoan = new Loan(loanTaker, book, LocalDate.now().minusDays(15));
		
		assertTrue(testLoan.extendLoan());		
		
	}
	
	@Test
	public void test_extendLoan_return_false(){
		Book book = new Book("OCP", 15, BigDecimal.valueOf(10), "Boring Book");
		Loan testLoan = new Loan(loanTaker, book, LocalDate.now().minusDays(20));
		
		assertFalse(testLoan.extendLoan());
		
	}
	
	public void test_extendLoan_on_reserved_book_returns_false(){
		Book reservedBook = new Book("Harry Potter", 30, BigDecimal.valueOf(20), "Popular Book");
		Loan theLoan = new Loan(loanTaker, reservedBook, LocalDate.now().minusDays(15));
		reservedBook.setReserved(true);
		
		assertFalse(theLoan.extendLoan());
	}
	
	@Test
	public void test_return_book_loanIsTerminted_and_bookIsAvailable(){
		testLoan.returnBook();
		assertTrue(testLoan.isTerminated());
		assertTrue(book.isAvailable());
	}
	
}
