package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    Account a;

    @BeforeEach
    public void runBefore() {
        a = new Account();
    }

    @Test
    public void testgetBalance() {
        assertEquals(300, a.getBalance());
        a.deposit(100.1);
        assertEquals(300 + 100.1, a.getBalance());
    }

    @Test
    public void testsetBalance() {
        a.setBalance(100.0);
        assertEquals(100.0, a.getBalance());
    }

    @Test
    public void testWithdraw() {
        assertEquals(300, a.getBalance());
        assertTrue(a.withdraw(150.50));
        assertEquals(300 - 150.50, a.getBalance());
        assertFalse(a.withdraw(200));
        assertEquals(300 - 150.50, a.getBalance());

    }

    @Test
    public void testDeposit() {
        assertEquals(300, a.getBalance());
        a.deposit(4.49);
        assertEquals(304.49, a.getBalance());
    }
}