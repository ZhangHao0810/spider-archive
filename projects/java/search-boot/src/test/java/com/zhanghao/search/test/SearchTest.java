package com.zhanghao.search.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhanghao.search.SearchApplication;
import com.zhanghao.search.model.Information;
import com.zhanghao.search.service.Searchservice;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class SearchTest {

	private static final Logger LOG= LoggerFactory.getLogger(SearchTest.class);
	
	@Autowired
	Searchservice searchservice;
	
	@Test
	public void test(){
		List<Information> informations =searchservice.search("刘鹏");
	    System.out.println(JSON.toJSON(informations));
	}
}
