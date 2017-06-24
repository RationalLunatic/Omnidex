package playercharacter;

public class PlayerCore {
    private String username;
    private String gender;
    private int age;

    public PlayerCore(String username, String gender, int age) {
        this.username = username;
        this.gender = gender;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
