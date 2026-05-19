package Required;
import org.testng.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
public class common {

static 	String headername1 ="Content-Type" ;
static 	String headervalue1 = "application/json";	
static String headername2 = "x-api-key";
static String headervalue2 = "reqres_2fd3dde8996c4ddf99507b3dbe3fcaa0";

public static Response Trigger_post_api(String endpoint,String requestbody) {
	RequestSpecification req_spe=RestAssured.given();
	req_spe.headers(headername1, headervalue1);
	req_spe.headers(headername2, headervalue2);
	req_spe.body(requestbody);
	Response response= req_spe.post(endpoint);
	return response;
}
public static Response Trigger_put_api(String endpoint,String requestbody) {
	RequestSpecification req_spe=RestAssured.given();
	req_spe.headers(headername1, headervalue1);
	req_spe.headers(headername2, headervalue2);
	req_spe.body(requestbody);
	Response response= req_spe.put(endpoint);
	return response;
}
public static Response Trigger_get_api(String endpoint) {
	RequestSpecification req_spe=RestAssured.given();
	req_spe.headers(headername2, headervalue2);
	Response response= req_spe.post(endpoint);
	return response;
}
}
