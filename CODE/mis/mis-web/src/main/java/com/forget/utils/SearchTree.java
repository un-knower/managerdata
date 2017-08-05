package com.forget.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @desc 查找树
 *
 *       AC 多模匹配 分为三步 1. 字典树的构造, 按照关键志生成一个查找树. 2.失败链的构造,
 *       最大后缀表示,生成查找失败节点的下一跳(和kmp模式匹配差不多) 3.输出
 *
 */
public class SearchTree {
	// 跟节点
	private Node root = new Node();
	// 生成fail,遍历用
	private List<String> keywords = new ArrayList<String>();

	/**
	 * 添加关键词，生成一个查找树
	 *
	 * 1. 字典树的构造
	 * 
	 * @param keyword
	 */
	public void add(String keyword) {
		if (keyword == null || keyword.length() == 0) {
			return;
		}
		keywords.add(keyword);
		Node currNode = root;
		for (int i = 0, j = keyword.length(); i < j; i++) {
			char c = keyword.charAt(i);
			String value = String.valueOf(c);
			if (currNode.getSons().containsKey(value)) {
				Node selectNode = currNode.getSons().get(value);
				if (i == j - 1) {
					selectNode.setOut(true);
				}
				currNode = selectNode;
			} else {
				Node sNode = new Node();
				sNode.setVal(value);
				sNode.setParent(currNode);
				currNode.addSon(value, sNode);
				if (i == j - 1) {
					sNode.setOut(true);
				}
				currNode = sNode;
			}
		}
	}

	/**
	 * 失败链的构造
	 */
	public void buildFail() {

		root.setFail(root);
		for (String keyword : keywords) {
			// 最大后缀表示
			Node fail = root;
			String prefix = "";
			for (char c : keyword.toCharArray()) {
				prefix += c;
				Node currNode = searchNode(prefix);
				String sval = String.valueOf(c);
				if (fail.getSons().containsKey(sval)) {
					if (fail.getSons().get(sval) != currNode) {
						fail = fail.getSons().get(sval);
						currNode.setFail(fail);
					} else {
						fail = root;
						currNode.setFail(fail);
					}

				} else {
					// 查找后缀，匹配最大
					boolean hasfound = false;
					for (int i = 1; i < prefix.length(); i++) {
						String suffix = prefix.substring(i);
						Node sufNode = searchNode(suffix);
						if (sufNode != null) {
							currNode.setFail(sufNode);
							fail = sufNode;
							hasfound = true;
							break;
						}
					}
					if (!hasfound) {
						currNode.setFail(root);
						fail = root;
					}
				}
			}
		}
	}

	/**
	 * 根据字符串 查找node
	 *
	 * @param keyword
	 * @return
	 */
	private Node searchNode(String keyword) {

		if (keyword.length() == 1) {
			return root.getSons().get(keyword);
		} else {
			Node tree = root.getSons().get(keyword.substring(0, 1));
			for (int i = 1, j = keyword.length(); i < j; i++) {
				char c = keyword.charAt(i);
				String value = String.valueOf(c);

				if (tree != null) {
					if (tree.getSons().containsKey(value)) {
						tree = tree.getSons().get(value);
					} else {
						return null;
					}
				} else {
					return null;
				}

			}
			return tree;
		}
	}

	// 查找关键词
	public Set<String> search(String exp, Set<String> levelSet) {
		Node pathNode = root;
		for (int i = 0, j = exp.length(); i < j; i++) {
			char c = exp.charAt(i);
			String value = String.valueOf(c);
			if (pathNode.getSons().containsKey(value)) {
				pathNode = pathNode.getSons().get(value);
				if (pathNode.isOut()) {
					levelSet.add("[" + pathNode.toString() + "]");
				}
			} else {
				do {
					if (pathNode.isOut()) {
						if (pathNode.isOut()) {
							levelSet.add("[" + pathNode.toString() + "]");
						}
					}
					if (pathNode.getSons().containsKey(value)) {
						pathNode = pathNode.getSons().get(value);
						if (pathNode.isOut()) {
							levelSet.add("[" + pathNode.toString() + "]");
						}
						break;
					}
				} while ((pathNode = pathNode.getFail()) != root);

				if (pathNode == root) {
					if (pathNode.getSons().containsKey(value)) {
						pathNode = pathNode.getSons().get(value);
						if (pathNode.isOut()) {
							levelSet.add("[" + pathNode.toString() + "]");
						}
					}
				}
			}
		}

		return levelSet;
	}

	public Node getRoot() {
		return root;
	}
  
	
	/*
	 * 读取文件内容
	 */
	 public static void readTxtFile(String filePath,SearchTree tree){
        try {
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                
                String line=null;
                while((line=bufferedReader.readLine()) != null){
                	tree.add(line.trim());
                }
                read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
	 
}
