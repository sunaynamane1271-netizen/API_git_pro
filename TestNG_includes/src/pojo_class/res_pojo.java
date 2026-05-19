package pojo_class;
import com.fasterxml.jackson.annotation.JsonProperty;
public class res_pojo {
	private String name;
	private String job;
	private String id;
	private String createdAt;
	@JsonProperty
	private Pojo_res_meta _meta;

	public String getName() {
		return name;
	}
	public String getJob() {
		return job;
	}
	public String getId() {
		return id;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public Pojo_res_meta get_meta() {
		return _meta;
	}
	
}
