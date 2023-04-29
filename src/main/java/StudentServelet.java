import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;


@WebServlet("/listStudent")
public class StudentServelet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("listStudent");
        HttpSession s= req.getSession();
        Object o =s.getAttribute("tr_course");



        if(o!=null){
            String course = (String) s.getAttribute("tr_course");
            String search= req.getParameter("search");
            DBStudent db=new DBStudent();
            List<Student> ls=db.getStudentList(course,search);
            JSONObject json=new JSONObject();
            json.put("listOfStudent",ls);
            String result=json.toString();
            returnJsonResponse(resp,result);
        }
        else {
            error(resp,"operation forbidden. user is not logged in.");
        }}

        private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
            response.setContentType("application/json");
            PrintWriter pr = null;
            try {
                pr = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert pr != null;
            pr.write(jsonResponse);
            pr.close();
        }

        private void error( HttpServletResponse resp, String mesaj) {

            try {
                PrintWriter pw = resp.getWriter();
                pw.println(mesaj);
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





