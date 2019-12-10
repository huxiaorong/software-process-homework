package rainbow.masuli.controller;


import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;




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
		
		int dynamicCount = Db.queryInt("SELECT Count(dynamic.dynamicId) FROM dynamic WHERE dynamic.userId = " + userId);
		int praiseCount = Db.queryInt("select count(id) from praise where userId = " + userId);
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
	
}
