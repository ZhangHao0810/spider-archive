package com.zhanghao.searchboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhanghao.searchboot.model.Information;
import com.zhanghao.searchboot.service.SearchService;


//@RestController//返回Json 默认自动所有的方法都加 @ResponseBody
//@RequestMapping("/api/search") //定义一个在本类中所有方法的api前缀
@Controller //只要有返回页面的，就要用Controller
public class SearchController {
	
	@Autowired
	SearchService searchService;
	
	@RequestMapping("/api/search")
	@ResponseBody//返回的是 Json 不写的话 ，因为前面写的是@ Controller 所以默认返回一个页面
	public List<Information> search(String word){
		return searchService.search(word);
	}
	
	@RequestMapping("/page/search")//Model 就好比 Model层的Model 可以通过它来给前端jsp传一些[key,value]值。
	public String searchIndexPage(String word,Model m) {
		m.addAttribute("word", word);
		m.addAttribute("list", searchService.search(word));
		return "search";
	}
	
	@RequestMapping("/api/rank")
	@ResponseBody
	public List<Map<String, Object>> searchRank(){
		return searchService.searchRank();
	}
	
	
	@RequestMapping("/page/rank")
	public  String searchRankPage(Model m) {
		m.addAttribute("list", searchService.searchRank());
		return "rank";
	}

}
