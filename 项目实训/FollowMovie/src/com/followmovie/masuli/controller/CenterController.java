package com.followmovie.masuli.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.followmovie.entity.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;




public class CenterController extends Controller {
	
	public void index(){
		System.out.println("mmmm");
	}
	
	public void register(){
		String phone = get("phone");
		String pwd = get("pwd");
		List<Record> phones = Db.find("select tel from user");
		int i = 0;
		for(;i<phones.size();++i){
			if(phones.get(i).equals(phone)){
				renderJson("您输入的手机号已被注册");
				break;
			}
		}
		if(i == phones.size()){
			Record user = new Record().set("tel", phone).set("password", pwd);
			Db.save("user", user);
			renderJson("注册成功");
		}
	}
	
	public void login(){
		String phone = get("phone");
		String pwd = get("pwd");
		Record user = Db.findFirst("select * from user where tel ='"+ phone + "' && password ='"+pwd+"'");
		if(null == user){
			renderJson("登录失败");
		}else{
			renderJson(user);
		}
		
	}
	
	
	public void getCount(){
		int userId = Integer.parseInt(getPara("userId"));
		
		int dynamicCount = Db.queryInt("SELECT Count(dynamic.id) FROM dynamic WHERE dynamic.id= " + userId);
		int praiseCount = Db.queryInt("select count(*) from praise where userId = " + userId);
		int placeCount = Db.queryInt("SELECT count(id) FROM placecollection where userId =" + userId);
		int movieCount = Db.queryInt("SELECT count(id) FROM moviecollection where userId =" + userId);
		int collectionCount = placeCount + movieCount;
		setAttr("dynamicCount",dynamicCount);
		setAttr("praiseCount",praiseCount);
		setAttr("collectionCount",collectionCount);
		renderJson(new String[]{"dynamicCount","praiseCount","collectionCount"});
	}
	
	public void edit(){
		String username = get("username");
		String sex = get("sex");
		String address = get("address");
		String birth = get("birth");
		Record user = Db.findById("user", get("userId")).set("userName", username).set("sex", sex).set("address", address).set("birth", birth);
		Db.update("user", user);
			
		renderJson("修改成功");
		
	}
	public void numLogin(){
		String phone = get("phone");
		Record user = Db.findFirst("select * from user where tel ="+ phone);
		if(null == user){
			Record user1 = new Record().set("tel", phone);
			Db.save("user", user1);
			renderJson(user1);
		}else{
			renderJson(user);
		}
	}
	public void forget(){
		String phone = get("phone");
		String pwd = get("pwd");
		Record user = Db.findFirst("select * from user where tel ="+ phone);
		if(null == user){
			renderJson("手机号输入有误");
		}else{
			Record user1 = Db.findById("user", user.get("id")).set("password", pwd);
			Db.update("user", user1);
			renderJson(user1);
		}
	}
	public void modifyNum(){
		String phone = get("phone");
		String newPhone = get("newPhone");
		Record user2 = Db.findFirst("select * from user where tel ="+ newPhone);
		if(null == user2){
			Record user = Db.findFirst("select * from user where tel ="+ phone);
			Record user1 = Db.findById("user", user.get("id")).set("tel", newPhone);
			Db.update("user", user1);
			renderJson(user1);
		}else{
			renderJson("您输入的手机号已存在");
		}
	}
	public void changePwd(){
		String phone = get("phone");
		String pwd = get("pwd");
		String pwd1 = get("newPwd");
		Record user = Db.findFirst("select * from user where tel ='"+ phone + "' && password ='"+pwd+"'");
		Record user1 = Db.findById("user", user.get("id")).set("password", pwd1);
		Db.update("user", user1);
		renderJson("修改成功");
	}
	public void cancellation(){
		int userId = Integer.parseInt(getPara("userId"));
		Db.delete("delete from user where id = "+userId);
		renderJson("注销成功");
		
	}
	public void sendMessage(){
		String content = get("etContent");
		String email = get("etMail");
		int userId = Integer.parseInt(getPara("userId"));
		Record opinion = new Record().set("content", content).set("email", email).set("userId", userId).set("date", new Date());
		Db.save("opinion", opinion);
		renderJson("发送成功");
	}
	
	public void qqLogin(){
		String qqId = get("qqId");
		System.out.println(qqId);
		String username = get("username");
		Record user = Db.findFirst("select * from user where qqId = '"+ qqId +"'" );
		if(user == null){
			Record user1 = new Record().set("qqId", qqId).set("username", username);
			Db.save("user", user1);
			renderJson(user1);
		}else{
			renderJson(user);
		}
		
	}
	
	public void upload() throws IOException{
		UploadFile upfile = getFile();
		File file = upfile.getFile();
		String fileName = new SimpleDateFormat("yyyyMMddkkmmss").format(new Date())+".jpg";
		String path = "E:/learning/java企业级应用开发一/demo/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/FollowMovie/headPicture/"+fileName;
		File saveFile = new File(path);
		upfile.getFile().renameTo(saveFile);
		String id = get("userId");
		Record user1 = Db.findById("user", id).set("headPicture", fileName);
		Db.update("user", user1);
		renderJson(fileName);
	}
	
	public String findMovieTypeById(int movieId){
		List<String> strTypeName=new ArrayList<String>();
		
		StringBuffer stringBuffer=new StringBuffer();
		List<Record> movieTypeIds=Db.find("select movieTypeId from movietypecomparison where movieId="+movieId);
		for(int j=0;j<movieTypeIds.size();j++){
			String typeNames=Db.queryStr("select name from movietype where movieTypeId="+movieTypeIds.get(j).getInt("movieTypeId"));
			stringBuffer.append(typeNames+"/");				
		}
		String string=stringBuffer.toString();
		string = string.substring(0,string.length()-1);
		strTypeName.add(string.toString());
	
		String type=strTypeName.toString().substring(1,strTypeName.toString().length()-1);
		return type;	
	}

	
	public void findMovieType(){
		String movies=get("strMovies");
		List<Movie> mList=new ArrayList<>();
		List<String> types=new ArrayList<>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        mList = gson.fromJson(movies, new TypeToken<List<Movie>>() {
         }.getType());
         
         for(int i=0;i<mList.size();i++){
        	 String string=findMovieTypeById(mList.get(i).getMovieId());
        	 types.add(string);
         }
         System.out.println("==="+types);
         renderJson(types);
	}

	public void getCollection(){
		int userId = Integer.parseInt(getPara("userId"));
		List<Record> movies = Db.find("select movie.* from moviecollection,movie where userId = "+userId+"&& movie.movieId = moviecollection.movieId");
		System.out.println(movies);
		renderJson(movies);
	}
	
	public void getPlace(){
		int userId = Integer.parseInt(getPara("userId"));
		List<Record> place = Db.find("select place.* from placecollection,place where userId = "+userId+"&& place.placeId = placecollection.placeId");
		System.out.println(place);
		renderJson(place);
	}
	
	public void QQphone(){
		int userId = Integer.parseInt(get("id"));
		String phone = get("phone");
		Record user1 = Db.findFirst("select * from user where tel = "+phone );
		if(user1 == null){
			Record user2 = Db.findById("user", userId).set("tel", phone);
			Db.update("user", user2);
			renderJson(user2);
		}else{
			renderJson("手机号已被注册");
		}
		
	}
	
}
