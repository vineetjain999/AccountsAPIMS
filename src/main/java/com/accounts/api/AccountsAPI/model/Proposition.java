package com.accounts.api.AccountsAPI.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Proposition {

    @JsonProperty("name")
    private String name;

    @JsonProperty("version")
    private String version;

  

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

	@Override
	public String toString() {
		return "Proposition [name=" + name + ", version=" + version + "]";
	}

    
}
