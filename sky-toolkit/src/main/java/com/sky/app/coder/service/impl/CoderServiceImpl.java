package com.sky.app.coder.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Column;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.sky.app.coder.helper.ConvertString;
import com.sky.app.coder.model.Element;
import com.sky.app.coder.model.Systems;
import com.sky.app.coder.service.ICoderService;

@Service("CoderService")
public class CoderServiceImpl implements ICoderService {
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlsession;
	@javax.annotation.Resource
	private SqlSessionFactory sqlSessionFactory;
	// 获取数据库连接对象
	Connection conn = null;
	PreparedStatement statement = null;
	// 数据库的列名称
	private String[] colnames; // 列名数组
	// 列名类型数组
	private String[] colTypes;
	// 存放加上表名的列名数组 ->对应实体类的属性名
	private String[] tablecolnames;
	// 用于放 加上别名的字段名称
	private String[] aliscolnames;

	@Override
	public List<Systems> getSystems(String sqlId, String sysKey) {
		return sqlsession.selectList(sqlId, sysKey);
	}

	@Override
	public Systems getSystemsOne(String sqlId, String upperSys) {
		return sqlsession.selectOne(sqlId, upperSys);
	}

	@Override
	public List<Element> getTagInfo(String sqlId, String moduCode) {
		return sqlsession.selectList(sqlId, moduCode);
	}

	@Override
	public Element getElement(String sqlId, String moduCode) {
		return sqlsession.selectOne(sqlId, moduCode);
	}

	@Override
	public Element getModuleOne(String sqlId, String moduCode) {
		return sqlsession.selectOne(sqlId, moduCode);
	}

	@Override
	public List<Element> getMultiTagInfo(String sqlId, String moduCode) {
		return sqlsession.selectList(sqlId, moduCode);
	}

	@Override
	public Element getMultiFieldOne(String sqlId, String tabCode) {
		return sqlsession.selectOne(sqlId, tabCode);
	}

