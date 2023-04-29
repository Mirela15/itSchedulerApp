import java.sql.Date;
import java.time.LocalDate;

public class Trainer extends Person {

    private int id;
    private Date date;

    public Trainer(String name, String email, String password,String course,Date data) {
        super(name, email, password,course);
        this.date=data;


    }

    public Trainer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + getName().toString() + '\'' +
                ", email='" + getEmail()+ '\'' +
                ", password='" + getPassword()+ '\'' +
                ", course='" + getCourse() + '\'' +
               ", date=" + getDate()+
                "} ";
    }
}
