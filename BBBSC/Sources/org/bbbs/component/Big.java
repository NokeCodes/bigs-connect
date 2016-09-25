package org.bbbs.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdaworks.crypto.SCryptUtil;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;

import er.extensions.components.javascript.ERXJSToManyRelationshipEditor.Keys;
import er.extensions.eof.ERXQ;

public class Big extends _Big {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Big.class);

	public static class BigClazz {

		public Big bigWithCredentials(EOEditingContext ctx, String email,
				String pass) {
			NSArray<Big> bigs = loginBig(ctx, email, pass);

			if (bigs.count() == 1) {
				Big big = bigs.lastObject();

				/*
				 * if (SCryptUtil.check(pass, big.passwd())) { return big;
				 * }
				 */
				// FIXME switch to the above
				if (pass.equals(big.passwd())) {
					return big;
				}
			}

			return null;
		}

		private NSArray<Big> loginBig(EOEditingContext ctx, String email,
				String pass) {
			EOQualifier qual = ERXQ.and(ERXQ.equals("email", email));

			return Big.fetchBigs(ctx, qual,
					new NSArray<EOSortOrdering>(Big.NAME.desc()));
		}
	}

	public static final BigClazz clazz = new BigClazz();
}
