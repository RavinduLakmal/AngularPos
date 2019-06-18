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

@WebServlet(name = "/api/customers/" ,urlPatterns = ("/api/customers"))
public class CustomerController extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String address = jsonObject.getString("address");
            double salary = Double.parseDouble(jsonObject.getString("salary"));

            connection = dataSource.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");

            preparedStatement.setObject(1, id);
            preparedStatement.setObject(2, name);
            preparedStatement.setObject(3, address);
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

//        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doDelete(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doOptions(req, resp);
    }
}
