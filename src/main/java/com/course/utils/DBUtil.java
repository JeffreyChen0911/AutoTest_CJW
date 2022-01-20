package com.course.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBUtil {
	
	public static SqlSession getSqlSession() throws IOException{
		// ��ȡ���õ���Դ�ļ�
		Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
		// sqlSession�����ܹ�ִ�������ļ��е�sql���
		SqlSession sqlSession = factory.openSession();
		return sqlSession;
	}
}
