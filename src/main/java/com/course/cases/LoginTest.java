package com.course.cases;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DBUtil;

public class LoginTest {
	
	@BeforeTest(groups = "loginTrue", description = "测试准备工作，获取HttpClient对象")
	public void beforeTest(){
		TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
		TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
		TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
		TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
		TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);	
		
		TestConfig.defaultHttpClient = new DefaultHttpClient();
	}
	
	@Test(groups = "loginTrue", description = "用户登录成功接口测试")
	public void loginTrue() throws IOException{
		SqlSession session = DBUtil.getSqlSession();
		LoginCase loginCase = session.selectOne("loginCase", "1");
		System.out.println(loginCase.toString());
		System.out.println(TestConfig.loginUrl);
		
		// 第一步就是发送请求
		String  result = getResult(loginCase);
		// 验证结果
		Assert.assertEquals(loginCase.getExpected(), result);
	}

	@Test(groups = "loginfalse", description = "用户登录失败接口测试")
	public void loginFalse() throws IOException{
		SqlSession session = DBUtil.getSqlSession();
		LoginCase loginCase = session.selectOne("loginCase", "2");
		System.out.println(loginCase.toString());
		System.out.println(TestConfig.loginUrl);
		
		// 第一步就是发送请求
		String  result = getResult(loginCase);
		// 验证结果
		Assert.assertEquals(loginCase.getExpected(), result);
	}
	
	private String getResult(LoginCase loginCase) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(TestConfig.loginUrl);
		JSONObject param = new JSONObject();
		param.put("userName", loginCase.getUserName());
		param.put("password", loginCase.getPassword());
		
		post.setHeader("content-type", "application/json");
		StringEntity entity = new StringEntity(param.toString(), "UTF-8");
		post.setEntity(entity);
		
		String result;//存放返回结果
		HttpResponse response = TestConfig.defaultHttpClient.execute(post);
		result = EntityUtils.toString(response.getEntity(), "UTF-8");
		
		TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
		
		return result;
	}
}
