package com.kiosk.dao;

import java.util.HashMap;
import java.util.List;

public interface IMenuDao {
	//메뉴 목록 출력
	public List<HashMap<String, String>> menuList(int num) throws Exception;
}
