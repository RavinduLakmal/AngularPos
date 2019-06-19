package lk.ijse.spapos.controller;

import javax.annotation.Resource;
import javax.json.*;

import javax.json.stream.JsonParsingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "/api/customers/" ,urlPatterns = ("/api/customers"))
public class CustomerController extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (PrintWriter out = resp.getWriter()) {

            if (req.getParameter("id") != null) {

                String id = req.getParameter("id");

                try {
                    Connection connection = dataSource.getConnection();
                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");
                    pstm.setObject(1, id);
                    ResultSet rst = pstm.executeQuery();

                    if (rst.next()) {
                        JsonObjectBuilder ob = Json.createObjectBuilder();
                        ob.add("id", rst.getString(1));
                        ob.add("name", rst.getString(2));
                        ob.add("address", rst.getString(3));
                        resp.setContentType("application/json");
                        out.println(ob.build());
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            else{
                try {
                    Connection connection = dataSource.getConnection();
                    Statement stm = connection.createStatement();
                    ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

                    resp.setContentType("application/json");

                    JsonArrayBuilder ab = Json.createArrayBuilder();

                    while (rst.next()){
                        JsonObjectBuilder ob = Json.createObjectBuilder();
                        ob.add("id",rst.getString("id"));
                        ob.add("name",rst.getString("name"));
                        ob.add("address",rst.getString("address"));
                        ab.add(ob.build());
                    }
                    out.println(ab.build());
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }

        }


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
        if (req.getParameter("id") != null){

            try {
                JsonReader reader = Json.createReader(req.getReader());
                JsonObject customer = reader.readObject();

                String id = customer.getString("id");
                String name = customer.getString("name");
                String address = customer.getString("address");

                if (!id.equals(req.getParameter("id"))){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                Connection connection= dataSource.getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?, address=? WHERE id=?");
                pstm.setObject(3,id);
                pstm.setObject(1,name);
                pstm.setObject(2,address);
                int affectedRows = pstm.executeUpdate();

                if (affectedRows > 0){
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }else{
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }

            }catch (JsonParsingException | NullPointerException  ex){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }catch (Exception ex){
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }


        }else{
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
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
