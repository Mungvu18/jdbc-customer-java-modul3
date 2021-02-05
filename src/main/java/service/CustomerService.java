package service;

import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService implements ICustomerService{
    // tạo 1 đối tượng conection để kết nối với database
    private Connection getConnection(){
        Connection connection = null;
        try {
            // tải lớp trình điều khiển jdbc cho DriverManager  hay còn gọi là load Driver trong memory tại thời điểm runtime
            Class.forName("com.mysql.jdbc.Driver");
            // tạo kết lối
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/customerManager",
                    "root",
                    "123456"
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    @Override
    public List<Customer> findAll() {
        // tọa 1 danh sách khách hàng
        List<Customer> customers = new ArrayList<>();
        // tạo 1 kêt nối với database
        Connection connection = getConnection();
        try {
            // tạo câu lệnh try vấn thông qua đối tượng statement
            Statement statement = connection.createStatement();
            // thực hiện truy vấn
            ResultSet resultSet = statement.executeQuery("select * from customers");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Customer customer = new Customer (id,name,address);
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer creat(int id, Customer customer) {
        // b1 tạo ra 1 đối tượng thuộc lớp connection để kết nối database
        Connection connection = getConnection();
        try {
            // tạo ra đối tượng để truy vấn
            PreparedStatement preparedStatement = connection.prepareStatement("insert into customers (id,name,Address) values (?,?,?)");
            // cài đặt thuộc tính cho đối tượng
            preparedStatement.setInt(1,customer.getId());
            preparedStatement.setString(2,customer.getName());
            preparedStatement.setString(3, customer.getAddress());
            // thực hiện truy vấn thông qua đối tượng try vấn
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = null;
        Connection connection = getConnection();

        // tạo đối tượng truy vấn
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where id=?");
            // cài đặt lại thuộc tính truy vấn cho đối tượng
            preparedStatement.setInt(1,id);
            // thực hiện truy vấn
            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                customer = new Customer(id,name,address);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    @Override
    public Customer update(int id, Customer customer) {
        // tạo một đối tượng connection để kết nối
        Connection connection = getConnection();
        // tạo mới đối tượng để try vấn
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update customers set name =?, address = ? where id = ?");
            // set lại thuộc tính cho đối tượng
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2,customer.getAddress());
            preparedStatement.setInt(3,customer.getId());
            // thực hiện câu lệnh truy vấn
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    @Override
    public void delete(int id) {
        // tạo 1 đối tượng connection để kết nối với database
        Connection connection = getConnection();
        // tạo một đối tượng để thực hiện try vấn
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from customers where id =?");
            // sét lại tuộc tính
            preparedStatement.setInt(1,id);
            // thực hiện câu lệnh truy vấn
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
