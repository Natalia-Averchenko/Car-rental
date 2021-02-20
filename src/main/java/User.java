import java.sql.Date;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date dateOfBirth;
    private long passportNumber;
    private Date dateOfIssue;


    public User(String firstName, String lastName, String middleName, Date dateOfBirth, long passportNumber, Date dateOfIssue) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.passportNumber = passportNumber;
        this.dateOfIssue = dateOfIssue;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfIssue;
    }
    public void setPassportNumber(long passportNumber) {
        this.passportNumber = passportNumber;
    }
    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfBirth = dateOfIssue;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public long getPassportNumber() {
        return this.passportNumber;
    }

    public Date getDateOfIssue() {
        return this.dateOfIssue;
    }

    @Override
    public String toString() {
        return "User ID: " +this.getUserId()+"\t\tFirst name: " + this.getFirstName() + "\tLast Name: " + this.getLastName() + "\tMiddle name: " + this.getMiddleName() + "\tDate of birth: " + this.getDateOfBirth() + "\tPassport Number: " + this.getPassportNumber() + "\tDate of issue: " + this.getDateOfIssue();
    }
}
