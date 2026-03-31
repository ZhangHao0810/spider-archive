package com.zhanghao.search.dao;

import java.util.List;
import com.zhanghao.search.model.Information;
import com.zhanghao.search.utils.bean.CommonQueryBean;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * Information数据库操作接口类
 * 
 **/

@Repository
public interface InformationDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Information  selectByPrimaryKey ( @Param("id") Long id );

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey ( @Param("id") Long id );

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert( Information record );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective( Information record );

	/**
	 * 
	 * list分页查询
	 * 
	 **/
	List<Information> list4Page (@Param("record") Information record, @Param("commonQueryParam") CommonQueryBean query);

	/**
	 * 
	 * count查询
	 * 
	 **/
	long count ( Information record);

	/**
	 * 
	 * list查询
	 * 
	 **/
	List<Information> list ( Information record);

	/**
	 * 模糊匹配
	 * @param word
	 * @return
	 */
	List<Information> match(String word);

	int insertIfNotExist(Information info);

}