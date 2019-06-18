package lk.ijse.spapos.controller;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "/api/items/" ,urlPatterns = ("/api/items"))
public class IremController extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        System.out.println(reader+" Reader ");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        Connection connection= null;

        try{
            JsonObject jsonObject= reader.readObject();
            String code = jsonObject.getString("code");
            String description = jsonObject.getString("description");
            double unitprice = Double.parseDouble(jsonObject.getString("unitprice"));
            int salary = Integer.parseInt(jsonObject.getString("qtyOmHand"));

            connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");

            preparedStatement.setObject(1, code);
            preparedStatement.setObject(2, description);
            preparedStatement.setObject(3, unitprice);
            preparedStatement.setObject(4,salary);
            boolean result = preparedStatement.executeUpdate()>0;

            if (result){
                out.println("true result");
            }else {
                out.println("false resault");
            }

        }catch (Exception e){
            e.fillInStackTrace();
            out.println("false exception");
        }finally {
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            out.close();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
