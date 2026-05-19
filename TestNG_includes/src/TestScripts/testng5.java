package TestScripts;

import java.io.File;
import java.time.LocalDateTime;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo_class.res_pojo;
import Required.Req_pojo;
import Required.common;
import Required.utility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class testng5 {
	File log_dir;
	String requestbody;
	String hostname;
	String resource;
	String endpoint;
	int statusCode;
	Response response;

	@BeforeClass
	public void testSetUp() throws JsonProcessingException {
		log_dir = utility.createFolder("Post_Api");
		Req_pojo objpojo = new Req_pojo();
		ObjectMapper objMap = new ObjectMapper();
		objpojo.setName("morpheus");
		objpojo.setJob("leader");
		requestbody = objMap.writeValueAsString(objpojo);

		hostname = "https://reqres.in/";
		resource = "api/users";
		endpoint = hostname + resource;
	}

	@Test(description = "validate the statuscode is 201")
	public void StatuscodeValidateNexecute() {
		response = common.Trigger_post_api(endpoint, requestbody);
		statusCode = response.getStatusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 201, "statusCode validation failed");
	}

	@Test(dependsOnMethods = { "StatuscodeValidateNexecute" }, description = "validate the response. ")
	public void validateResponseBody() {
		res_pojo responsebody = response.as(res_pojo.class);
		System.out.println(response.getBody().asString());

		JsonPath jsnph = new JsonPath(requestbody);
		String req_name = jsnph.getString("name");
		String req_job = jsnph.getString("job");
		LocalDateTime curTime = LocalDateTime.now();
		String expDate = curTime.toString().substring(0, 11);

		String res_name = responsebody.getName();
		String res_job = responsebody.getJob();
		String res_id = responsebody.getId();
		String res_createdAt = responsebody.getCreatedAt().substring(0, 11);
		String _meta_powered_by = responsebody.get_meta().getPowered_by();
		String _meta_docs_url = responsebody.get_meta().getDocs_url();
		String _meta_upgrade_url = responsebody.get_meta().getUpgrade_url();
		String _meta_example_url = responsebody.get_meta().getExample_url();
		String _meta_variant = responsebody.get_meta().getVariant();
		String res_meta_message = responsebody.get_meta().getMessage();
		String res_meta_cta_label = responsebody.get_meta().getCta().getLabel();
		String res_meta_cta_url = responsebody.get_meta().getCta().getUrl();
		String res_meta_context = responsebody.get_meta().getContext();
		
		Assert.assertEquals(req_name, res_name, "name key is not expected");
		Assert.assertEquals(req_job, res_job, "job key is not expected");
		Assert.assertEquals(expDate, res_createdAt, "time/date key is not expected");
		Assert.assertNotNull(res_id, "id is less than 1");
		//Assert.assertEquals(_meta_powered_by, "ReqRes", "Powered_by key is not valid");
		
	}

	@AfterClass
	public void creatLogFile() throws Exception {
		utility.creatLogFile(log_dir, "testng5", statusCode, endpoint, requestbody, response.getBody().asString());
	}
}
