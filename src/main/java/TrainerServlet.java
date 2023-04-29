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

@WebServlet("/listTrainer")
public class TrainerServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("list of trainer");
        HttpSession s= req.getSession();
        Object o =s.getAttribute("st_course");

        if(o!=null){
           String Course= (String) s.getAttribute("st_course");

            DBTrainer db=new DBTrainer();
            List<Trainer> ls=db.getTrainerList(Course);
            JSONObject json=new JSONObject();
            json.put("listOfTrainer",ls);
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
