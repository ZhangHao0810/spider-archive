package com.zhanghao.search.service;

import java.util.List;
import java.util.Map;

import com.zhanghao.search.model.Information;

public interface Searchservice  {

	List<Information> search(String word);

	void importNews();
	
	List<Map<String, Object>> searchRunk();

}
