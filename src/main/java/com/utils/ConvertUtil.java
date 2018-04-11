/*	
 * @(#)ConvertUtil.java     1.0 2010/10/12		
 * 		
 * Copyright (c) 2010 SHANGHAI BINGKUN DIGITAL TECHNOLOGY CO.,LTD		
 * All rights reserved		
 * 		
 * This software is the confidential and proprietary information of 		
 * SHANGHAI BINGKUN.("Confidential Information").  You shall not		
 * disclose such Confidential Information and shall use it only in		
 * accordance with the terms of the license agreement you entered into		
 * with SHANGHAI BINGKUN.		
 */
package com.utils;

import java.io.*;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * <p>
 * 共通 转换Util
 * </p>
 * 
 * @author lipc
 * 
 */
@SuppressWarnings("unchecked")
public class ConvertUtil {



	/**
	 * obj 转换成字符串
	 * 
	 * @param obj
	 * @return obj：1）为空时返回""；
	 * 				2）匹配字符串类型时转为字符串；
	 * 				3）其他情况将返回obj.toString()
	 */
	public static String getString(Object obj) {
		if (null == obj) {
			return "";
		}
		if (obj instanceof String) {
			return (String) obj;
		} else {
			return obj.toString();
		}
	}
	
	public static float getFloat(Object obj){
		float res = 0;
		if(obj instanceof Integer){
			res = (Integer)obj;
		}else if(obj instanceof Float){
			res = (Float)obj;
		}else if(obj instanceof Number){
			Number num = (Number)obj;
			res = num.floatValue();
		}else{
			try{
				res= Float.parseFloat(getString(obj));
			}catch(Exception e){}
		}
		return res;
	}
	
	public static String obj2Price(Object obj){
		float price = getFloat(obj);
		DecimalFormat df = new DecimalFormat("0.00"); 
		return df.format(price);
	}
	/**
	 * obj 转换成整形
	 * @param obj
	 * @return
	 * 
	 * */
	public static int getInt(Object obj){
		//对象为空返回0
		if(null==obj){
			return 0;
		}
		//对像为Integer类型直接返回
		if(obj instanceof Integer){
			return (Integer)obj;
		}
		//对象为String类型
		else if(obj instanceof String){
			try{
				return Integer.parseInt(String.valueOf(obj));
			}catch(Exception e){
				return 0;
			}
		}else if(obj instanceof Number){
			Number num = (Number)obj;
			return num.intValue();
		}else{
			return 0;
		}
	}

	

	
	public static void addMapToList(Map<String, Object> map, List<Map<String, Object>> list) {
		if(list == null || map == null || map.isEmpty()) {
			return;
		}
		if(list.isEmpty()) {
			list.add(map);
		} else {
			boolean exitMap = false;
			for(Map<String, Object> m: list) {
				if(m.get("path") != null && map.get("path") != null && m.get("path").toString().equals(map.get("path").toString())) {
					exitMap = true;
					break;
				}
			}
			if(!exitMap) {
				list.add(map);
			}
		}
	}
	

	
	
	/**
	 * 在最底层的节点中添加一个虚节点,用于上层父节点的展开
	 * @param list
	 */
	public static void addArtificialCounterDeep(List list){
		if (list!=null && !list.isEmpty()){
			for (int i=0;i<list.size();i++){
				HashMap map = (HashMap)list.get(i);
				List childList = (List)map.get("nodes");
				if (childList !=null && !childList.isEmpty()){
					addArtificialCounterDeep(childList);
				}else{
					List artificialList = new ArrayList();
					HashMap artificialMap = new HashMap();
					artificialMap.put("name","artificialCounter");
					artificialMap.put("id","artificialCounter");
					artificialList.add(artificialMap);
					map.put("nodes", artificialList);
				}
			}
		}
	}
	
