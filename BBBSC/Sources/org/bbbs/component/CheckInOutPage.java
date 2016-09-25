package org.bbbs.component;

import org.bbbs.Session;
import org.joda.time.DateTime;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOSession;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.components.ERXComponent;

public class CheckInOutPage extends ERXComponent {
	private boolean	checkingIn;
	private boolean	checkingOut;

	private String	location	= "Enter your current location";

	private String	status;

	public CheckInOutPage(WOContext context) {
		super(context);
	}

	public WOActionResults doCheckIn() {
		return doCheckInOut(true);
	}

	public WOActionResults doCheckOut() {
		return doCheckInOut(false);
	}

	public WOActionResults doCheckInOut(boolean checkIn) {
		Session session = (Session) session();

		EOEditingContext ctx = session.defaultEditingContext();

		Big user = session.getUser();

		if (user.status() == checkIn) {
			if (checkIn) {
				status = "You're already checked in. Check out first";
			} else {
				status = "You're not checked in. Do that before checking out";
			}
		} else {
			user.setStatus(checkIn);

			CheckInOut cio = CheckInOut.createCheckInOut(ctx, !checkIn,
					location, DateTime.now(), user);

			status = "You have succesfully checked "
					+ (checkIn ? "in" : "out");
		}

		ctx.saveChanges();

		return null;
	}

	public boolean isCheckingIn() {
		return checkingIn;
	}

	public void setCheckingIn(boolean checkingIn) {
		this.checkingIn = checkingIn;
	}

	public boolean isCheckingOut() {
		return checkingOut;
	}

	public void setCheckingOut(boolean checkingOut) {
		this.checkingOut = checkingOut;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}