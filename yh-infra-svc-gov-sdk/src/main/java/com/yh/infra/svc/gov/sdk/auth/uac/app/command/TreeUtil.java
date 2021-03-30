package com.yh.infra.svc.gov.sdk.auth.uac.app.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 树的工具类，生成前台生成树所需的xml格式或json格式
 * 
 * @author 李舜
 * 
 */
public class TreeUtil<T extends Treeable> {
	private TreeNode rootNode = new TreeNode();
	
	/**
	 * 获取根节点
	 * @param objs
	 * @return
	 */
	public TreeNode getRootNode(Collection<T> objs) {
		getColl(objs);
		return rootNode;
	}

	/**
	 * 根据对象集合生成相应的XML结构
	 * @param objs
	 * @return
	 */
	public String objectToXML(Collection<T> objs) {
		getColl(objs);
//		List<TreeNode> list = getColl(objs);
//		recursionFn(list,rootNode);
//		return rootNode.getAllXML();
		String xml = getXML(rootNode);
//		System.out.println(xml);
		return xml;
	}
	
	/**
	 * 根据对象集合生成select的options
	 * @param objs
	 * @return
	 */
	public String objectToOption(Collection<T> objs) {
		getColl(objs);
		String option = getOption(rootNode);
		return option;
	}
	
	/**
	 * 根据对象集合生成select的json格式
	 * @param objs
	 * @return
	 */
	public String objcetToJson(Collection<T> objs) {
		getColl(objs);
		String json = getJson(rootNode);
		return json;
	}
	
	private String getJson(TreeNode treeNode) {
		if(treeNode.getChildren().size()>0) {
			String json = "";
			for(TreeNode tn : treeNode.getChildren()) {
				json += getJson(tn);
			}
			json = addJsonSplit(json);
			treeNode.setEndJson(json);
			return treeNode.getAllJson();
		}else {
			return treeNode.getAllJson();
		}
	}
	
	private String addJsonSplit(String json1) {
//		System.out.println("原来是："+json1);
		String result = "";
		String[] jsons = json1.split("  ");
		for(String o : jsons) {
			String s = o.substring(0,o.indexOf("--")+12);
			String m = "&nbsp;&nbsp;&nbsp;";
			String e = o.substring(o.indexOf("--")+12);
			result += " "+s+m+e+" ";
		}
//		System.out.println("改变后："+result);
		return result;
	}
	
	/**
	 * 根据树形对象整合Options
	 * @param treeNode 最上层节点
	 * @return 所有XML
	 */
	private String getOption(TreeNode treeNode) {
		if(treeNode.getChildren().size()>0) {
			String option = "";
			for(TreeNode tn : treeNode.getChildren()) {
				option += getOption(tn);
			}
			option = addOptionSplit(option);
			treeNode.setEndOption(option);
			return treeNode.getAllOption();
		}else {
			return treeNode.getAllOption();
		}
	}
	
	private String addOptionSplit(String options1) {
//		System.out.println("原来是："+options1);
		String result = "";
		String[] options = options1.split("  ");
		for(String o : options) {
			String s = o.substring(0,o.indexOf(">")+1);
			String m = "&nbsp;&nbsp;&nbsp;";
			String e = o.substring(o.indexOf(">")+1);
			result += " "+s+m+e+" ";
		}
//		System.out.println("改变后："+result);
		return result;
	}
	
	/**
	 * 根据树形对象整合XML
	 * @param treeNode 最上层节点
	 * @return 所有XML
	 */
	private String getXML(TreeNode treeNode) {
		if(treeNode.getChildren().size()>0) {
			String xml = "";
			for(TreeNode tn : treeNode.getChildren()) {
				xml += getXML(tn);
			}
			treeNode.setMidXML(xml);
			return treeNode.getAllXML();
		}else {
			return treeNode.getAllXML();
		}
	}

	/**
	 * 获取排序好的集合
	 * 
	 * @param objs
	 */
	public List<TreeNode> getColl(Collection<T> objs) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		for (T obj : objs) {
			if ((Integer)obj.getParentId() == 0) {
				rootNode.setData(obj);
			} else {
				TreeNode node = new TreeNode();
				node.setData(obj);
				list.add(node);
			}
		}
		recursionFn(list, rootNode);
		return list;
	}

	private void recursionFn(Collection<TreeNode> coll, TreeNode node) {
		if (hasChild(coll, node)) {
			Collection<TreeNode> childList = getChildList(coll, node);
			node.setChildren(childList);
			Iterator<TreeNode> it = childList.iterator();
			while (it.hasNext()) {
				TreeNode n = it.next();
				recursionFn(coll, n);
			}
		} else {

		}
	}

	private boolean hasChild(Collection<TreeNode> coll, TreeNode node) {
		return getChildList(coll, node).size() > 0 ? true : false;
	}

	private Collection<TreeNode> getChildList(Collection<TreeNode> coll,
                                              TreeNode node) {
		List<TreeNode> li = new ArrayList<TreeNode>();
		Iterator<TreeNode> it = coll.iterator();
		while (it.hasNext()) {
			TreeNode n = it.next();
			if (n.getParentId().equals(node.getId())) {
				li.add(n);
			}
		}
		return li;
	}
}