	/**
	 * 以二进制方式克隆对象  
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static Object byteClone(Object src) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		out.writeObject(src);
		out.close();
		ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream in = new ObjectInputStream(bin);
		Object clone = in.readObject();
		in.close();
		return (clone);
	}  
	
	/**
	 * 用DTO2来覆盖DTO1
	 * 
	 * @param dto1 
	 * 			需要覆盖的DTO
	 * @param dto2
	 * 			用来覆盖的DTO
	 * @param flg 
	 * 			true:空值也覆盖   false:空值不覆盖
	 * @return
	 * 
	 * @throws Exception 
	 */
	public static <T> void convertDTO(T dto1, T dto2, boolean flg) throws Exception{
		if (null != dto1 && null != dto2) {
			Method[] mdArr2 = dto2.getClass().getMethods();
			for (Method md2 : mdArr2) {
				if (md2.getName().indexOf("get") == 0) {
					Object value = md2.invoke(dto2);
					if (!flg && (null == value || "".equals(value))) {
						continue;
					}
					Method[] mdArr1 = dto1.getClass().getMethods();
					String mdName = "set" + md2.getName().substring(3);
					for (Method md1 : mdArr1) {
						if (mdName.equals(md1.getName())) {
							md1.invoke(dto1, value);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * 用DTO2来覆盖DTO1
	 * 
	 * @param dto1 
	 * 			需要覆盖的DTO
	 * @param dto2
	 * 			用来覆盖的DTO
	 * @param flg 
	 * 			true:空值也覆盖   false:空值不覆盖
	 * @return
	 * 
	 * @throws Exception 
	 */
	public static <T> void convertNewDTO(T dto1, T dto2, boolean flg) throws Exception{
		if (null != dto1 && null != dto2) {
			Method[] mdArr2 = dto2.getClass().getMethods();
			for (Method md2 : mdArr2) {
				Class[] ptypes = md2.getParameterTypes();
				if (md2.getName().indexOf("get") == 0 && null != ptypes && ptypes.length == 0) {
					Object value = md2.invoke(dto2);
					if (!flg && (null == value || "".equals(value))) {
						continue;
					}
					Method[] mdArr1 = dto1.getClass().getMethods();
					String mdName = "set" + md2.getName().substring(3);
					for (Method md1 : mdArr1) {
						if (mdName.equals(md1.getName())) {
							md1.invoke(dto1, value);
							break;
						}
					}
				}
			}
		}
	}
	

	/**
	 * 返回字符串的字符长度,汉字的长度为2
	 * 
	 * 
	 * */
	public static int getStringLength(String str){
		
		int length = 0;
		if(null == str || str.length() == 0){
			return length;
		}
		//将字符串转化成字符数组
		char[] charArr = str.toCharArray();
		for(char tempChar : charArr){
			//如果是汉字则长度加2
			if(tempChar >= 0x0391 && tempChar <= 0xFFE5){
				length += 2;
			}else{
				length ++;
			}
		}
		return length;
	}
	


	/**
	 * map转list
	 * @param map
	 * @return
	 */
	public static List<Map<String, Object>> map2List(Map<String, Object> map){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(null != map){
			for(Map.Entry<String,Object> en: map.entrySet()){
				Map<String, Object> temp = (Map<String, Object>)en.getValue();
				list.add(new HashMap<String,Object>(temp));
			}
		}
		return list;
	}
	
	/**
	 * 取得不重复的KEY集合
	 * @param srcList
	 * @param key
	 * @return
	 */
	public static List<String> getKeyList(List<Map<String,Object>> srcList, String key){
		List<String> keyList = new ArrayList<String>();
		if(null != srcList){
			for(Map<String,Object> map : srcList){
				String value = getString(map.get(key));
				if(!keyList.contains(value)){
					keyList.add(value);
				}
			}
		}
		return keyList;
	}
	
	/**
	 * List复制
	 * @param srcList
	 * @return
	 */
	public static List<Map<String,Object>> copyList(List<Map<String,Object>> srcList){
		List<Map<String,Object>> list = null;
		if(null != srcList){
			list = new ArrayList<Map<String,Object>>();
			for (Map<String,Object> src : srcList) {
				list.add(new HashMap<String, Object>(src));
			}
		}
		return list;
	}
	
	/**
	 * 把树中不为柜台的叶子节点删除掉
	 * 
	 * @param treeList 待处理树型结构
	 */
	public static void cleanTreeList(List<Map<String, Object>> treeList) {
		for(int i = 0; i < treeList.size(); i++) {
			Map<String, Object> map = treeList.get(i);
			List<Map<String, Object>> nextList = (List)map.get("nodes");
			if(nextList != null && !nextList.isEmpty()) {
				cleanTreeList(nextList);
				if(nextList.isEmpty()) {
					treeList.remove(i);
					i--;
				}
			} else {
				Object type = map.get("type");
				if(type != null && "4".equals(type.toString())) {
					continue;
				} else {
					treeList.remove(i);
					i--;
				}
			}
		}
	}
	
	/**
	 * 把树中柜台节点删除掉
	 * 
	 * @param treeList 待处理树型结构
	 */
	public static void cleanTreeList2(List<Map<String, Object>> treeList) {
		for(int i = 0; i < treeList.size(); i++) {
			Map<String, Object> map = treeList.get(i);
			List<Map<String, Object>> nextList = (List)map.get("nodes");
			if(nextList != null && !nextList.isEmpty()) {
				cleanTreeList2(nextList);
			} else {
				Object type = map.get("type");
				if(type != null && "4".equals(type.toString())) {
					treeList.remove(i);
					i--;
				}
			}
		}
	}
	
	/**
	 * 把树结构转化成按部门类型分的结构
	 * 
	 * @param treeList 树结构List
	 * @param resultMap 按部门类型分的结构
	 */
	public static void getDepTypeList(List<Map<String, Object>> treeList, Map<String, Object> resultMap) {
		if(treeList != null && !treeList.isEmpty()) {
			for(int i = 0; i < treeList.size(); i++) {
				Map<String, Object> map = treeList.get(i);
				String type = (String)map.get("departType");
				Map<String, Object> temp = new HashMap<String, Object>(map);
				temp.remove("nodes");
				if(resultMap.containsKey(type)) {
					((List)resultMap.get(type)).add(temp);
				} else {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					list.add(temp);
					resultMap.put(type, list);
				}
				List<Map<String, Object>> nextList = (List)map.get("nodes");
				if(nextList != null && !nextList.isEmpty()) {
					getDepTypeList(nextList, resultMap);
				}
			}
		}
	}
	

	
	/**
	 * 判断字符串是否为空或者空字符串 如果字符串是空或空字符串则返回true，否则返回false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * MD5加密
	 * 
	 * @param secret_key
	 * @return
	 */
	public static String createSign(String secret_key) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(secret_key.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	
	public static List<Map<String, Object>> getTreeList(List<Map<String, Object>> list){
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();
		if(null != list && list.size() > 0){
			for(Map<String, Object> map : list){
				String parentId = getString(map.get("parentId"));
				// 根节点
				if("".equals(parentId)){
					treeList.add(map);
				}else{
					for(Map<String, Object> tree : treeList){
						// 发现父节点
						Map<String, Object> pnode = getNode(tree,parentId);
						// 发现父节点
						if(null != pnode){
							List<Map<String, Object>> nodes = (List<Map<String, Object>>)pnode.get("nodes");
							if(null == nodes){
								nodes = new ArrayList<Map<String,Object>>();
								pnode.put("nodes", nodes);
							}
							nodes.add(map);
							break;
						}
					}
				}
			}
		}
		return treeList;
	}

	/**
	 * 递归查询：根据ID查询节点
	 * @param node
	 * @param id
	 * @return
	 */
	public static Map<String, Object> getNode(Map<String, Object> node,String id){
		Map<String, Object> returnNode = null;
		String id_n = ConvertUtil.getString(node.get("id"));
		if(id_n.equals(id)){
			returnNode = node;
		}else{
			List<Map<String, Object>> nodes = (List<Map<String, Object>>)node.get("nodes");
			if(null != nodes && nodes.size() > 0){
				for(Map<String, Object> tempNodes : nodes){
					returnNode = getNode(tempNodes,id);
					if(null != returnNode){
						break;
					}
				}
			}
		}
		return returnNode;
	}
	
	/**
	 * 取得不重复的List
	 * @param srcList
	 * @param key
	 * @return
	 */
	public static List<Map<String,Object>> getNotRepeatList(List<Map<String,Object>> srcList,String key){
		List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
		List<String> keyList = new ArrayList<String>();
		if(null != srcList){
			for(Map<String,Object> map : srcList){
				String value = getString(map.get(key));
				if(!keyList.contains(value)){
					keyList.add(value);
					resList.add(map);
				}
			}
		}
		return resList;
	}
}
