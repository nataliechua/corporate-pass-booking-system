package project.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	private String location = "/wamp64\\www\\group-project-g1t5\\demo\\src\\main\\resources\\templates\\emailTemplates";

	
	/** 
	 * @return String
	 */
	public String getLocation() {
		return location;
	}

	
	/** 
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

}