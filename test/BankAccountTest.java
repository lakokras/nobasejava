import exception.InsufficientFundsException;
import exception.InvalidAccountException;
import exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    public void depositShouldIncreaseBalance() {
        BankAccount acc1 = new BankAccount("ACC001", 1000, "Bill");
        acc1.deposit(500);
        assertEquals(1500, acc1.getBalance());
    }

    @Test
    public void withdrawShouldDecreaseBalance() throws InsufficientFundsException, InvalidAmountException {
        BankAccount acc2 = new BankAccount("ACC002", 1000, "Bob");
        acc2.withdraw(300);
        assertEquals(700, acc2.getBalance());
    }

    @Test
    public void withdrawShouldThrowInsufficientFunds() {
        BankAccount acc3 = new BankAccount("ACC003", 500, "Michel");
        assertThrows(InsufficientFundsException.class, () -> acc3.withdraw(1000));
    }

    @Test
    public void depositShouldThrowNegativeAmount() {
        BankAccount acc4 = new BankAccount("ACC004", 1000, "Anna");
        assertThrows(IllegalArgumentException.class, () -> acc4.deposit(-500));
    }

    @Test
    public void transferShouldMoveBetweenAccounts() throws InsufficientFundsException, InvalidAmountException,
            AccountNotFoundException, InvalidAccountException {
        BankAccount acc1 = new BankAccount("ACC001", 1000, "Bill");
        BankAccount acc2 = new BankAccount("ACC002", 1000, "Bob");
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