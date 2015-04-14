
package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import javax.faces.context.FacesContext;


 
@ManagedBean(name="Loginbean")
@SessionScoped
public class Login {
    private String username;
    private String password;
    private String dbusername;
    private String dbpassword;
    private int dbuserid;
    private String privilege;

    public String getPrivilege() {
        return privilege;
    }

    public int getDbuserid() {
        return dbuserid;
    }
 
    public String getDbpassword() {
        return dbpassword;
    }
    public String getDbusername() {
        return dbusername;
    }
 
    Connection con;
    Statement ps;
    ResultSet rs;
    String SQL_Str;
 
    public void dbData(String UName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/expenses","root","");
            ps = con.createStatement();
            SQL_Str="Select * from users where username like ('" + UName +"')";
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            dbuserid=rs.getInt(1);
            dbusername=rs.getString(5);
            dbpassword=rs.getString(6);
            privilege=rs.getString(7);
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Exception Occur :" + ex);
        }
    }
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUsername() {
        return username;
    }
 
    public String checkValidUser()
    {
        dbData(username);
 
        if(username.equalsIgnoreCase(dbusername))
        {
 
            if(password.equals(dbpassword))
                //return "valid";
            {
                FacesContext context = FacesContext.getCurrentInstance();
                if(privilege.equals("0"))
                {
                context.getExternalContext().getSessionMap().put("key","0");
                context.getExternalContext().getSessionMap().put("session_userid",dbuserid);
                    return "http://www.google.com";
                }
                else{
                context.getExternalContext().getSessionMap().put("key","1");
                context.getExternalContext().getSessionMap().put("session_userid",dbuserid);
                return "http://www.hotmail.com";}
            }
            else
            {
                return "Incorrect password";
            }
        }
        else
        {
            return "Incorrect username";
        }
    }

}