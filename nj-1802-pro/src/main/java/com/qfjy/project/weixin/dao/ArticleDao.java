package com.qfjy.project.weixin.dao;

import java.util.ArrayList;
import java.util.List;

import com.qfjy.project.weixin.bean.resp.Article;

/**
 * 图文model 查询DAO
 */
public class ArticleDao extends BaseDao{

	
	/**
	 * 查询文章列表
	 */
	public List<Article> getFindAll(){
		String sql=" select  aid,title,description,picUrl,url from article ";
		
		List<Article> listArticle= new ArrayList<Article>();
		List list;
		System.out.print(sql);
		try {
			list = super.select(sql);
			if(!list.isEmpty()) {
				for(int i = 0;i < list.size();i++) {
					ArrayList tempRow = (ArrayList) list.get(i);
					Article ar = new Article();
				
					ar.setTitle((String) tempRow.get(1));
					ar.setDescription((String) tempRow.get(2));
					ar.setPicUrl((String) tempRow.get(3));
					ar.setUrl((String) tempRow.get(4));
					listArticle.add(ar);
				}	
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listArticle;
	}
}
