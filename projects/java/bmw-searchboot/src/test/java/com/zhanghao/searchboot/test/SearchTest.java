package com.zhanghao.searchboot.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhanghao.searchboot.MainApplication;
import com.zhanghao.searchboot.model.Information;
import com.zhanghao.searchboot.service.SearchService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class SearchTest {

	private static final Logger LOG= LoggerFactory.getLogger(SearchTest.class);
	
	@Autowired
	SearchService searchservice;
	
	
	@Test
	public void searchtest(){
		List<Information> informations =searchservice.search("刘鹏");
	    System.out.println(JSON.toJSON(informations));
	}
	
	@Test
	public void inserttest(){
		searchservice.importNews();  
	}
}
