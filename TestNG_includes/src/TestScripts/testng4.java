package TestScripts;

import java.io.File;
import java.time.LocalDateTime;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Required.RetryAnalyzer;
import Required.common;
import Required.utility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class testng4 {
	File log_dir;
	String requestbody;
	String hostname;
	String resource;
	String endpoint;
	int statusCode;
	Response response;
	ResponseBody responsebody;

	@BeforeClass
	public void testSetUp() {
		log_dir = utility.createFolder("Post_Api");
		hostname = "https://reqres.in/";
		resource = "api/users";
		endpoint = hostname + resource;
	}

	@Test(description = "validate the statuscode is 201", retryAnalyzer = RetryAnalyzer.class)
	public void StatuscodeValidateNexecute() {
		requestbody = "{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}";
		response = common.Trigger_post_api(endpoint, requestbody);
		statusCode = response.getStatusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, 201, "statusCode validation failed");
	}

	@Test(dependsOnMethods = { "StatuscodeValidateNexecute" }, description = "validate the response. ")
	public void validateResponseBody() {
		responsebody = response.getBody();
		System.out.println(responsebody.asString());

		JsonPath jsnph = new JsonPath(requestbody);
		String req_name = jsnph.getString("name");
		String req_job = jsnph.getString("job");
		LocalDateTime curTime = LocalDateTime.now();
		String expDate = curTime.toString().substring(0, 11);

		String res_name = responsebody.jsonPath().getString("name");
		String res_job = responsebody.jsonPath().getString("job");
		String res_id = responsebody.jsonPath().getString("id");
		String res_createdAt = responsebody.jsonPath().getString("createdAt").substring(0, 11);
		String res_meta_powered_by = responsebody.jsonPath().getString("_meta.powered_by");
		String res_meta_docs_url = responsebody.jsonPath().getString("_meta.docs_url");
		String res_meta_upgrade_url = responsebody.jsonPath().getString("_meta.upgrade_url");
		String res_meta_example_url = responsebody.jsonPath().getString("_meta.example_url");
		String res_meta_variant = responsebody.jsonPath().getString("_meta.variant");

		Assert.assertEquals(req_name, res_name, "name key is not expected");
		Assert.assertEquals(req_job, res_job, "job key is not expected");
		Assert.assertEquals(expDate, res_createdAt, "time/date key is not expected");
		Assert.assertEquals(res_meta_powered_by, "ReqRes", "Powered_by key is not valid");
		Assert.assertNotNull(res_id, "id is less than 1");
	}

	@AfterClass
	public void creatLogFile() throws Exception {
		utility.creatLogFile(log_dir, "testng4", statusCode, endpoint, requestbody, response.getBody().asString());
	}

}
