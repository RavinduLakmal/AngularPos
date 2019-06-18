import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "/Login",urlPatterns = ("/Login"))
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println(request);
//        String name = request.getParameter("user");
//        String mobile = request.getParameter("mobile");
//        String address = request.getParameter("address");
//
//        response.sendRedirect("Welcome.jsp");

//        System.out.println(mobile);
//
//        if (name.equals("Ravindu") && mobile.equals("mobile")){
//            response.sendRedirect("Welcome.jsp");
//        }else{
//            response.sendRedirect("index.jsp");
//        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
