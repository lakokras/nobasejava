import exception.InsufficientFundsException;
import exception.InvalidAccountException;
import exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    BankAccount acc1;
    BankAccount acc2;

    @BeforeEach
    public void setUp() {
        acc1 = new BankAccount("ACC001", 1000, "Bill");
        acc2 = new BankAccount("ACC002", 1000, "Anna");
    }

    @Test
    public void depositShouldIncreaseBalance() {
        acc1.deposit(500);
        assertEquals(1500, acc1.getBalance());
    }

    @Test
    public void withdrawShouldDecreaseBalance() throws InsufficientFundsException, InvalidAmountException {
        acc2.withdraw(300);
        assertEquals(700, acc2.getBalance());
    }

    @Test
    public void withdrawShouldThrowInsufficientFunds() {
        assertThrows(InsufficientFundsException.class, () -> acc2.withdraw(1500));
    }

    @Test
    public void depositShouldThrowNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> acc1.deposit(-500));
    }

    @Test
    public void transferShouldMoveBetweenAccounts() throws InsufficientFundsException, InvalidAmountException,
            AccountNotFoundException, InvalidAccountException {
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(acc1);
        accounts.add(acc2);
        Bank bank = new Bank(accounts, new ArrayList<>());
        bank.transfer("ACC001", "ACC002", 500);
        assertEquals(500, acc1.getBalance());
        assertEquals(1500, acc2.getBalance());
    }
}

/*
* 1 test
* Создаём счёт с балансом 1000
* Кладём 500
* assertEquals проверяет что баланс стал 1500
*
* 2 test
* Создаём счёт с балансом 1000
* Снимаем 300
* assertEquals проверяет что баланс стал 700
*
* 3 test
* Создаём счёт с балансом 500
* Снимаем 1000
* assertThrows проверяет работу исключения, что нельзя снять больше, чем есть на балансе
*
* 4 test
* Создаём счёт с балансом 1000
* Кладём -500
* assertThrows проверяет работу исключения, что нельзя пополнять на отрицательное значение
*
* 5 test
* Создаем два счёта с балансом 1000 и 1000
* Переводим сумму в 500 через Bank с одного аккаунта на другой
* assertEquals проверяет, что у первого баланс уменьшился, а у второго увеличился
* */