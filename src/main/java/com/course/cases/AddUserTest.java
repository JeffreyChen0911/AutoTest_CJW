package com.course.cases;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DBUtil;

public class AddUserTest {

	@Test(dependsOnGroups = "loginTrue", description = "����û��ӿ�")
	public void addUser() throws IOException{
		SqlSession session = DBUtil.getSqlSession();
		AddUserCase addUserCase = session.selectOne("addUserCase");
		System.out.println(addUserCase.toString());
		System.out.println(TestConfig.addUserUrl);
		
		//�����󣬻�ȡ���
		String result = getResult(addUserCase);
		//��֤���ؽ��
		
		User user = session.selectOne("addUser", addUserCase);
		
		System.out.println(user.toString());
		Assert.assertEquals(addUserCase.getExpected(), result);
	}

	private String getResult(AddUserCase addUserCase) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(TestConfig.addUserUrl);
		JSONObject param = new JSONObject();
		param.put("userName", addUserCase.getUserName());
		param.put("password", addUserCase.getPassword());
		param.put("sex", addUserCase.getSex());
		param.put("age", addUserCase.getAge());
		param.put("permission", addUserCase.getPermission());
		param.put("isDelete", addUserCase.getIsDelete());
		
		// ����ͷ��Ϣ
		post.setHeader("content-type", "application/json");
		StringEntity entity = new StringEntity(param.toString(), "UTF-8");
		post.setEntity(entity);
		
		// ����cookies
		TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
		String result;//��ŷ��ؽ��
		HttpResponse response = TestConfig.defaultHttpClient.execute(post);
		
		result = EntityUtils.toString(response.getEntity(), "UTF-8");
		
		System.out.println(result);
		
		return null;
	}
}
