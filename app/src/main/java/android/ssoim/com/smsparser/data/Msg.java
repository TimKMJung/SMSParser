package android.ssoim.com.smsparser.data;

/**
 * Created by renewinspirit on 2017. 6. 19..
 */

public class Msg {

private String id;
private String address;
private String body;
private String t_id;

public Msg(String ID) {
	id = ID;
	body = "";
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getBody() {
	return body;
}

public void setBody(String body) {
	this.body = body;
}

public String getID() {
	return id;