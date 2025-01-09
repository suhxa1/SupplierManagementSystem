package Supply_Management.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Supply_Management.dao.SupplierDAO;
import Supply_Management.model.Supplier;

/**
 * Servlet implementation class SupplierServlet
 */
@WebServlet("/")
public class SupplierServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SupplierDAO supplierDAO;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplierServlet() {
    	this.supplierDAO = new SupplierDAO();
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
		
	}
    


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch(action) {
		case "/new" :
			showNewForm(request, response);
			break;
		case "/insert" :
			try {
				insertUser(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/delete" :
			try {
				deleteUser(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/edit" :
			try {
				showEditForm(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/update" :
			try {
				updateUser(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;	
		default:
			try {
				listUser(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//handle list
			
		}
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);	
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException , IOException, SQLException{
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		Supplier newUser = new Supplier(name, email, phone);
		supplierDAO.insertUser(newUser);
		response.sendRedirect("list");
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		int id = Integer.parseInt(request.getParameter("id"));
		supplierDAO.deleteUser(id);
		response.sendRedirect("list");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		int id = Integer.parseInt(request.getParameter("id"));
		Supplier existingUser = supplierDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
	}
	
	

	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		Supplier supplier = new Supplier(id, name, email, phone);
		supplierDAO.updateUser(supplier);
		response.sendRedirect("list");
		
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException, SQLException{
		List <Supplier> listUser = supplierDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}



}