	// 单表模型
	@Override
	public String getClassStr(String tablename, String tablepri) {
		// 输出的类字符串
		// 模块数据库表主键
		String convertTablePri = ConvertString.convertSomeCharUpper(tablepri.toLowerCase());
		StringBuffer str = new StringBuffer("");
		// 获取表类型和表名的字段名
		try {
			conn = sqlSessionFactory.openSession().getConnection();
			String sql = "select * from " + tablename;
			statement = conn.prepareStatement(sql);
			// 获取数据库的元数据
			ResultSetMetaData metadata = statement.getMetaData();
			// 数据库的字段个数
			int len = metadata.getColumnCount();
			// 字段名称
			colnames = new String[len + 1];
			// 字段类型 --->已经转化为java中的类名称了
			colTypes = new String[len + 1];
			for (int i = 1; i <= len; i++) {
				// System.out.println(metadata.getColumnName(i)+":"+metadata.getColumnTypeName(i)+":"+sqlType2JavaType(metadata.getColumnTypeName(i).toLowerCase())+":"+metadata.getColumnDisplaySize(i));
				// metadata.getColumnDisplaySize(i);
				colnames[i] = ConvertString.convertSomeCharUpper(metadata.getColumnName(i).toLowerCase()); // 获取字段名称
				System.out.println(colnames[i]);
				if (colnames[i].equals(convertTablePri)) {
					colTypes[i] = "String"; // 如果是主键,则类型全为字符串
				} else {
					colTypes[i] = sqlType2JavaType(metadata.getColumnTypeName(i)); // 获取字段类型
				}
				System.out.println(metadata.getColumnTypeName(i));
				System.out.println(colTypes[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// 校验
		if (null == colnames && null == colTypes)
			return null;
		// 拼接属性
		for (int index = 1; index < colnames.length; index++) {
			if (colnames[index].equals(convertTablePri)) {
				str.append("    @Id\r\n" + "    @GeneratedValue(generator=\"UUID\")\n"
						+ getAttrbuteString(colnames[index], colTypes[index]));
			} else {
				str.append(getAttrbuteString(colnames[index], colTypes[index]));
			}

		}
		// 拼接get，Set方法
		for (int index = 1; index < colnames.length; index++) {
			str.append(getGetMethodString(colnames[index], colTypes[index]));
			str.append(getSetMethodString(colnames[index], colTypes[index]));
		}
		return str.toString();
	}

	// 多表模型，根据表名和主键字段生成实体类内容
	public String getMultiClassStrBytable(String tablename, String tablepri) {
		// 输出的类字符串
		StringBuffer str = new StringBuffer("");
		// 获取表类型和表名的字段名
		try {
			conn = sqlSessionFactory.openSession().getConnection();
			String sql = "select * from " + tablename;
			PreparedStatement statement = conn.prepareStatement(sql);
			// 获取数据库的元数据
			ResultSetMetaData metadata = statement.getMetaData();
			// 数据库的字段个数
			int len = metadata.getColumnCount();
			// 字段名称
			colnames = new String[len];
			// 加上表名字段名称->对应实体类的属性名
			tablecolnames = new String[len];
			// 字段类型 --->已经转化为java中的类名称了
			colTypes = new String[len];
			for (int i = 1; i <= len; i++) {
				// System.out.println(metadata.getColumnName(i)+":"+metadata.getColumnTypeName(i)+":"+sqlType2JavaType(metadata.getColumnTypeName(i).toLowerCase())+":"+metadata.getColumnDisplaySize(i));
				// metadata.getColumnDisplaySize(i);
				colnames[i - 1] = metadata.getColumnName(i);
				tablecolnames[i - 1] = ConvertString
						.convertSomeCharUpperReplace(tablename + "." + metadata.getColumnName(i));
				if (colnames[i-1].equals(tablepri)) {
					colTypes[i-1] = "String"; // 如果是主键,则类型全为字符串
				} else {
					colTypes[i - 1] = sqlType2JavaType(metadata.getColumnTypeName(i)); // 获取字段类型
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// 校验
		if (null == colnames && null == colTypes)
			return null;
		// 拼接属性
		for (int index = 0; index < colnames.length; index++) {
			if (colnames[index].equals(tablepri)) {
				str.append("    @Id\r\n" + "    @GeneratedValue(generator=\"UUID\")\n" + "    @Column(name=\""
						+ colnames[index] + "\")\r\n" + getAttrbuteString(tablecolnames[index], colTypes[index]));
			} else {
				str.append("    @Column(name=\"" + colnames[index] + "\")\r\n"
						+ getAttrbuteString(tablecolnames[index], colTypes[index]));
			}

		}
		// 拼接get，Set方法
		for (int index = 0; index < colnames.length; index++) {
			str.append(getGetMethodString(tablecolnames[index], colTypes[index]));
			str.append(getSetMethodString(tablecolnames[index], colTypes[index]));
		}
		return str.toString();
	}

	// 多表模型,通过表名数据生成实体类内容
	@Override
	public String getMultiClassStr(String[] tablenames,List<String> listss) {
		// 用于存放多个表列名数组
		List<String[]> listnamearr = new ArrayList<String[]>();
		// 用于存放多个表列名(加上表名组合的)数组
		List<String[]> listtablenamearr = new ArrayList<String[]>();
		// 用于存放多个表类型数组
		List<String[]> listtypearr = new ArrayList<String[]>();
		// 输出的类字符串
		StringBuffer str = new StringBuffer("");
		for (int i=0;i<tablenames.length;i++) {
			try {
				conn = sqlSessionFactory.openSession().getConnection();
				String sql = "select * from " + tablenames[i];
				PreparedStatement statement = conn.prepareStatement(sql);
				// 获取数据库的元数据
				ResultSetMetaData metadata = statement.getMetaData();
				// 数据库的字段个数
				int len = metadata.getColumnCount();
				// 加上表名的字段名称->对应实体类的属性名
				tablecolnames = new String[len];
				// 字段类型 --->已经转化为java中的类名称了
				colTypes = new String[len];
				for (int j = 1; j <= len; j++) {
					tablecolnames[j - 1] = ConvertString
							.convertSomeCharUpperReplace(tablenames[i] + "." + metadata.getColumnName(j));// 获取加上表名字段名称
					if (metadata.getColumnName(j).equals(listss.get(i))) {
						colTypes[j-1] = "String"; // 如果是主键,则类型全为字符串
					} else {
						colTypes[j - 1] = sqlType2JavaType(metadata.getColumnTypeName(j)); // 获取字段类型
					}
				}
				listnamearr.add(colnames);
				listtablenamearr.add(tablecolnames);
				listtypearr.add(colTypes);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// 合并
		if (listtablenamearr.size() > 0) {
			tablecolnames = concatAll(listtablenamearr);
		}
		// 合并多个表列类型数组
		if (listtypearr.size() > 0) {
			colTypes = concatAll(listtypearr);
		}
		// 校验
		if (null == tablecolnames && null == colTypes)
			return null;
		// 拼接属性
		for (int index = 0; index < tablecolnames.length; index++) {
			str.append(getAttrbuteString(tablecolnames[index], colTypes[index]));
		}
		// 拼接get，Set方法
		for (int index = 0; index < tablecolnames.length; index++) {
			str.append(getGetMethodString(tablecolnames[index], colTypes[index]));
			str.append(getSetMethodString(tablecolnames[index], colTypes[index]));
		}
		return str.toString();
	}

	// 多表模型，获得映射文件mapper中select后面的每个表的字段
	public String getMultiMapperSelectField(String[] tablenames) {
		// 用于存放多个表列名数组
		List<String[]> listnamearr = new ArrayList<String[]>();
		// 用于存放多个表加上别名的列名数组
		List<String[]> listalisnamearr = new ArrayList<String[]>();
		StringBuffer str = new StringBuffer("");
		for (int j = 0; j < tablenames.length; j++) {
			try {
				Connection conn = sqlSessionFactory.openSession().getConnection();
				String sql = "select * from " + tablenames[j];
				PreparedStatement statement = conn.prepareStatement(sql);
				// 获取数据库的元数据
				ResultSetMetaData metadata = statement.getMetaData();
				// 数据库的字段个数
				int len = metadata.getColumnCount();
				// 加上表名的字段名称
				tablecolnames = new String[len];
				// 加上表名的字段且有别名->对应实体类
				aliscolnames = new String[len];
				for (int i = 1; i <= len; i++) {
					tablecolnames[i - 1] = tablenames[j] + "." + metadata.getColumnName(i); // 获取表名加上字段组合的字段
					aliscolnames[i - 1] = ConvertString
							.convertSomeCharUpperReplace(tablenames[j] + "." + metadata.getColumnName(i));
				}
				listnamearr.add(tablecolnames);
				listalisnamearr.add(aliscolnames);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 合并多个表列名数组,并获得重复元素的下标
		if (listnamearr.size() > 0) {
			tablecolnames = concatAll(listnamearr);
		}
		if (listalisnamearr.size() > 0) {
			aliscolnames = concatAll(listalisnamearr);
		}
		// 校验
		if (null == tablecolnames)
			return null;
		// 拼接带有别名的查询字段
		for (int index = 0; index < tablecolnames.length; index++) {
			if (index < tablecolnames.length - 1) {
				str.append("\r" + (tablecolnames[index]) + "  as  " + aliscolnames[index] + ",");
			} else {
				str.append("\r" + (tablecolnames[index]) + "  as " + aliscolnames[index]);
			}
		}
		return str.toString();

	}

	/*
	 * mysql的字段类型转化为java的类型
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit") || sqlType.equalsIgnoreCase("boolean")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")) {
			return "BigDecimal";
		} else if (sqlType.equalsIgnoreCase("float") || sqlType.equalsIgnoreCase("double")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("blob")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Timestamp";
		}

		return null;
	}

	/*
	 * 获取字段字符串
	 */
	public StringBuffer getAttrbuteString(String name, String type) {
		if (!check(name, type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		}
		;
		String format = String.format("    private %s %s;\n\r", new String[] { type, name });
		return new StringBuffer(format);
	}

	/*
	 * 校验name和type是否合法
	 */
	public boolean check(String name, String type) {
		if ("".equals(name) || name == null || name.trim().length() == 0) {
			return false;
		}
		if ("".equals(type) || type == null || type.trim().length() == 0) {
			return false;
		}
		return true;

	}

	/*
	 * 获取get方法字符串
	 */
	private StringBuffer getGetMethodString(String name, String type) {
		if (!check(name, type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		}
		;
		String Methodname = "get" + GetTuoFeng(name);
		String format = String.format("    public %s %s(){\n\r", new Object[] { type, Methodname });
		format += String.format("        return this.%s;\r\n", new Object[] { name });
		format += "    }\r\n";
		return new StringBuffer(format);
	}

	// 将名称首字符大写
	private String GetTuoFeng(String name) {
		name = name.trim();
		if (name.length() > 1) {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
		} else {
			name = name.toUpperCase();
		}
		return name;
	}

	/*
	 * 获取字段的get方法字符串
	 */
	private Object getSetMethodString(String name, String type) {
		if (!check(name, type)) {
			System.out.println("类中有属性或者类型为空");
			return null;
		}
		;
		String Methodname = "set" + GetTuoFeng(name);
		String format = String.format("    public void %s(%s %s){\n\r", new Object[] { Methodname, type, name });
		format += String.format("        this.%s = %s;\r\n", new Object[] { name, name });
		format += "    }\r\n";
		return new StringBuffer(format);
	}

	/*
	 * 将多个数组合并，按传入参数的顺序合并
	 */
	public static String[] concatAll(List<String[]> listarr) {
		// 存放最终合并后的数组
		String[] result = null;
		if (listarr.size() > 0) {
			int totalLength = 0;
			for (String[] array : listarr) {
				totalLength += array.length;
			}
			result = new String[totalLength];
			int offset = 0;
			for (String[] array : listarr) {
				System.arraycopy(array, 0, result, offset, array.length);
				offset += array.length;
			}
			return result;
		} else {
			return result;
		}

	}

}
