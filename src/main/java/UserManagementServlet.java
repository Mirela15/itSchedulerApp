import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/userManagement")
public class UserManagementServlet extends HttpServlet {

    protected static Connection LoadConn()
    {
        final String URLDB = "jdbc:postgresql://localhost:5432/itScheduler";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="JavaOradea3!";

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


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action"); // name as in the html form
        System.out.println("action is:" + action);
        boolean newStudent= false;
        boolean newTrainer= false;
        if (action != null && action.equalsIgnoreCase("NEWStudent")) {
            newStudent= newStudent(req, resp);

            if(newStudent)
            {
                RequestDispatcher rd=req.getRequestDispatcher("login.html");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                error(resp, "there is an error while trying to create this user, pls try again");
            }


        }
        else if (action!=null && action.equalsIgnoreCase("NewTrainer")){

            newTrainer= newTrainer(req, resp);

            if(newTrainer)
            {
                RequestDispatcher rd=req.getRequestDispatcher("login.html");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (action != null && action.equalsIgnoreCase("LOGIN")) {
            //afisare
            newStudent = loginStudent(req, resp);
            newTrainer=  loginTrainer(req,resp);
            if(newStudent )
            {
                System.out.println("Login student ok");
                RequestDispatcher rd=req.getRequestDispatcher("MyStuffStudent.jsp");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
              else if  (newTrainer)
            {
                System.out.println("Login trainer ok");
                RequestDispatcher rd=req.getRequestDispatcher("MyStuffTrainer.jsp");

                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                RequestDispatcher rd=req.getRequestDispatcher("login.html");
                try {
                    rd.forward(req, resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (action != null && action.equalsIgnoreCase("OUT")) {
            HttpSession s = req.getSession();
            s.invalidate();
            try {
                resp.sendRedirect("login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         else {
            System.out.println("action didn`t come ");
            error(resp, "error on ui side");
        }

    }

    private boolean newStudent(HttpServletRequest req, HttpServletResponse resp) {
        boolean chkMehod=false;
        boolean inserted=false;
        String name = req.getParameter("st_name");
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        String confirmPwd = req.getParameter("confirmPwd");
        String coursethtml = req.getParameter("st_course");
        String hasknowledge= req.getParameter("hasknowledge");

        System.out.println(pwd+confirmPwd);
        // validari
        if(!pwd.equals(confirmPwd))
        {
           error(resp, "Password is not the same as confirmed password");
           return false;
        }
        DBStudent db =new DBStudent();
        chkMehod=db.checkEmailCourse(email);
        if(!chkMehod){
           inserted =true;
        }
        else {
            error(resp,"Email already in use.");
            return false;
        }



        DBStudent dbStudent = new DBStudent();
        Student s = new Student (name,email,pwd, coursethtml,hasknowledge);
         inserted = dbStudent.newStudent(s);


        return inserted;

    }

    private boolean loginStudent(HttpServletRequest req, HttpServletResponse resp) {
        Student student = null;
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        boolean isLoggedIn=false;

        DBStudent dbStudent = new DBStudent();
        student = dbStudent.login(email, pwd);
        if (student != null)
        {
            HttpSession s = req.getSession();
            s.setAttribute("id", student.getId());
            s.setAttribute("email", student.getEmail());
            s.setAttribute("st_course",student.getCourse());
            isLoggedIn=true;
        }
       return isLoggedIn;
    }




    private boolean newTrainer(HttpServletRequest req, HttpServletResponse resp) {

        boolean inserted=false;
        String name = req.getParameter("tr_name");
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        String confirmPwd = req.getParameter("confirmPwd");
        String coursethtml = req.getParameter("course");
        String date = req.getParameter("date");
        boolean chkMehod=false;

        System.out.println(pwd + confirmPwd);
        // validari
        if (!pwd.equals(confirmPwd)) {
            error(resp, "Password is not the same as confirmed password");
            return false;
        }
        DBTrainer db =new DBTrainer();
        chkMehod=db.checkEmailCourse(coursethtml,email);
        if(!chkMehod){
            inserted =true;
        }
        else {
            error(resp,"The course is already taken or email already in use.");
            return false;
        }

        DBTrainer dbTrainer = new DBTrainer();
       Trainer trainer  = new Trainer(name, email, pwd, coursethtml, Date.valueOf(date));

         inserted=  dbTrainer.newTrainerDB(trainer);

        return inserted;
    }

    private boolean loginTrainer(HttpServletRequest req, HttpServletResponse resp) {
        Trainer trainer = null;
        String email = req.getParameter("email");
        String pwd = req.getParameter("password");
        boolean isLoggedIn = false;

        DBTrainer dbTrainer = new DBTrainer();
        trainer = dbTrainer.login(email, pwd);
        if (trainer != null) {
            HttpSession s = req.getSession();
            s.setAttribute("id",trainer.getId());
            s.setAttribute("email",trainer.getEmail());
            s.setAttribute("tr_course",trainer.getCourse());
            s.setAttribute("date",trainer.getDate());
            isLoggedIn = true;
        }
        return isLoggedIn;
    }

    private void error( HttpServletResponse resp, String mesaj) {

        try {
            PrintWriter pw = resp.getWriter();
            pw.println(mesaj);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
