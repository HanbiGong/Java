/**********************************************
 Workshop # 3
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-12-01
 **********************************************/
package ca.senecacollege.application.workshop3.di;

import ca.senecacollege.application.workshop3.controllers.*;
import ca.senecacollege.application.workshop3.repositories.*;
import ca.senecacollege.application.workshop3.services.*;

public class AppInitializer {

    private final UserRepository userRepo = new UserRepository();
    private final LoanRepository loanRepo = new LoanRepository();
    private final LoanCalculation loanCalc = new FixedRateLoan();
    private final AmortizationService amortService = new AmortizationService(loanCalc);

    public AppInitializer() {
        var u1 = new ca.senecacollege.application.workshop3.models.User();
        u1.setUsername("test");
        u1.setPassword("1234");
        u1.setEmail("t@mail.com");
        userRepo.addUser(u1);

        var u2 = new ca.senecacollege.application.workshop3.models.User();
        u2.setUsername("demo");
        u2.setPassword("1111");
        u2.setEmail("d@mail.com");
        userRepo.addUser(u2);
    }

    // Returns controller instances with dependencies injected
    public Object getController(Class<?> cls) {

        if (cls == SignupController.class) {
            SignupController c = new SignupController();
            c.setUserRepository(userRepo);
            return c;
        }

        if (cls == LoginController.class) {
            LoginController c = new LoginController();
            c.setUserRepository(userRepo);
            return c;
        }

        if (cls == LoanController.class) {
            LoanController c = new LoanController();
            c.setLoanRepository(loanRepo);
            c.setLoanCalculation(loanCalc);
            return c;
        }

        if (cls == LoanAmortizationController.class) {
            LoanAmortizationController c = new LoanAmortizationController();
            c.setAmortizationService(amortService);
            return c;
        }

        if (cls == CustomerController.class) {
            return new CustomerController();
        }

        if (cls == VehicleController.class) {
            return new VehicleController();
        }

        if (cls == HomepageController.class) {
            return new HomepageController();
        }

        // default
        try {
            return cls.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
