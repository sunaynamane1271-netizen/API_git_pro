package TestScripts;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.path.json.JsonPath;
import java.time.LocalDateTime;
import java.io.File;
import java.io.IOException;

import Required.common;
import Required.utility;
import Required.dataDriven_dataProvdr;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class testng1 {
	File log_dir;
	String requestbody;
	String hostname;
	String resource;
	String endpoint;
	int statusCode;
	Response response;

	@DataProvider
	public Object[][] request_Data() {
		return new Object[][] { { "Nayna", "SrQA" }, { "Sunayna", "QASDET" }, { "Mane", "QALead" } };
	}

	@BeforeClass
	public void testSetUp() {
		log_dir = utility.createFolder("Post_Api");
		hostname = "https://reqres.in/";
		resource = "api/users";
		endpoint = hostname + resource;
	}

	@Test(dataProvider = "request_Data", dataProviderClass = dataDriven_dataProvdr.class)
	public void StatuscodeValidateNexecute(String Req_name, String Req_job) throws IOException {
		requestbody = "{\r\n" + "    \"name\": \"" + Req_name + "\",\r\n" + "    \"job\": \"" + Req_job + "\"\r\n"
				+ "}";
		response = common.Trigger_post_api(endpoint, requestbody);
		statusCode = response.getStatusCode();
		System.out.println(statusCode);

		ResponseBody responsebody = response.getBody();
		System.out.println(responsebody.asString());

		JsonPath jsnp = new JsonPath(requestbody);
		String req_name = jsnp.getString("name");
		String req_job = jsnp.getString("job");
		LocalDateTime exp_tm = LocalDateTime.now();
		String currentT = exp_tm.toString().substring(0, 11);

		String res_name = responsebody.jsonPath().getString("name");
		String res_job = responsebody.jsonPath().getString("job");
		String res_id = responsebody.jsonPath().getString("id");
		String res_createdAt = responsebody.jsonPath().getString("createdAt").substring(0, 11);
		String res_meta_powered_by = responsebody.jsonPath().getString("_meta.powered_by");
		String res_meta_docs_url = responsebody.jsonPath().getString("_meta.docs_url");
		String res_meta_upgrade_url = responsebody.jsonPath().getString("_meta.upgrade_url");
		String res_meta_example_url = responsebody.jsonPath().getString("_meta.example_url");
		String res_meta_variant = responsebody.jsonPath().getString("_meta.variant");

		Assert.assertEquals(res_name, req_name);
		Assert.assertEquals(res_job, req_job);
		Assert.assertEquals(currentT, res_createdAt, "time/date key is not expected");
		Assert.assertEquals(res_meta_powered_by, "ReqRes", "Powered_by key is not valid");
		Assert.assertNotNull(res_id, "id is less than 1");

		String data = jsnp.getString("name");
		utility.creatLogFile(log_dir, "testng1" + data, statusCode, endpoint, requestbody,
				response.getBody().asString());
	}
}
