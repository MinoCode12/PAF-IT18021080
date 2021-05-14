package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/OrderAPI")
public class OrderAPI extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Order Obj = new Order();
	
    public OrderAPI() {
        super();
   
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = Obj.readOrder();
		
		response.getWriter().write(output.toString());

	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = Obj.insertOrder(request.getParameter("product_name"), 
				request.getParameter("quantity"),
				request.getParameter("customer_name"),
				request.getParameter("customer_address"),
				request.getParameter("contact_no"));
		
		System.out.println(output);
		
		response.getWriter().write(output);
		
		
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ID = request.getParameter("ID");
		String product_name = request.getParameter("product_name");
		String quantity = request.getParameter("quantity");
		String customer_name = request.getParameter("customer_name");
		String customer_address = request.getParameter("customer_address");
		String contact_no = request.getParameter("contact_no");
		System.out.println("ID: "+ID);
	
		String output = Obj.updateOrder(ID, product_name, quantity, customer_name, customer_address, contact_no);
		
		response.getWriter().write(output);
		
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("oId");

		String output = Obj.deleteOrder(id);
		
		System.out.println(output);
		
		response.getWriter().write(output.toString());
	
	}
	
	
	private static Map getParseMap(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			Scanner sc = new Scanner(request.getInputStream(), "UTF-8");
			String query = sc.hasNext() ? sc.useDelimiter("\\A").next() : "";
			sc.close();
			
			String[] params = query.split("&");
			
			for(String param : params) {
				
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
