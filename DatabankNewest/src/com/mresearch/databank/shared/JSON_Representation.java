package com.mresearch.databank.shared;

import java.io.Serializable;

import com.google.gwt.json.client.JSONObject;

public class JSON_Representation implements Serializable {
	public String getRepresent() {
		return represent;
	}
	public void setRepresent(String represent) {
		this.represent = represent;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6151538640341868038L;
	private String represent;
	private JSONObject obj;
	public JSON_Representation() {
	}
	public JSON_Representation(String repr) {
		this.represent = repr;
	}
	public JSON_Representation(JSONObject repr) {
		this.obj = repr;
	}
	public JSONObject getObj() {
		return obj;
	}
	public void setObj(JSONObject obj) {
		this.obj = obj;
	}
}
