import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBStudent {

    protected static Connection LoadConn() {
        final String URLDB = "jdbc:postgresql://localhost:5432/itScheduler";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "JavaOradea3!";

        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }


    public boolean newStudent(Student student) {

        System.out.println(student);

        boolean isInserted = false;
        try {


            // 2. creez un prepared ststement si il populez cu date
            PreparedStatement pSt = LoadConn().prepareStatement("INSERT INTO student (st_name,email, password, st_course,hasknowledge) VALUES(?,?,?,?,?)");
            pSt.setString(1, student.getName());
            pSt.setString(2, student.getEmail());
            pSt.setString(3, student.getPassword());
            pSt.setString(4, student.getCourse());
            pSt.setString(5, student.isHasknowledge());


            // 3. executie
            int insert = pSt.executeUpdate();
            if (insert != -1)
                isInserted = true;
            System.out.println(isInserted);

            pSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            isInserted = false;

        }


        return isInserted;
    }

    public Student login(String name, String password) {

        Student s = null;
        try {
            PreparedStatement pSt = LoadConn().prepareStatement("select id, email, st_course from student where email=? and  password=?");

            pSt.setString(1, name);
            pSt.setString(2, password);
            // 3. executie
            ResultSet rs = pSt.executeQuery();
            // atata timp cat am randuri
            while (rs.next()) {
                s = new Student();
                s.setId(rs.getInt("id"));
                s.setEmail(rs.getString("email"));
                s.setCourse(rs.getString("st_course"));
            }

            rs.close();
            pSt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return s;
    }


    //==============================List Student=====================================
    public List<Student> getStudentList(String course, String search) {

        Student msl = null;
        List<Student> list = new ArrayList<>();

        System.out.println("get list student");
        try {
            PreparedStatement pst = LoadConn().prepareStatement("select * from student  where st_course=? and hasknowledge like concat('%',?,'%') order by st_name desc");
            pst.setString(1, course);
            pst.setString(2, search);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                msl = new Student();
                msl.setId(rs.getInt("id"));
                msl.setName(rs.getString("st_name"));
                msl.setEmail(rs.getString("email"));
                msl.setHasknowledge(rs.getString("hasknowledge"));
                msl.setCourse(rs.getString("st_course"));

                list.add(msl);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkEmailCourse(String reqEmail) {
        String respEmail = null;
        boolean chkOk = false;
        int getRow = 0;
        try {
            PreparedStatement pSt1 = LoadConn().prepareStatement("select email from student ");
            ResultSet rs = pSt1.executeQuery();
            while (rs.next()) {
                respEmail = rs.getString("email").trim();
                if (reqEmail.equalsIgnoreCase(respEmail) || reqEmail == null) {
                    chkOk = true;
                    break;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chkOk;

    }
    //==============================delete Student=====================================

    public void delete(int idname) {


        try {
            PreparedStatement pSt = LoadConn().prepareStatement("delete from student where id=?");
            pSt.setInt(1, idname);
            pSt.executeUpdate();

            pSt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }



}
