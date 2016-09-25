package org.bbbs.component;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;

import org.bbbs.Application;
import org.bbbs.Session;

public class BaseComponent extends ERXComponent {
	public BaseComponent(WOContext context) {
		super(context);
	}
	
	@Override
	public Application application() {
		return (Application)super.application();
	}
	
	@Override
	public Session session() {
		return (Session)super.session();
	}
}
