package com.zhanghao.search.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class EntityUtil {

	private final String type_char = "char";

	private final String type_date = "date";

	private final String type_timestamp = "timestamp";

	private final String type_int = "int";

	private final String type_bigint = "bigint";

	private final String type_text = "text";

	private final String type_bit = "bit";

	private final String type_decimal = "decimal";

	private final String type_blob = "blob";

	private final String moduleName = "search"; // 鏁版嵁搴撳悕绉?

	private final String bean_path = "d:\\DATA\\mybatistest\\bean\\";

	private final String mapper_path = "d:\\DATA\\mybatistest\\mapper\\";

	private final String xml_path = "d:\\DATA\\mybatistest\\xml\\";// xml璺緞

	private final String model_package = "com.zhanghao.search.model";// model鐨勫寘鎵€鍦?

	private final String dao_package = "com.zhanghao.search.dao";// dao鐨勫寘鎵€鍦?

	private final String driverName = "com.mysql.jdbc.Driver";

	private final String user = "root";// 鏁版嵁搴撶敤鎴?

	private final String password = "change-me";// "root";//鏁版嵁搴撳瘑鐮?

	private final String url = "jdbc:mysql://127.0.0.1:3306/search?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false";

	private String tableName = null;

	private String beanName = null;

	private String mapperName = null;

	private Connection conn = null;

	private void init() throws ClassNotFoundException, SQLException {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
	}

	/**
	 * 鑾峰彇鎵€鏈夌殑琛?
	 *
	 * @return
	 * @throws SQLException
	 */
	private List<String> getTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		PreparedStatement pstate = conn.prepareStatement("show tables");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString(1);
			// if ( tableName.toLowerCase().startsWith("yy_") ) {
			tables.add(tableName);
			// }
		}
		return tables;
	}

	private void processTable(String table) {
		StringBuffer sb = new StringBuffer(table.length());
		String tableNew = table.toLowerCase();
		String[] tables = tableNew.split("_");
		String temp = null;
		for (int i = 0; i < tables.length; i++) {
			temp = tables[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		beanName = sb.toString();
		mapperName = beanName;
	}

	private Set<String> processImportType(List<String> columns, List<String> types) {
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < columns.size(); i++) {
			String type = types.get(i);
			if (type.indexOf(type_date) > -1) {
				set.add("import java.util.Date;");
			} else if (type.indexOf(type_timestamp) > -1) {
				set.add("import java.util.Date;");
			} else if (type.indexOf(type_decimal) > -1) {
				set.add("import java.math.BigDecimal;");
			}
		}
		return set;
	}

	private String processType(String type) {
		if (type.indexOf(type_char) > -1) {
			return "String";
		} else if (type.indexOf(type_bigint) > -1) {
			return "Long";
		} else if (type.indexOf(type_int) > -1) {
			return "Integer";
		} else if (type.indexOf(type_date) > -1) {
			return "Date";
		} else if (type.indexOf(type_text) > -1) {
			return "String";
		} else if (type.indexOf(type_timestamp) > -1) {
			return "Date";
		} else if (type.indexOf(type_bit) > -1) {
			return "Boolean";
		} else if (type.indexOf(type_decimal) > -1) {
			return "BigDecimal";
		} else if (type.indexOf(type_blob) > -1) {
			return "byte[]";
		}
		return null;
	}

	private boolean processTypeIfCase(String type) {
		if (type.indexOf(type_char) > -1) {
			return true;
		} else if (type.indexOf(type_text) > -1) {
			return true;
		} else {
			return false;
		}
	}

	private String processType(String type, String enumType) {
		if (StringUtils.isNotBlank(enumType)) {
			return enumType;
		} else if (type.indexOf(type_char) > -1) {
			return "String";
		} else if (type.indexOf(type_bigint) > -1) {
			return "Long";
		} else if (type.indexOf(type_int) > -1) {
			return "Integer";
		} else if (type.indexOf(type_date) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_text) > -1) {
			return "String";
		} else if (type.indexOf(type_timestamp) > -1) {
			return "java.util.Date";
		} else if (type.indexOf(type_bit) > -1) {
			return "Boolean";
		} else if (type.indexOf(type_decimal) > -1) {
			return "java.math.BigDecimal";
		} else if (type.indexOf(type_blob) > -1) {
			return "byte[]";
		}
		return null;
	}

	private String processField(String field) {
		StringBuffer sb = new StringBuffer(field.length());
		// field = field.toLowerCase();
		if (field.contains("__")) {
			return field;
		}
		String[] fields = field.split("_");
		String temp = null;
		sb.append(fields[0]);
		for (int i = 1; i < fields.length; i++) {
			temp = fields[i].trim();
			sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
		}
		return sb.toString();
	}

	/**
	 * 灏嗗疄浣撶被鍚嶉瀛楁瘝鏀逛负灏忓啓
	 *
	 * @param beanName
	 * @return
	 */
	private String processResultMapId(String beanName) {
		return beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
	}

	/**
	 * 鏋勫缓绫讳笂闈㈢殑娉ㄩ噴
	 *
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter buildClassComment(BufferedWriter bw, String text) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("/**");
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" * " + text);
		bw.newLine();
		bw.write(" * ");
		bw.newLine();
		bw.write(" **/");
		return bw;
	}

	/**
	 * 鏋勫缓鏂规硶涓婇潰鐨勬敞閲?
	 *
	 * @param bw
	 * @param text
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter buildMethodComment(BufferedWriter bw, String text) throws IOException {
		bw.newLine();
		bw.write("\t/**");
		bw.newLine();
		bw.write("\t * ");
		bw.newLine();
		bw.write("\t * " + text);
		bw.newLine();
		bw.write("\t * ");
		bw.newLine();
		bw.write("\t **/");
		return bw;
	}

	/**
	 * 鐢熸垚瀹炰綋绫?
	 *
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildEntityBean(List<String> columns, List<String> types, List<String> comments, String tableComment) throws IOException {
		// 鍒涘缓鍖呯洰褰?
		String packagePath = bean_path + File.separator + createPackagePath(model_package);
		File folder = new File(packagePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File beanFile = new File(packagePath, beanName + ".java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(beanFile)));
		bw.write("package " + model_package + ";");
		bw.newLine();
		bw.newLine();
		Set<String> set = processImportType(columns, types);
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String str = it.next();
			bw.write(str);
			bw.newLine();
		}
		// bw.write("import lombok.Data;");
		// bw.write("import javax.persistence.Entity;");
		bw = buildClassComment(bw, tableComment);
		bw.newLine();
		// bw.write("@Entity");
		// bw.write("@Data");
		// bw.newLine();
		bw.write("public class " + beanName + "{");
		bw.newLine();
		bw.newLine();
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			bw.write("\n  /**" + comments.get(i) + "**/");
			bw.newLine();
			bw.write("\n  private " + processType(types.get(i)) + " " + processField(columns.get(i)) + ";");
			bw.newLine();
			bw.newLine();
		}
		bw.newLine();
		// 鐢熸垚get 鍜?set鏂规硶
		String tempField = null;
		String _tempField = null;
		String tempType = null;
		for (int i = 0; i < size; i++) {
			tempType = processType(types.get(i));
			_tempField = processField(columns.get(i));
			tempField = _tempField.substring(0, 1).toUpperCase() + _tempField.substring(1);
			bw.newLine();
			// bw.write("\tpublic void set" + tempField + "(" + tempType + " _"
			// + _tempField + "){");
			bw.write("\n  public void set" + tempField + "(" + tempType + " " + _tempField + ") { ");
			// bw.write("\t\tthis." + _tempField + "=_" + _tempField + ";");
			bw.write("\r    this." + _tempField + " = " + _tempField + ";");
			bw.write("\n  }");
			bw.newLine();
			bw.newLine();
			bw.write("\n  public " + tempType + " get" + tempField + "() { ");
			bw.write("\r    return this." + _tempField + ";");
			bw.write("\n  }");
			bw.newLine();
		}
		bw.newLine();
		bw.write("}");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	/**
	 * 鏋勫缓Mapper鏂囦欢
	 *
	 * @throws IOException
	 */
	private void buildMapper(List<String> columns, List<String> types) throws IOException {
		File folder = new File(mapper_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperFile = new File(mapper_path, mapperName + "Dao.java");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperFile), "utf-8"));
		bw.write("package " + dao_package + ";");
		bw.newLine();
		bw.newLine();
		bw.write("import java.util.List;");
		bw.newLine();
		bw.write("import " + model_package + "." + beanName + ";");
		bw.newLine();
		bw.write("import com.bmw.search.util.bean.CommonQueryBean;");
		bw.newLine();
		bw.newLine();
		bw.write("import org.apache.ibatis.annotations.Param;");
		bw.newLine();
		bw.write("import org.springframework.stereotype.Repository;");

		bw = buildClassComment(bw, mapperName + "鏁版嵁搴撴搷浣滄帴鍙ｇ被");
		bw.newLine();
		bw.newLine();
		// bw.write("public interface " + mapperName + " extends " +
		// mapper_extends + "<" + beanName + "> {");
		bw.write("@Repository");
		bw.newLine();
		bw.write("public interface " + mapperName + "Dao" + "{");
		bw.newLine();
		bw.newLine();
		// ----------瀹氫箟Mapper涓殑鏂规硶Begin----------
		bw = buildMethodComment(bw, "鏌ヨ锛堟牴鎹富閿甀D鏌ヨ锛?);
		bw.newLine();
		bw.write("\t" + beanName + "  selectByPrimaryKey ( @Param(\"" + processField(columns.get(0)) + "\") " + processType(types.get(0)) + " " + processField(columns.get(0)) + " );");
		bw.newLine();
		bw = buildMethodComment(bw, "鍒犻櫎锛堟牴鎹富閿甀D鍒犻櫎锛?);
		bw.newLine();
		bw.write("\t" + "int deleteByPrimaryKey ( @Param(\"" + processField(columns.get(0)) + "\") " + processType(types.get(0)) + " " + processField(columns.get(0)) + " );");
		bw.newLine();
		bw = buildMethodComment(bw, "娣诲姞");
		bw.newLine();
		bw.write("\t" + "int insert( " + beanName + " record );");
		bw.newLine();
		// bw = buildMethodComment(bw, "娣诲姞 锛堝尮閰嶆湁鍊肩殑瀛楁锛?);
		// bw.newLine();
		// bw.write("\t" + "int insertSelective( " + beanName + " record );");
		// bw.newLine();
		bw = buildMethodComment(bw, "淇敼 锛堝尮閰嶆湁鍊肩殑瀛楁锛?);
		bw.newLine();
		bw.write("\t" + "int updateByPrimaryKeySelective( " + beanName + " record );");
		bw.newLine();
		// bw = buildMethodComment(bw, "淇敼锛堟牴鎹富閿甀D淇敼锛?);
		// bw.newLine();
		// bw.write("\t" + "int updateByPrimaryKey ( " + beanName +
		// " record );");
		// bw.newLine();

		bw = buildMethodComment(bw, "list鍒嗛〉鏌ヨ");
		bw.newLine();
		bw.write("\t" + "List<" + beanName + "> list4Page (@Param(\"record\") " + beanName + " record, @Param(\"commonQueryParam\") CommonQueryBean query);");
		bw.newLine();

		bw = buildMethodComment(bw, "count鏌ヨ");
		bw.newLine();
		bw.write("\t" + "long count ( " + beanName + " record);");
		bw.newLine();

		bw = buildMethodComment(bw, "list鏌ヨ");
		bw.newLine();
		bw.write("\t" + "List<" + beanName + "> list ( " + beanName + " record);");
		bw.newLine();

		// ----------瀹氫箟Mapper涓殑鏂规硶End----------
		bw.newLine();
		bw.write("}");
		bw.flush();
		bw.close();
	}

	/**
	 * 鏋勫缓瀹炰綋绫绘槧灏刋ML鏂囦欢
	 *
	 * @param columns
	 * @param types
	 * @param comments
	 * @throws IOException
	 */
	private void buildMapperXml(List<String> columns, List<String> types, List<String> comments) throws IOException {
		File folder = new File(xml_path);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		File mapperXmlFile = new File(xml_path, mapperName + ".xml");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
		bw.newLine();
		bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		bw.newLine();
		bw.write("<mapper namespace=\"" + dao_package + "." + mapperName + "Dao\">");
		bw.newLine();
		bw.newLine();

		/*
		 * bw.write("\t<!--瀹炰綋鏄犲皠-->"); bw.newLine();
		 * bw.write("\t<resultMap id=\"" + this.processResultMapId(beanName) +
		 * "ResultMap\" type=\"" + beanName + "\">"); bw.newLine();
		 * bw.write("\t\t<!--" + comments.get(0) + "-->"); bw.newLine();
		 * bw.write("\t\t<id property=\"" + this.processField(columns.get(0)) +
		 * "\" column=\"" + columns.get(0) + "\" />"); bw.newLine(); int size =
		 * columns.size(); for ( int i = 1 ; i < size ; i++ ) {
		 * bw.write("\t\t<!--" + comments.get(i) + "-->"); bw.newLine();
		 * bw.write("\t\t<result property=\"" +
		 * this.processField(columns.get(i)) + "\" column=\"" + columns.get(i) +
		 * "\" />"); bw.newLine(); } bw.write("\t</resultMap>"); bw.newLine();
		 * bw.newLine(); bw.newLine();
		 */

		// 涓嬮潰寮€濮嬪啓SqlMapper涓殑鏂规硶
		// this.outputSqlMapperMethod(bw, columns, types);
		buildSQL(bw, columns, types);

		bw.write("</mapper>");
		bw.flush();
		bw.close();
	}

	private void buildSQL(BufferedWriter bw, List<String> columns, List<String> types) throws IOException {

		bw.write("\t<resultMap id=\"" + beanName + "\" type=\"" + model_package + "." + beanName + "\" >");
		bw.newLine();
		// <result column="$!item.columnName"
		// property="$!item.formatColumnName"/>
		for (int i = 0; i < columns.size(); i++) {
			bw.write("\t\t" + "<result column=\"" + columns.get(i) + "\" property=\"" + processField(columns.get(i)) + "\"/>");
			bw.newLine();
		}
		bw.write("\t</resultMap>");
		bw.newLine();
		bw.newLine();

		int size = columns.size();
		// 閫氱敤缁撴灉鍒?
		bw.write("\t<!-- 閫氱敤鏌ヨ缁撴灉鍒?->");
		bw.newLine();
		bw.write("\t<sql id=\"Base_Column_List\">");
		bw.newLine();

		for (int i = 0; i < size; i++) {
			bw.write("\t\t" + columns.get(i));
			if (i != size - 1) {
				bw.write(",");
				bw.newLine();
			}
		}

		bw.newLine();
		bw.write("\t</sql>");
		bw.newLine();
		bw.newLine();

		// 鏌ヨ锛堟牴鎹富閿甀D鏌ヨ锛?
		bw.write("\t<!-- 鏌ヨ锛堟牴鎹富閿甀D鏌ヨ锛?-->");
		bw.newLine();
		bw.write("\t<select id=\"selectByPrimaryKey\" resultMap=\"" + beanName + "\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
		bw.newLine();
		bw.write("\t\t SELECT");
		bw.newLine();
		bw.write("\t\t <include refid=\"Base_Column_List\" />");
		bw.newLine();
		bw.write("\t\t FROM " + tableName);
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();
		bw.newLine();
		// 鏌ヨ瀹?

		// 鍒犻櫎锛堟牴鎹富閿甀D鍒犻櫎锛?
		bw.write("\t<!--鍒犻櫎锛氭牴鎹富閿甀D鍒犻櫎-->");
		bw.newLine();
		bw.write("\t<delete id=\"deleteByPrimaryKey\" parameterType=\"java.lang." + processType(types.get(0)) + "\">");
		bw.newLine();
		bw.write("\t\t DELETE FROM " + tableName);
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</delete>");
		bw.newLine();
		bw.newLine();
		// 鍒犻櫎瀹?

		// 娣诲姞insert鏂规硶
		bw.write("\t<!-- 娣诲姞 -->");
		bw.newLine();
		bw.write("\t<insert id=\"insert\" parameterType=\"" + model_package + "." + beanName + "\" >");
		bw.newLine();
		bw.write("\t\t INSERT INTO " + tableName);
		bw.newLine();
		bw.write(" \t\t(");
		bw.newLine();
		for (int i = 0; i < size; i++) {
			bw.write("\t\t\t " + columns.get(i));
			if (i != size - 1) {
				bw.write(",");
			}
			bw.newLine();
		}
		bw.write("\t\t) ");
		bw.newLine();
		bw.write("\t\t VALUES ");
		bw.newLine();
		bw.write(" \t\t(");
		bw.newLine();
		for (int i = 0; i < size; i++) {
			bw.write("\t\t\t " + "#{" + processField(columns.get(i)) + "}");
			if (i != size - 1) {
				bw.write(",");
			}
			bw.newLine();
		}
		bw.write(" \t\t) ");
		bw.newLine();
		bw.write("\t\t <selectKey keyProperty=\"" + processField(columns.get(0)) + "\" resultType=\"" + processType(types.get(0)) + "\" order=\"AFTER\">");
		bw.newLine();
		bw.write("\t\t\t select LAST_INSERT_ID()");
		bw.newLine();
		bw.write("\t\t </selectKey>");
		bw.newLine();
		bw.write("\t</insert>");
		bw.newLine();
		bw.newLine();

		// 娣诲姞insert瀹?

		// --------------- insert鏂规硶锛堝尮閰嶆湁鍊肩殑瀛楁锛?
		// bw.write("\t<!-- 娣诲姞 锛堝尮閰嶆湁鍊肩殑瀛楁锛?->");
		// bw.newLine();
		// bw.write("\t<insert id=\"insertSelective\" parameterType=\"" +
		// processResultMapId(beanName) + "\">");
		// bw.newLine();
		// bw.write("\t\t INSERT INTO " + tableName);
		// bw.newLine();
		// bw.write("\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
		// bw.newLine();
		//
		// String tempField = null;
		// for (int i = 0; i < size; i++) {
		// tempField = processField(columns.get(i));
		// bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
		// bw.newLine();
		// bw.write("\t\t\t\t " + columns.get(i) + ",");
		// bw.newLine();
		// bw.write("\t\t\t</if>");
		// bw.newLine();
		// }
		//
		// bw.newLine();
		// bw.write("\t\t </trim>");
		// bw.newLine();
		//
		// bw.write("\t\t <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >");
		// bw.newLine();
		//
		// tempField = null;
		// for (int i = 0; i < size; i++) {
		// tempField = processField(columns.get(i));
		// bw.write("\t\t\t<if test=\"" + tempField + "!=null\">");
		// bw.newLine();
		// bw.write("\t\t\t\t #{" + tempField + "},");
		// bw.newLine();
		// bw.write("\t\t\t</if>");
		// bw.newLine();
		// }
		//
		// bw.write("\t\t </trim>");
		// bw.newLine();
		// bw.write("\t</insert>");
		// bw.newLine();
		// bw.newLine();
		// // --------------- 瀹屾瘯

		// 淇敼update鏂规硶
		String tempField = null;
		bw.write("\t<!-- 淇?鏀?->");
		bw.newLine();
		bw.write("\t<update id=\"updateByPrimaryKeySelective\" parameterType=\"" + model_package + "." + beanName + "\" >");
		bw.newLine();
		bw.write("\t\t UPDATE " + tableName);
		bw.newLine();
		bw.write(" \t\t <set> ");
		bw.newLine();

		tempField = null;
		for (int i = 1; i < size; i++) {
			tempField = processField(columns.get(i));
			if (processTypeIfCase(types.get(i))) {
				bw.write("\t\t\t<if test=\"" + tempField + " != null and " + tempField + " != ''\">");
			} else {
				bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
			}
			bw.newLine();
			bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + "},");
			bw.newLine();
			bw.write("\t\t\t</if>");
			bw.newLine();
		}

		bw.newLine();
		bw.write(" \t\t </set>");
		bw.newLine();
		bw.write("\t\t WHERE " + columns.get(0) + " = #{" + processField(columns.get(0)) + "}");
		bw.newLine();
		bw.write("\t</update>");
		bw.newLine();
		bw.newLine();
		// update鏂规硶瀹屾瘯

		// ----- 淇敼锛堝尮閰嶆湁鍊肩殑瀛楁锛?
		// bw.write("\t<!-- 淇?鏀?->");
		// bw.newLine();
		// bw.write("\t<update id=\"updateByPrimaryKey\" parameterType=\"" +
		// processResultMapId(beanName) + "\">");
		// bw.newLine();
		// bw.write("\t\t UPDATE " + tableName);
		// bw.newLine();
		// bw.write("\t\t SET ");
		//
		// bw.newLine();
		// tempField = null;
		// for (int i = 1; i < size; i++) {
		// tempField = processField(columns.get(i));
		// bw.write("\t\t\t " + columns.get(i) + " = #{" + tempField + "}");
		// if (i != size - 1) {
		// bw.write(",");
		// }
		// bw.newLine();
		// }
		//
		// bw.write("\t\t WHERE " + columns.get(0) + " = #{" +
		// processField(columns.get(0)) + "}");
		// bw.newLine();
		// bw.write("\t</update>");
		// bw.newLine();
		// bw.newLine();

		// list4Page鏂规硶
		tempField = null;
		bw.write("\t<!-- list4Page 鍒嗛〉鏌ヨ-->");
		bw.newLine();
		bw.write("\t<select id=\"list4Page\" resultMap=\"" + beanName + "\">");
		bw.newLine();
		bw.write("\t\t SELECT ");
		bw.newLine();
		bw.write("\t\t <include refid=\"Base_Column_List\" />");
		bw.newLine();
		bw.write("\t\t from " + tableName);
		bw.newLine();
		bw.write(" \t\t where 1=1  ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			if (processTypeIfCase(types.get(i))) {
				bw.write("\t\t<if test=\"record." + tempField + " != null and record." + tempField + " != ''\">");
			} else {
				bw.write("\t\t<if test=\"record." + tempField + " != null\">");
			}
			bw.newLine();
			bw.write("\t\t\t and " + columns.get(i) + " = #{record." + tempField + "} ");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t\t<if test=\"" + "commonQueryParam" + " != null\">");
		bw.newLine();
		bw.write("\t\t\t<if test=\"commonQueryParam.order != null \">");
		bw.newLine();
		bw.write("\t\t\t\t order by #{commonQueryParam.order}");
		bw.write("\t\t\t<if test=\"commonQueryParam.sort != null \">");
		bw.write("\t\t\t\t #{commonQueryParam.sort}");
		bw.write("\t\t\t</if>");
		bw.newLine();
		bw.write("\t\t\t</if>");
		bw.newLine();
		bw.write("\t\t\t<if test=\"commonQueryParam.start != null  and commonQueryParam.pageSize != null\">");
		bw.newLine();
		bw.write("\t\t\t\t limit #{commonQueryParam.start}, #{commonQueryParam.pageSize}");
		bw.newLine();
		bw.write("\t\t\t</if>");
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t</select>");
		bw.newLine();

		// count鏂规硶
		tempField = null;
		bw.write("\t<!-- count 鎬绘暟-->");
		bw.newLine();
		bw.write("\t<select id=\"count\" resultType=\"long\">");
		bw.newLine();
		bw.write("\t\t SELECT ");
		bw.newLine();
		bw.write("\t\t count(1) ");
		bw.newLine();
		bw.write("\t\t from " + tableName);
		bw.newLine();
		bw.write(" \t\t where 1=1  ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			if (processTypeIfCase(types.get(i))) {
				bw.write("\t\t<if test=\"" + tempField + " != null and " + tempField + " != ''\">");
			} else {
				bw.write("\t\t<if test=\"" + tempField + " != null\">");
			}
			bw.newLine();
			bw.write("\t\t\t and " + columns.get(i) + " = #{" + tempField + "} ");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t</select>");
		bw.newLine();

		// list鏂规硶
		tempField = null;
		bw.write("\t<!-- list 鏌ヨ-->");
		bw.newLine();
		bw.write("\t<select id=\"list\" resultMap=\"" + beanName + "\">");
		bw.newLine();
		bw.write("\t\t SELECT ");
		bw.newLine();
		bw.write("\t\t <include refid=\"Base_Column_List\" />");
		bw.newLine();
		bw.write("\t\t from " + tableName);
		bw.newLine();
		bw.write(" \t\t where 1=1  ");
		bw.newLine();
		tempField = null;
		for (int i = 0; i < size; i++) {
			tempField = processField(columns.get(i));
			if (processTypeIfCase(types.get(i))) {
				bw.write("\t\t<if test=\"" + tempField + " != null and " + tempField + " != ''\">");
			} else {
				bw.write("\t\t<if test=\"" + tempField + " != null\">");
			}
			bw.newLine();
			bw.write("\t\t\t and " + columns.get(i) + " = #{" + tempField + "} ");
			bw.newLine();
			bw.write("\t\t</if>");
			bw.newLine();
		}
		bw.write("\t</select>");
		bw.newLine();
	}

	/**
	 * 鑾峰彇鎵€鏈夌殑鏁版嵁搴撹〃娉ㄩ噴
	 *
	 * @return
	 * @throws SQLException
	 */
	private Map<String, String> getTableComment() throws SQLException {
		Map<String, String> maps = new HashMap<String, String>();
		PreparedStatement pstate = conn.prepareStatement("show table status");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString("NAME");
			String comment = results.getString("COMMENT");
			maps.put(tableName, comment);
		}
		return maps;
	}

	public void generate() throws ClassNotFoundException, SQLException, IOException {
		init();
		String prefix = "show full fields from ";
		List<String> columns = null;
		List<String> types = null;
		List<String> comments = null;
		PreparedStatement pstate = null;
		List<String> tables = getTables();
		Map<String, String> tableComments = getTableComment();
		for (String table : tables) {
			columns = new ArrayList<String>();
			types = new ArrayList<String>();
			comments = new ArrayList<String>();
			pstate = conn.prepareStatement(prefix + table);
			ResultSet results = pstate.executeQuery();
			while (results.next()) {
				columns.add(results.getString("FIELD").toLowerCase());
				types.add(results.getString("TYPE"));
				comments.add(results.getString("COMMENT"));
			}
			tableName = table;
			processTable(table);
			// this.outputBaseBean();
			String tableComment = tableComments.get(tableName);
			buildEntityBean(columns, types, comments, tableComment);
			buildMapper(columns, types);
			buildMapperXml(columns, types, comments);
		}
		conn.close();
	}

	public static String createPackagePath(String packageName) {
		StringBuffer sbBuffer = new StringBuffer();
		String[] arrs = packageName.split("\\.");
		for (String str : arrs) {
			sbBuffer.append(str).append(File.separator);
		}
		return sbBuffer.toString();
	}

	public static void main(String[] args) {
		try {
			new EntityUtil().generate();
			// 鑷姩鎵撳紑鐢熸垚鏂囦欢鐨勭洰褰?
			Runtime.getRuntime().exec("cmd /c start explorer d:\\DATA\\mybatistest\\");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

