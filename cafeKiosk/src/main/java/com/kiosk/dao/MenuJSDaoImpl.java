package com.kiosk.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.kiosk.vo.MenuVo;

@Primary
@Repository
public class MenuJSDaoImpl implements MenuJSDao{ 
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	public MenuJSDaoImpl(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public void setSqlMapClient(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public int menuCount(HashMap<String,String> map) {
		return sqlSessionTemplate.selectOne("countMenu", map);
	}
	
	@Override
	public List<MenuVo> MenuList(HashMap<String, String> map) {
		return sqlSessionTemplate.selectList("menuList", map);
	}
	
	@Override
	public String Pass() {
		return sqlSessionTemplate.selectOne("Pass");
	}
	
	@Override
	public int menuDelete(String number) {
		return sqlSessionTemplate.delete("menuDelete", number);
	}
	
	@Override
	public List<MenuVo> categoryList() {
		return sqlSessionTemplate.selectList("getCategory");
	}
	
	@Override
	public int menuCheck(MenuVo vo) {
		return sqlSessionTemplate.selectOne("menuChk", vo);
	}
	
	@Override
	public void menuInsert(MenuVo vo) {
		sqlSessionTemplate.insert("menuInsert", vo);
	}
	
	@Override
	public void menuImageInsert(MenuVo vo) {
		sqlSessionTemplate.insert("menuImageInsert", vo);
	}
	
	@Override
	public MenuVo menuSelect(int num) {
		return sqlSessionTemplate.selectOne("menuSelect", num);
	}
	
	@Override
	public int changeActivation(MenuVo vo) {
		return sqlSessionTemplate.update("changeActivation", vo);
	}
}
