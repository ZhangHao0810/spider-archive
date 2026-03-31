package com.zhanghao.search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhanghao.search.model.Information;
import com.zhanghao.search.service.Searchservice;

@Controller
public class SearchController {
	@Autowired
	Searchservice searchservice;
	
	@RequestMapping("/api/search")
	@ResponseBody
	public List<Information> search (String word){
		return searchservice.search(word);
	}
	
	@RequestMapping("/page/search")
	public String searchIndexPage(String word, Model m){
		m.addAttribute("word",word);
		m.addAttribute("list",searchservice.search(word));
		return "search";
	}
	
	@RequestMapping("/api/rank")
	@ResponseBody
	public List<Map<String, Object>> searchRank(){
		return searchservice.searchRunk();
	}
	
	@RequestMapping("/page/rank")
	public String searchRankPage( Model m){
		m.addAttribute("list",searchservice.searchRunk());
		return "rank";
	}
}
