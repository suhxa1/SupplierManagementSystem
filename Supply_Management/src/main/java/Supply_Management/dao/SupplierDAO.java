package Supply_Management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Supply_Management.model.Supplier;



//this DAO class provides CRUD database operations for the table users in the database.| I
public class SupplierDAO {
	//private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo";
	private String jdbcUsername = "root";
	private String jdbcPassword = "yuvankajanan";
	
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + " (name, email, phone) VALUES " 
			+ " (?,?,?);";
	private static final String SELECT_USER_BY_ID = " select id, name, email, phone from users where id = ?";
	private static final String SELECT_ALL_USERS = " select * from users";
	private static final String DELETE_USERS_SQL = " delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, phone =? where id = ?;";
	

	
	// set connection
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
			System.out.println("db connection sucessfully!!!");
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	

	// create user

	public void insertUser(Supplier supplier) throws SQLException {
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, supplier.getName());
			preparedStatement.setString(2, supplier.getEmail());
			preparedStatement.setString(3, supplier.getphone());
			preparedStatement.executeUpdate();			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// update user
	
	public boolean updateUser(Supplier supplier) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);){
			statement.setString(1, supplier.getName());
			statement.setString(2, supplier.getEmail());
			statement.setString(3, supplier.getphone());
			statement.setInt(4, supplier.getId());	
			
			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	// select user by id
	
	public Supplier selectUser(int id) throws SQLException {
		Supplier supplier = null;
		
		// Step 1 : Establishing connection
		try(Connection connection = getConnection();
				
				// Step 2 : create a statement using connection object
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			
			// Step 3 : Execute a query or update a query
			ResultSet rs = preparedStatement.executeQuery();
			
			// Step 4 : process the ResultSet object
			while(rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				supplier = new Supplier(id, name, email, phone);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return supplier;
	}
	
	
	// select All users
	
	public List<Supplier> selectAllUsers() throws SQLException{
		List<Supplier> suppliers = new ArrayList<>();
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);){
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name =rs.getString("name");
				String email =rs.getString("email");
				//String phone =rs.getString("phone");
				String ph1 = rs.getString("phone");				
				suppliers.add(new Supplier(id, name, email, ph1));
			}
		}catch(SQLException e){
				e.printStackTrace();
		}
		return suppliers;
	}
	
		
	
	// delete user
	
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted; 
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;		
		}	
		return rowDeleted;
	}
	

	
}



















