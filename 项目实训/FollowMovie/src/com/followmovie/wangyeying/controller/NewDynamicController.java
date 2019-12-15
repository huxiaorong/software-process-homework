package com.followmovie.wangyeying.controller;



import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.followmovie.model.Dynamic;
import com.followmovie.model.DynamicPicture;
import com.followmovie.model.User;
import com.followmovie.model.praise;
import com.followmovie.utils.DBUtil;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.upload.UploadFile;





public class NewDynamicController extends Controller{
	
	private static int dynamicId = dynamicCount()+1;
	
	public void insertDynamic(){
		String userId = getPara("userId");
		int id = Integer.parseInt(userId);
		String blog = getPara("blog");
		String sql = "select * from user where id = ?";
		User user = (User) User.dao.findFirst(sql,id);
		new Dynamic().set("dynamicId", dynamicId)
					.set("id",userId)
					.set("blog", blog)
					.set("userName",user.get("userName"))
					.set("headPicture",user.get("headPicture"))
					.set("likeCount", 0)
					.save();
		
		renderJson("success");
		
	}
	
	public void insertDynamicPicture(){
		UploadFile upfile = getFile();
		File file = upfile.getFile();
		String c = getPara("count");
		int count = Integer.parseInt(c);
		String realPath = "E:/learning/java企业级应用开发一/demo/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/FollowMovie/dynamic";
		String fileName = file.getName();
		fileName = new SimpleDateFormat("yyyyMMddkkmmss").format(new Date()) + fileName;
		File saveFile = new File(realPath + "/" + fileName);
		upfile.getFile().renameTo(saveFile);
		Connection con = null;
		PreparedStatement pstm;
		con = DBUtil.getCon();
		String str = "select * from dynamicpicture where id = '"+dynamicId+"' "; 
		List<DynamicPicture> pic = DynamicPicture.dao.find(str);
		if(pic.size()==0){
			DynamicPicture.dao.set("id", dynamicId).save();
		}
		try {
			String line = "img"+count;
			String sql = null;
			if(count==1){
				sql = "update dynamicpicture set img1 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==2){
				sql = "update dynamicpicture set img2 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==3){
				sql = "update dynamicpicture set img3 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==4){
				sql = "update dynamicpicture set img4 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==5){
				sql = "update dynamicpicture set img5 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==6){
				sql = "update dynamicpicture set img6 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==7){
				sql = "update dynamicpicture set img7 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==8){
				sql = "update dynamicpicture set img8 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}else if(count==9){
				sql = "update dynamicpicture set img9 = '"+fileName+"' where id = '"+dynamicId+"' "; 
			}
			pstm = con.prepareStatement(sql);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson("success");
	}

	
	public static int dynamicCount(){
		String sql = "select * from dynamic";
		List<Dynamic> list = Dynamic.dao.find(sql);
		int count = list.size();
		return count;
	}
	
	
	public void getAllDynamic(){
		String sql = "select * from dynamic order by dynamicId desc";
		List<Dynamic> list = Dynamic.dao.find(sql);
		renderJson(list);
	}
	
	public void getAllPicture(){
		String sql = "select * from dynamicPicture";
		List<DynamicPicture> picList = DynamicPicture.dao.find(sql);
		renderJson(picList);
	}
	
	public void getAllLike(){
		String userId = getPara("userId");
		int uid = Integer.parseInt(userId);
		String likeSql = "select dynamicId from praise where userId = ?";
		List<praise> list = praise.dao.find(likeSql,userId);
		List<Integer> dynamicId = new ArrayList<Integer>();
		for(int i = 0;i<list.size();i++){
			dynamicId.add(list.get(i).get("dynamicId"));
		}
		System.out.println(dynamicId.toString());
		renderJson(dynamicId);
	}
	
	public void plusOrMinus(){
		String id = getPara("dynamicId");
		int di = Integer.parseInt(id);
		String before = getPara("userId");
		int userId = Integer.parseInt(before);
		String msg = getPara("what");
		int single = 0;
		Connection con=null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		try {
			con = DBUtil.getCon();
			if(msg.equals("plus")){
				pstm = con.prepareStatement("update dynamic set likeCount=likeCount+1 where dynamicId=?");
				pstm2 = con.prepareStatement("insert into praise values(?,?)");
			}else if(msg.equals("minus")){
				pstm = con.prepareStatement("update dynamic set likeCount=likeCount-1 where dynamicId=?");
				pstm2 = con.prepareStatement("delete from praise where userId=? and dynamicId=?");
			}
			pstm.setInt(1,di);
			pstm2.setInt(1, userId);
			pstm2.setInt(2, di);
			pstm.executeUpdate();
			pstm2.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson();
		
	}

	


}
