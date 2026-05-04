import exception.InsufficientFundsException;
import exception.InvalidAmountException;

public class BankAccount{
    private final String owner;
    private String accountID;
    protected double balance;

    public BankAccount(String accountID, double balance, String owner) {
        setAccountID(accountID);
        setBalance(balance);
        this.owner = owner;
    }

    private void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма депозита должна быть положительной");
        }
        balance += amount;
        System.out.println("Была начислена сумма: " + amount);
    }
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Сумма снятия должна быть положительной");

        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                    String.format("Недостаточно средств. Текущий баланс: %.2f, запрошенная сумма: %.2f",
                            balance, amount));
        }
        balance -= amount;
        System.out.println("Была списана сумма: " + amount);
    }

    @Override
    public String toString() {
        return String.format("[ID=%s, баланс=%.2f, владелец=%s]",
                accountID, balance, owner);
    }

    public void displayBalance() {
        System.out.println("Текущий баланс: " + balance);
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public double getBalance() {
        return balance;
    }
}


/*
 * Снимает указанную сумму со счёта.
 * @param amount сумма для снятия (должна быть положительной)
 * @throws IllegalArgumentException если сумма превышает баланс или <=0
 */