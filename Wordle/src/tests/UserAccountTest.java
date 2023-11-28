package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.AuthenticationService;

class UserAccountTest {

	@Test
	void test() {
		AuthenticationService service=new AuthenticationService();
		//create new account.
		assertEquals(0, service.createAccount("Umid", "123"));

		// return 0 since the account already exists.
		assertEquals(0, service.createAccount("Umid", "123"));
		
	}
	
//	@Test
//	void test2()
//	{
//		AuthenticationService service=new AuthenticationService();
//		
//		assertEquals(1, service.createAccount("Umid", "123"));
//		//say user does not exist
//		assertEquals(1, service.logIn("Lolo", "124"));
//		// wrong password
//		assertEquals(2, service.logIn("Umid", "124"));
//		//login successfully
//		assertEquals(3, service.logIn("Umid", "123"));
//		//someone else is logged in
//		assertEquals(0, service.logIn("Umid", "123"));
//		//log out
//		assertEquals(1, service.logOut());
//		//no one to log out
//		assertEquals(0, service.logOut());
//	}
	
}
