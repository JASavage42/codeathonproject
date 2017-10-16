package codeathon.ku.mentr;

/**
 * Created by jason on 10/15/2017.
 */

public class UserInfo {
    private String name;
    private String lastName;
    private String email;
    private boolean mentor;


    public UserInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getMentor() {
        return mentor;
    }

    public void setMentor(boolean mentor) {
        this.mentor = mentor;
    }
}
