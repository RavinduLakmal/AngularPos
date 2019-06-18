package lk.ijse.spapos.controller;

import lk.ijse.spapos.dto.OrderDetailDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "/api/orders/" ,urlPatterns = ("/api/orders"))
public class OrderController extends HttpServlet {

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
            String code = jsonObject.getString("odCode");
            Date date = Date.valueOf(jsonObject.getString("date"));
            String custId = jsonObject.getString("custId");
            JsonArray orderdetails = jsonObject.getJsonArray("Orderdetails");

            List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

            for (int i = 0; i < orderdetails.size(); i++) {


                JsonObject object = orderdetails.getJsonObject(i);
//                String name = code;
                String addres = object.getString("itemCode");
                int qty = Integer.parseInt(object.getString("qty"));
                double unitPricr = Double.parseDouble(object.getString("unitPricr"));

                OrderDetailDTO orderDetailDTO = new OrderDetailDTO(code,addres,qty,unitPricr);

                orderDetailDTOS.add(orderDetailDTO);
                //            for (JsonObject jsonObject1 : orderdetails){
//                jsonObject1.toString();
//                System.out.println();
            }

            connection = dataSource.getConnection();


            connection.setAutoCommit(true);


            PreparedStatement preparedStatement = connection.prepareStatement("Insert Into Orders VALUES(?,?,?)");
            preparedStatement.setObject(1,code);
            preparedStatement.setObject(2,date);
            preparedStatement.setObject(3,custId);
            boolean result = preparedStatement.executeUpdate()>0;

            if (result){
                for (OrderDetailDTO orderDetailDTO:orderDetailDTOS) {
                    PreparedStatement pr = connection.prepareStatement("INSERT INTO Orderdetail VALUES(?,?,?,?)");
                    pr.setObject(1,orderDetailDTO.getOdID());
                    pr.setObject(2,orderDetailDTO.getItemCode());
                    pr.setObject(3,orderDetailDTO.getQty());
                    pr.setObject(4,orderDetailDTO.getUnitPricr());
                }
            }


        }catch (Exception e){
            e.fillInStackTrace();
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
