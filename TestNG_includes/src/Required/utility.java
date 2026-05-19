package Required;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.restassured.http.Headers;

public class utility {
	public static File createFolder(String foldername) {
		String project_dir = System.getProperty("user_dir");
		System.out.println(project_dir);
		File folder = new File(project_dir + "//Api_logs//" + foldername);
		if (folder.exists()) {
			System.out.println(foldername + "exist in current folder" + project_dir);
		} else {
			System.out.println(foldername + "does not exist in current folder" + project_dir);
			folder.mkdirs();
			System.out.println(foldername + "exist in current folder" + project_dir);
		}
		return folder;
	}

	public static void creatLogFile(File filelocation, String foldername, int statuscode, String endpoint,
			String requestbody, String responsebody) throws IOException {
		File nextfile = new File(filelocation + "//" + foldername + ".txt");
		System.out.println(nextfile);
		FileWriter datawriter = new FileWriter(nextfile);
		datawriter.write(endpoint);
		datawriter.write(statuscode);
		datawriter.write(responsebody);
		datawriter.write(requestbody);
		datawriter.close();
	}

}
