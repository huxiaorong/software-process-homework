package com.followmovie.weixinxin.controller;



import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;


public class IndexController extends Controller{
	private List<Record> updateMovie;
	private List<Record> carouselMovie;
	//首页
	public void index() {
		System.out.println("hello---index");
	}
	
	//轮播图
	public void carousel() {
		carouselMovie=Db.find("select * from movie order by releaseYear desc limit 5");
		renderJson(carouselMovie);
	
	}
	
	//最近更新
	public void recentChanges(){
		updateMovie=Db.find("select * from movie order by uploadYear desc limit 6");
		renderJson(updateMovie);	
	}
	
	
	//根据movieId查找电影信息
	public void findMovieById(){
		int movieId=getInt("movieId");
		List<Record> movies=Db.find("select * from movie where movieId="+movieId);
		renderJson(movies);	
	}
	
	
	//根据movieId查找电影类型(最近更新)
	public void recentChangesGetType(){
		recentChanges();
		List<String> strTypeName=new ArrayList<String>();
		for(int i=0;i<updateMovie.size();i++){
			StringBuffer stringBuffer=new StringBuffer();
			List<Record> movieTypeIds=Db.find("select movieTypeId from movietypecomparison where movieId="+updateMovie.get(i).getInt("movieId"));
			for(int j=0;j<movieTypeIds.size();j++){
				String typeNames=Db.queryStr("select name from movietype where movieTypeId="+movieTypeIds.get(j).getInt("movieTypeId"));
				stringBuffer.append(typeNames+"/");				
			}
			String string=stringBuffer.toString();
			string = string.substring(0,string.length()-1);
			strTypeName.add(string.toString());
		}
		renderJson(strTypeName.toString());	
	}
	
	public void findMovieTypeById(){
		int movieId=getInt("movieId");
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
	
		renderJson(strTypeName.toString());	
	}

	
	
	public void findCarouselMovieByPosition(){
		carousel();
		int position=getInt("position");
		renderJson(carouselMovie.get(position).getInt("movieId"));	
	}
	
	
	
	public String findMovieTypeById1(int movieId){
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
	
		System.out.println(strTypeName.size()+"mm");
		return strTypeName.toString();	
	}
	
	public void findMovieByPlaceId(){
		int placeId=getInt("placeId");
		List<Record> movies=new ArrayList<>();
		Record movie=null;
		List<Record>  movieIds =Db.find("select movieId from movieplacecomparison where placeId = "+placeId);
		for(int i=0;i<movieIds.size();i++){
			movie=Db.findFirst("select * from movie where movieId="+movieIds.get(i).getInt("movieId"));
			movies.add(movie);
		}
		renderJson(movies);	
	}
	
	
	public void downImg(){
		String img=get("img");
		System.out.println("mmm"+img);
		
	    File file = new File(IndexController.class.getClassLoader().getResource("../../movies/"+img).getPath());
	    System.out.println("xxx"+file.getAbsolutePath());
		renderFile(file);

	}
	
	
	public void addMovieCollecte(){
		int userId=getInt("userId");
		int movieId=getInt("movieId");
		
		Record r = new Record().set("userId", userId).set("movieId", movieId);
		Db.save("moviecollection", r);
		renderJson("OK");
	}
	
	public void addPlaceCollecte(){
		int userId=getInt("userId");
		int placeId=getInt("placeId");
		System.out.println("11");
		Record r = new Record().set("userId", userId).set("placeId", placeId);
		System.out.println("22");
		Db.save("placecollection", r);
		renderJson("OK");
	}
	
	public void cancelMovieCollecte(){
		int userId=getInt("userId");
		int movieId=getInt("movieId");
		
		Db.delete("delete from moviecollection where userId="+userId +"&& movieId="+movieId);
		renderJson("OK");
	}
	
	public void cancelPlaceCollecte(){
		int userId=getInt("userId");
		int placeId=getInt("placeId");
		
		Db.delete("delete from placecollection where userId="+userId +"&& placeId="+placeId);
		renderJson("OK");
	}

	public void judgePlaceCollected(){
		int userId=getInt("userId");
		int placeId=getInt("placeId");
		Record r=Db.findFirst("select * from placecollection where userId="+userId+"&& placeId="+placeId);
		System.out.println("mmmm"+r);
		if(r==null){
			renderJson("not");
		}else{
			renderJson("yes");
		}
	}
	
	public void judgeMovieCollected(){
		int userId=getInt("userId");
		int movieId=getInt("movieId");
		Record r=Db.findFirst("select * from moviecollection where userId="+userId+"&& movieId="+movieId);
		if(r==null){
			renderJson("not");
		}else{
			renderJson("yes");
		}
	}

	
	public void findSurroundingByPlaceId(){
		int placeId=getInt("placeId");
		List<Record> list=new ArrayList<>();
		List<Record>  surroundingList =Db.find("select surroundingId from surrounding where placeId = "+placeId);
		for(int i=0;i<surroundingList.size();i++){
			Record r=Db.findFirst("select * from place where placeId="+surroundingList.get(i).getInt("surroundingId"));
			list.add(r);
		}
		System.out.println(list.toString());
		renderJson(list);
		
	}
	
	public void findPlaceByMovieId(){
		int movieId=getInt("movieId");
		int pageNumber=getInt("pageNumber");
		Page<Record> placeIds=Db.paginate(pageNumber, 2, "select placeId"," from movieplacecomparison where movieId = "+movieId);
		List<Record> placeList = new ArrayList<>();
		Record place=null;
		
		for(int i=0;i<placeIds.getList().size();i++){
			 place=Db.findFirst("select * from place where placeId = "+placeIds.getList().get(i).getInt("placeId"));
			 placeList.add(place);
		}
		renderJson(placeList);		
	}


	public void guessLike(){
		List<Record> movies=new ArrayList<>();
		List<Record> movieIdList=new ArrayList<>();
		int userId=getInt("userId");
		//System.out.println("ss"+userId+"aa"+pageNumber);
		List<Record> movieId=Db.find("select movieId from moviecollection where userId = "+userId);
		int x=(int)((Math.random()*movieId.size()));
		if(x==movieId.size()){
			x=x-1;
		}
		System.out.println("ww"+movieId+"ss"+x+"cc"+movieId.get(x).getInt("movieId"));
		List<Record> movieTypeIds= Db.find("select movieTypeId from movietypecomparison where movieId = "+movieId.get(x).getInt("movieId"));
		System.out.println("ddd"+movieTypeIds);
		for(int i=0;i<movieTypeIds.size();i++){
			List<Record> r= Db.find("select movieId from movietypecomparison where movieTypeId = "+movieTypeIds.get(i).getInt("movieTypeId"));
			movieIdList.addAll(r);
		}
		for(int i=0;i<movieIdList.size();i++){
			Record m=Db.findFirst("select * from movie where movieId = "+movieIdList.get(i).getInt("movieId"));
			movies.add(m);
		}
		System.out.println("ffffffffffff"+movies.size());
		System.out.println("sssssssss"+movies.toString());
		renderJson(movies);
		
	}
	
	public void recommendMovie(){
		int pageNumber=getInt("pageNumber");
		Page<Record> m=Db.paginate(pageNumber, 10, "select *"," from movie");
		renderJson(m.getList());		
	}

	
	
	
}
