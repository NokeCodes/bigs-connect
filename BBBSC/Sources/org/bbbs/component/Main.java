package org.bbbs.component;

import org.bbbs.Session;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;

public class Main extends BaseComponent {
	private String	email	= "Enter your email address";
	private String	pass	= "Enter your password";

	private String	errorMessage;

	public Main(WOContext context) {
		super(context);
	}

	public WOActionResults loginUser() {
		Session session = session();

		EOEditingContext ctx = session.defaultEditingContext();

		Big user = Big.clazz.bigWithCredentials(ctx, email, pass);

		if (user == null) {
			errorMessage = "Incorrect username or password";
			return null;
		}

		session.setUser(user);

		CheckInOutPage outPage = pageWithName(CheckInOutPage.class);

		return outPage;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
