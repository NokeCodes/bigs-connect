package org.bbbs;

import org.bbbs.component.Main;

import er.extensions.appserver.ERXApplication;
import er.extensions.foundation.ERXPatcher;

public class Application extends ERXApplication {
	public static void main(String[] argv) {
		// Make sure we use correct classes
		ERXPatcher.setClassForName(Session.class, "Session");
		ERXPatcher.setClassForName(Main.class, "Main");

		ERXApplication.main(argv, Application.class);
	}

	public Application() {
		ERXApplication.log.info("Welcome to " + name() + " !");
		/* ** put your initialization code in here ** */
		setAllowsConcurrentRequestHandling(true);
	}
}
