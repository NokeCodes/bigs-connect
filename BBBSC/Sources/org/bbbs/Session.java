package org.bbbs;

import org.bbbs.component.Big;

import er.extensions.appserver.ERXSession;

public class Session extends ERXSession {
	private static final long	serialVersionUID	= 1L;
	private Big					user;

	public Session() {
	}

	@Override
	public Application application() {
		return (Application) super.application();
	}

	public void setUser(Big user) {
		this.user = user;
	}

	public Big getUser() {
		return user;
	}
}
