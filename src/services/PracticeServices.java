package services;

import access.DBAccess;
import entity.AreaInfo;
import entity.ServiceResult;
import entity.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/7/12.
 */
public class PracticeServices {
    private static PracticeServices instance = null;

    public static PracticeServices getInstance() {
        if (instance == null) {
            instance = new PracticeServices();
        }
        return instance;
    }

    public String getLoginResult(String jsonArgs, String arguments) {
        ServiceResult serviceResult = new ServiceResult();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            if (arguments.isEmpty()) {
                serviceResult.setCode(1);
                serviceResult.setMesssage("getLoginResult 参数为空！");
            }

            JSONObject jsonObject = JSONObject.fromObject(arguments);
            String username = jsonObject.getString("username");
            String passsword = jsonObject.getString("password");

            String sql = "SELECT t1.*,areaname FROM userinfo t1  LEFT JOIN areainfo t2 ON t1.`areacode`=t2.`areacode` WHERE t1.`username`='" + username + "' AND t1.`password`='" + passsword + "'";

            conn = DBAccess.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            if (rs.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername(rs.getString("username"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setAreaname(rs.getString("areaname"));
                userInfo.setAreacode(rs.getString("areacode"));
                userInfo.setCreatetime(rs.getString("createtime"));
                userInfo.setRemarks(rs.getString("remarks"));
                serviceResult.setCode(0);
                serviceResult.setData(userInfo);
            } else {
                serviceResult.setCode(-1);
                serviceResult.setMesssage("用户名或密码不正确！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setCode(-1);
            serviceResult.setMesssage("getLoginResult catch an exception:" + e.getMessage());

        } finally {
            dispose(conn, stat, rs);
        }
        String val = serviceResult.toString();
        System.out.println(val);
        return val;
    }

    private void dispose(Connection conn, Statement stat, ResultSet rs) {
        try {
            if (conn != null) {
                DBAccess.closeConnection(conn);
            }
            if (stat != null) {
                stat.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UserInfo userInfo=new UserInfo();
            userInfo.setUsername("admin");
            userInfo.setPassword("admin");
            JSONObject jo =JSONObject.fromObject(userInfo);
            PracticeServices.getInstance().getLoginResult(jo.toString(), jo.toString());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String updateUser(String jsonArgs, String arguments) {
        //返回0 正常 1msg  3异常

        ServiceResult serviceResult = new ServiceResult();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            JSONObject jsonObject = JSONObject.fromObject(jsonArgs);
            int flag = jsonObject.getInt("flag");
            String username=jsonObject.getString("username");
            String sql = "";

            if(flag==3){//删除
                sql="DELETE FROM userinfo WHERE username = '"+username+"'";
            }else{
                String password=jsonObject.getString("password");
                String areacode=jsonObject.getString("areacode");
                String remarks=jsonObject.getString("remarks");
                String createtime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                if(flag==1){//添加
                    sql="INSERT INTO `practice`.`userinfo`(`username`,`password`,`areacode`,`remarks`,`createtime`)\n" +
                            "VALUES ('"+username+"','"+password+"','"+areacode+"','"+remarks+"','"+createtime+"')";
                }else if(flag==2){//修改
                    sql="UPDATE `practice`.`userinfo` SET  " +
                            "`password` = '"+password+"',\n" +
                            "  `areacode` = '"+areacode+"',\n" +
                            "  `remarks` = '"+remarks+"',\n" +
                            "  `createtime` = '"+createtime+"'\n" +
                            "WHERE `username` = '"+username+"'";
                }
            }
            conn = DBAccess.getConnection();
            stat = conn.createStatement();
            stat.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setCode(-1);
            serviceResult.setMesssage("updateUser catch an exception:" + e.getMessage());

        } finally {
            dispose(conn, stat, rs);
        }
        return serviceResult.toString();
    }

    public String getAllCities(String jsonArgs, String arguments) {
        ServiceResult serviceResult = new ServiceResult();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            String sql = "select * from areainfo where pareacode='32'";

            conn = DBAccess.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                AreaInfo areaInfo = new AreaInfo();
                areaInfo.setDescribtion(rs.getString("discribtion"));
                areaInfo.setpAreacode(rs.getString("pareacode"));
                areaInfo.setAreaname(rs.getString("areaname"));
                areaInfo.setAreacode(rs.getString("areacode"));
                jsonArray.add(areaInfo);
            }
            serviceResult.setCode(0);
            serviceResult.setData(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setCode(-1);
            serviceResult.setMesssage("getAllCities catch an exception:" + e.getMessage());

        } finally {
            dispose(conn, stat, rs);
        }
        return serviceResult.toString();
    }

    public String getRegions(String jsonArgs, String argument) {
        ServiceResult serviceResult = new ServiceResult();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            JSONObject jsonObject = JSONObject.fromObject(jsonArgs);
            String pcode = jsonObject.getString("areacode");
            String sql = "SELECT * FROM areainfo WHERE pareacode='"+pcode+"'";

            conn = DBAccess.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                AreaInfo areaInfo = new AreaInfo();
                areaInfo.setDescribtion(rs.getString("discribtion"));
                areaInfo.setpAreacode(rs.getString("pareacode"));
                areaInfo.setAreaname(rs.getString("areaname"));
                areaInfo.setAreacode(rs.getString("areacode"));
                jsonArray.add(areaInfo);
            }
            serviceResult.setCode(0);
            serviceResult.setData(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setCode(-1);
            serviceResult.setMesssage("getRegions catch an exception:" + e.getMessage());

        } finally {
            dispose(conn, stat, rs);
        }
        return serviceResult.toString();
    }

    public String getAllUsers(String jsonArgs, String arguments) {
        ServiceResult serviceResult = new ServiceResult();
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT t1.*,areaname FROM userinfo t1  LEFT JOIN areainfo t2 ON t1.`areacode`=t2.`areacode` WHERE " ;

            JSONObject jsonObject = JSONObject.fromObject(jsonArgs);

            if(jsonObject==null)
            {
                sql+="1==1 ";
            }else
            {
                String areacode=jsonObject.getString("ccode");
                sql+="t1.`areacode` LIKE '"+areacode+"%' AND ";
                String username=jsonObject.getString("uname");
                sql+=" username LIKE '%"+username+"%' AND ";
                String cdate=jsonObject.getString("cdate");
                sql+="createtime like '"+cdate+"%'";
            }
            sql+=" ORDER BY createtime DESC";
            conn = DBAccess.getConnection();
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUsername(rs.getString("username"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setAreaname(rs.getString("areaname"));
                userInfo.setAreacode(rs.getString("areacode"));
                userInfo.setCreatetime(rs.getString("createtime"));
                userInfo.setRemarks(rs.getString("remarks"));
                jsonArray.add(userInfo);
            }
            serviceResult.setCode(0);
            serviceResult.setData(jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
            serviceResult.setCode(-1);
            serviceResult.setMesssage("getAllUsers catch an exception:" + e.getMessage());

        } finally {
            dispose(conn, stat, rs);
        }
        return serviceResult.toString();
    }

    public String getAllAreas(String jsonArgs, String arguments) {
        return "";
    }
}
