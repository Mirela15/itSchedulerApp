import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBTrainer {
    protected static Connection LoadConn()
    {
        final String URLDB = "jdbc:postgresql://localhost:5432/itScheduler";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="JavaOradea3!";
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public boolean newTrainerDB(Trainer t){

        System.out.println(t);

        boolean isInserted=false;
        try {

            PreparedStatement pSt = LoadConn().prepareStatement("INSERT INTO trainer (tr_name,email,password,tr_course,date) VALUES(?,?,?,?,?)");
            pSt.setString(1,t.getName());
            pSt.setString(2,t.getEmail());
            pSt.setString(3,t.getPassword());
            pSt.setString(4, t.getCourse());
            pSt.setDate(5, (t.getDate()));

            int insert = pSt.executeUpdate();
            if(insert!=-1)
                isInserted=true;
            System.out.println(isInserted);

            pSt.close();
            pSt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            isInserted=false;

        }
        return isInserted;
    }
    public Trainer login(String email, String password) {

       Trainer t = null;

        try {
            PreparedStatement pSt = LoadConn().prepareStatement("select id, email,tr_course from trainer where email=? and password=?");

            pSt.setString(1, email);
            pSt.setString(2, password);

            // 3. executie
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
               t = new Trainer();
               t.setCourse(rs.getString("tr_course"));
               t.setEmail(rs.getString("email"));
               t.setId(rs.getInt("id"));
            }

            rs.close();
            pSt.close();


        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return t;
    }



    public List<Trainer> getTrainerList (  String course){

        Trainer mtl=null;
        List<Trainer> list =new ArrayList<>() ;
        System.out.println("get list trainer");
        try {
        PreparedStatement pst = LoadConn().prepareStatement("select * from trainer where  tr_course=? ");
       // pst.setInt(1,course);
        pst.setString(1,course);
        ResultSet rs= pst.executeQuery();

        while(rs.next()){
        mtl=new Trainer();
        mtl.setId(rs.getInt("id"));
        mtl.setName(rs.getString("tr_name"));
        mtl.setEmail(rs.getString("email"));
        mtl.setDate(rs.getDate("date"));
        mtl.setCourse(rs.getString("tr_course"));

        list.add(mtl);
        }
        rs.close();
        pst.close();
        } catch (SQLException e) {
        e.printStackTrace();
        }
        System.out.println(list);
        return  list;
        }

        public boolean checkEmailCourse(String ReqCourse,String ReqEmail){
        String ResCourse=null;
        String ResEmail=null;
        boolean chkOk=false;
            try {
                PreparedStatement pSt1=LoadConn().prepareStatement("select tr_course,email from trainer ");
                ResultSet rs = pSt1.executeQuery();
                    while (rs.next()) {
                        ResCourse= rs.getString("tr_course").trim();
                        ResEmail=rs.getString("email").trim();
                        if (ReqCourse.equals(ResCourse) || ReqEmail.equalsIgnoreCase(ResEmail) || ReqEmail==null){
                            chkOk=true;
                            break;
                        }

            }}
            catch (SQLException e) {
                e.printStackTrace();}
            return chkOk;

        }
}