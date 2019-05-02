package com.example.administrator.batheasy.InternalWithServer;//package messages;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Hashtable;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import bath.BathRoom;
//import bath.BathRoomState;
//import net.sf.ezmorph.object.DateMorpher;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
///*
// * private String character;
//	private String command;
//	private String UTel;
//	private String UName;
//	private String UPwd;
//	private String USex;
//	private String USchool;
// */
//import net.sf.json.util.JSONUtils;
//import utils.JsonBeanTrans;
//
//public class test {
//
//	public static void main(String[] args) throws ParseException {
//		UserRegister r1 = new UserRegister();
//		r1.setCharacter("User");
//		r1.setCommand("ע��");
//		r1.setUName("q");
//		r1.setUPwd("23");
////		r1.setUSchool("whut");
////		r1.setUSex("��");
//		JSONObject j = JSONObject.fromObject(r1);
//		String jstr = j.toString();
//		String name = j.getString("UName");
//		System.out.println(jstr + " " + name);
//
//		Date d1 = new Date();
//		//BathRoom bRoom1 = new BathRoom(1, 1, BathRoomState.FREE, "", d1);
//		Date d2 = new Date();
//		//BathRoom bRoom2 = new BathRoom(2, 1, BathRoomState.USING, "1564898731", d2);
//		List<BathRoom> bList = new ArrayList<BathRoom>();
//		bList.add(bRoom1);
//		bList.add(bRoom2);
////		Map<String, BathRoomState> bRoomStateList = new Hashtable<String, BathRoomState>();
////		bList.forEach((x) -> bRoomStateList.put("" + x.getRNo(), x.getRState()));
//		ServerReturnBathRooms ss = new ServerReturnBathRooms();
//		ss.setbRoomList(bList);
//		ss.setCharacter("Server");
//		ss.setCommand("�������");
//		
////		JSONObject j1 = JSONObject.fromObject(ss);
////		String js = j1.toString();
////		System.out.println(js);
//		
//		//JSONObject js = JsonBeanTrans.beanToJson(ss, "yyyy-MM-dd HH:mm:ss");
//		JSONObject js = JSONObject.fromObject(ss);
//		System.out.println(js.toString());
//
//		ServerReturnBathRooms s = (ServerReturnBathRooms)JsonBeanTrans.jsonToBean(js, ServerReturnBathRooms.class);
//		System.out.println(s.getCharacter() + s.getCommand() + s.getbRoomList());
//		
////		String[] formats = { "yyyy-MM-dd HH:mm:ss" };
////        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(formats));
////        Map<String, BathRoomState> m = (Map<String, BathRoomState>)js.get("bRoom");
////        for(String i : m.keySet()) {
////        	System.out.println(i + " : " + m.get(i));
////        }
//		JSONArray broomlist = (JSONArray)js.get("bRoomList");
//		ArrayList list = new ArrayList();
//		for(int i = 0; i < broomlist.size(); i++) {
//			JSONObject jo = (JSONObject)broomlist.get(i);
//			System.out.println(jo.toString());
//			list.add(jo.get("startTime"));
//		}
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).toString());
//		}
//
//		
//	}
//
//}
