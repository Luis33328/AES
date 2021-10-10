
public class User {

	private String id;
	private String key;
	
	public User(String id, String key) {
		this.id = id;
		this.key = key;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
