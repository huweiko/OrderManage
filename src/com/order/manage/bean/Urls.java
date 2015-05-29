package com.order.manage.bean;

public class Urls {
	
//	public static final String SERVER_IP = "http://stjmry.gicp.net:888/josn.aspx";
	private static Urls instance = null;
	
	public static Urls getInstance(){
		if(instance == null){
			instance = new Urls();
		}
		return instance;
	}
	private Urls(){
		
	}
	private String getServerUrls(){
		return "http://"+SERVER_IP+":888/josn.aspx";
	}
	private String SERVER_IP = "187.7.85.246";

	public String getSERVER_IP() {
		return SERVER_IP;
	}
	public void setSERVER_IP(String sERVER_IP) {
		this.SERVER_IP = sERVER_IP;
	}
	
	public String getSubmitOrder(){
		return getServerUrls() + "?type=post";
	}
	public String getWare(){
		return getServerUrls() + "?type=get";
	}

}
