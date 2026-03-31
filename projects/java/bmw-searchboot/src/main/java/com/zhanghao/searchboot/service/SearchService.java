package com.zhanghao.searchboot.service;

import java.util.List;
import java.util.Map;

import com.zhanghao.searchboot.model.Information;

public interface SearchService {

	
	List<Information> search(String word);

	void importNews();
	
	List<Map<String, Object>> searchRank();
}
