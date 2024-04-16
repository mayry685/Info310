package domain;
import java.util.Collection;

public class Account {
    
    //datafields
    private String AccountId;
    private String FirstName;
    private String LastName;
    private String UserName;
    private String Password;
    private String Email;
    private String Status;

    public Account(String AccountId, String FirstName, String LastName, String UserName, String Password, String Email, String Status) {
        this.AccountId = AccountId;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.UserName = UserName;
        this.Password = Password;
        this.Email = Email;
        this.Status = Status;
    }

    public Account() {
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String AccountId) {
        this.AccountId = AccountId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    @Override
    public String toString() {
        return "Account{" + "AccountId=" + AccountId + ", FirstName=" + FirstName + ", LastName=" + LastName + ", UserName=" + UserName + ", Password=" + Password + ", Email=" + Email + ", Status=" + Status + '}';
    }
    
}