public class Student extends Person
{
    private int id;
    private String hasknowledge;

    public Student(String name, String email, String password, String course,String hasknowledge) {
        super(name, email, password, course);
        this.hasknowledge=hasknowledge;
    }

    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String isHasknowledge() {
        return hasknowledge;
    }

    public void setHasknowledge(String hasknowledge) {
        this.hasknowledge = hasknowledge;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + getName().toString() + '\'' +
                ", email='" + getEmail()+ '\'' +
                ", password='" + getPassword()+ '\'' +
                ", course='" + getCourse() + '\'' +
                "} ";
    }
}
