package codeathon.ku.mentr;

/**
 * Created by jason on 10/15/2017.
 */

public class UserInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String mentor;
    private String name = firstName + " " + lastName;

    public UserInfo () {

    }

    public void setName(String name) {
        this.name= name;
    }

    public String getName() { return name;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }
}
