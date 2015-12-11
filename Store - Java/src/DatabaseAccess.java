import java.util.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class DatabaseAccess {
	
	public static Order [] GetPendingOrders()
	{
		// TODO:  Query the database and retrieve the information.
		// resultset.findcolumn(string col)
		
		// DUMMY DATA!
		Order o = new Order();
		o.OrderID = 1;
		o.Customer = new Customer();
		o.Customer.CustomerID = 1;
		o.Customer.Name = "Kevin";
		o.Customer.Email = "kevin@pathology.washington.edu";
		o.OrderDate = new Date();
		o.Status = "ORDERED";
		o.TotalCost = 520.20;
		o.BillingAddress = "1959 NE Pacific St, Seattle, WA 98195";
		o.BillingInfo	 = "PO 12345";
		o.ShippingAddress= "1959 NE Pacific St, Seattle, WA 98195";
		return new Order [] { o };
	}
	
	public static Product[] GetProducts()
	{
      Product[] products = new Product[100];
      try {
         int countP = 0;
   		String url = "jdbc:sqlserver://is-fleming.ischool.uw.edu";
   		String user = "perry";
   		String pass = "Info340C";
   		Connection conn = DriverManager.getConnection(url, user, pass);
   		conn.setCatalog("Store");
   		Statement sta = conn.createStatement();
   		String Sql = "select * from Products";
   		ResultSet rs = sta.executeQuery(Sql);
   		System.out.println(rs); 
         while (rs.next()) {
            Product p = new Product();
            p.ProductID = rs.getInt("ItemID");
            p.InStock = rs.getInt("QuantityOnHand");
            p.Name = rs.getString("Name");
            p.Description = rs.getString("Description");
            p.Price = rs.getDouble("Cost");
            String Cquery = "SELECT CommentText FROM Comments WHERE ProductId=? ";
            java.sql.PreparedStatement commentQ = conn.prepareStatement(Cquery);
            commentQ.setString(1, "" + p.ProductID); 
            ResultSet results = commentQ.executeQuery();
            p.UserComments = new String [100];
            int count = 0;
            while (results.next()) {
               p.UserComments[count] = results.getString("CommentText");
               count++;
            };
            System.out.println(p.toString());
            products[countP] = p;
            countP++;
         };
         conn.close();
      
      }catch(SQLException e) {
      }
      return products ;   
	}

	public static Order GetOrderDetails(int OrderID)
	{
		// TODO:  Query the database to get the flight information as well as all 
		// the reservations.
		
		// DUMMY DATA FOLLOWS
		Order o = new Order();
		o.OrderID = 1;
		o.Customer = new Customer();
		o.Customer.CustomerID = 1;
		o.Customer.Name = "Kevin";
		o.Customer.Email = "kevin@pathology.washington.edu";
		o.OrderDate = new Date();
		o.Status = "ORDERED";
		o.TotalCost = 520.20;
		o.BillingAddress = "1959 NE Pacific St, Seattle, WA 98195";
		o.BillingInfo	 = "PO 12345";
		o.ShippingAddress= "1959 NE Pacific St, Seattle, WA 98195";

		LineItem li = new LineItem();
		li.Order = o;
		li.PricePaid = 540.00;
		li.Product = new Product();
		li.Product.Description = "A great product.";
		li.Product.Name = "Computer Mouse";
		li.Quantity = 2;
		
		o.LineItems = new LineItem[] {li};
		return o;
	}

	public static Product GetProductDetails (int ProductID)
	{
		Product p = new Product();
      try {
         int countP = 0;
   		String url = "jdbc:sqlserver://is-fleming.ischool.uw.edu";
   		String user = "perry";
   		String pass = "Info340C";
   		Connection conn = DriverManager.getConnection(url, user, pass);
   		conn.setCatalog("Store");
         String Pquery = "SELECT * FROM Products WHERE ProductId=? ";
         java.sql.PreparedStatement productQ = conn.prepareStatement(Pquery);
         productQ.setString(1, "" + ProductID); 
         ResultSet rs = productQ.executeQuery();
   		System.out.println(rs); 
         p.ProductID = rs.getInt("ItemID");
         p.InStock = rs.getInt("QuantityOnHand");
         p.Name = rs.getString("Name");
         p.Description = rs.getString("Description");
         p.Price = rs.getDouble("Cost");
         String Cquery = "SELECT CommentText FROM Comments WHERE ProductId=?";
         java.sql.PreparedStatement commentQ = conn.prepareStatement(Cquery);
         commentQ.setString(1, "" + ProductID); 
         ResultSet results = commentQ.executeQuery();
         p.UserComments = new String [100];
         int count = 0;
         while (results.next()) {
            p.UserComments[count] = results.getString("CommentText");
            count++;
         };

      }catch(SQLException e) {
      }		
		return p;
		
	}
	
	public static Customer [] GetCustomers ()
	{
		// TODO:  Query the database to retrieve a list of customers.
		
		// DUMMY VALUES FOLLOW
		Customer c1 = new Customer();
		c1.CustomerID = 1;
		c1.Email = "k@u";
		c1.Name = "Kevin Fleming";
		
		Customer c2 = new Customer();
		c2.CustomerID = 2;
		c2.Email = "k@u";
		c2.Name = "Niki Cassaro";

		Customer c3 = new Customer();
		c3.CustomerID = 3;
		c3.Email = "k@u";
		c3.Name = "Ava Fleming";
		
		return new Customer [] { c1, c2, c3 };
	}
	
	public static Order [] GetCustomerOrders (Customer c)
	{
		Order o = new Order();
		o.OrderID = 1;
		o.Customer = new Customer();
		o.Customer.CustomerID = 1;
		o.Customer.Name = "Kevin";
		o.Customer.Email = "kevin@pathology.washington.edu";
		o.OrderDate = new Date();
		o.Status = "ORDERED";
		o.TotalCost = 520.20;
		o.BillingAddress = "1959 NE Pacific St, Seattle, WA 98195";
		o.BillingInfo	 = "PO 12345";
		o.ShippingAddress= "1959 NE Pacific St, Seattle, WA 98195";

		return new Order [] { o };
	}
	
	public static Product [] SearchProductReviews(String query)
	{
      Product[] products = new Product[100];
      try {
         int countP = 0;
   		String url = "jdbc:sqlserver://is-fleming.ischool.uw.edu";
   		String user = "perry";
   		String pass = "Info340C";
   		Connection conn = DriverManager.getConnection(url, user, pass);
   		conn.setCatalog("Store");
   		String Pquery = "select * from Products join Comments on Comments.ProductId=Products.ItemID where Comments.CommentText like ?";
         java.sql.PreparedStatement productQ = conn.prepareStatement(Pquery);
         productQ.setString(1, query); 
         ResultSet results = productQ.executeQuery();
         p.UserComments = new String [100];
         Set 
         while (results.next()) {
            Product p = new Product();
            
         }
   		System.out.println(rs); 

		return new Product [] { p} ;
	}
	                    
	public static void MakeOrder(Customer c, LineItem [] LineItems)
	{
		// TODO: Insert data into your database.
		// Show an error message if you can not make the reservation.
		
		JOptionPane.showMessageDialog(null, "Create order for " + c.Name + " for " + Integer.toString(LineItems.length) + " items.");
	}
}
