// DO NOT EDIT.  Make changes to CheckInOut.java instead.
package org.bbbs.component;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;

import er.extensions.eof.*;
import er.extensions.foundation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public abstract class _CheckInOut extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "CheckInOut";

  // Attribute Keys
  public static final ERXKey<Boolean> IS_CHECKOUT = new ERXKey<Boolean>("isCheckout");
  public static final ERXKey<String> LOCATION = new ERXKey<String>("location");
  public static final ERXKey<org.joda.time.DateTime> MARK = new ERXKey<org.joda.time.DateTime>("mark");

  // Relationship Keys
  public static final ERXKey<org.bbbs.component.Big> BIG = new ERXKey<org.bbbs.component.Big>("big");

  // Attributes
  public static final String IS_CHECKOUT_KEY = IS_CHECKOUT.key();
  public static final String LOCATION_KEY = LOCATION.key();
  public static final String MARK_KEY = MARK.key();

  // Relationships
  public static final String BIG_KEY = BIG.key();

  private static final Logger log = LoggerFactory.getLogger(_CheckInOut.class);

  public CheckInOut localInstanceIn(EOEditingContext editingContext) {
    CheckInOut localInstance = (CheckInOut)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Boolean isCheckout() {
    return (Boolean) storedValueForKey(_CheckInOut.IS_CHECKOUT_KEY);
  }

  public void setIsCheckout(Boolean value) {
    log.debug( "updating isCheckout from {} to {}", isCheckout(), value);
    takeStoredValueForKey(value, _CheckInOut.IS_CHECKOUT_KEY);
  }

  public String location() {
    return (String) storedValueForKey(_CheckInOut.LOCATION_KEY);
  }

  public void setLocation(String value) {
    log.debug( "updating location from {} to {}", location(), value);
    takeStoredValueForKey(value, _CheckInOut.LOCATION_KEY);
  }

  public org.joda.time.DateTime mark() {
    return (org.joda.time.DateTime) storedValueForKey(_CheckInOut.MARK_KEY);
  }

  public void setMark(org.joda.time.DateTime value) {
    log.debug( "updating mark from {} to {}", mark(), value);
    takeStoredValueForKey(value, _CheckInOut.MARK_KEY);
  }

  public org.bbbs.component.Big big() {
    return (org.bbbs.component.Big)storedValueForKey(_CheckInOut.BIG_KEY);
  }

  public void setBig(org.bbbs.component.Big value) {
    takeStoredValueForKey(value, _CheckInOut.BIG_KEY);
  }

  public void setBigRelationship(org.bbbs.component.Big value) {
    log.debug("updating big from {} to {}", big(), value);
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
      setBig(value);
    }
    else if (value == null) {
      org.bbbs.component.Big oldValue = big();
      if (oldValue != null) {
        removeObjectFromBothSidesOfRelationshipWithKey(oldValue, _CheckInOut.BIG_KEY);
      }
    } else {
      addObjectToBothSidesOfRelationshipWithKey(value, _CheckInOut.BIG_KEY);
    }
  }


  public static CheckInOut createCheckInOut(EOEditingContext editingContext, Boolean isCheckout
, String location
, org.joda.time.DateTime mark
, org.bbbs.component.Big big) {
    CheckInOut eo = (CheckInOut) EOUtilities.createAndInsertInstance(editingContext, _CheckInOut.ENTITY_NAME);
    eo.setIsCheckout(isCheckout);
    eo.setLocation(location);
    eo.setMark(mark);
    eo.setBigRelationship(big);
    return eo;
  }

  public static ERXFetchSpecification<CheckInOut> fetchSpec() {
    return new ERXFetchSpecification<CheckInOut>(_CheckInOut.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<CheckInOut> fetchAllCheckInOuts(EOEditingContext editingContext) {
    return _CheckInOut.fetchAllCheckInOuts(editingContext, null);
  }

  public static NSArray<CheckInOut> fetchAllCheckInOuts(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _CheckInOut.fetchCheckInOuts(editingContext, null, sortOrderings);
  }

  public static NSArray<CheckInOut> fetchCheckInOuts(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<CheckInOut> fetchSpec = new ERXFetchSpecification<CheckInOut>(_CheckInOut.ENTITY_NAME, qualifier, sortOrderings);
    NSArray<CheckInOut> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static CheckInOut fetchCheckInOut(EOEditingContext editingContext, String keyName, Object value) {
    return _CheckInOut.fetchCheckInOut(editingContext, ERXQ.equals(keyName, value));
  }

  public static CheckInOut fetchCheckInOut(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<CheckInOut> eoObjects = _CheckInOut.fetchCheckInOuts(editingContext, qualifier, null);
    CheckInOut eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one CheckInOut that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CheckInOut fetchRequiredCheckInOut(EOEditingContext editingContext, String keyName, Object value) {
    return _CheckInOut.fetchRequiredCheckInOut(editingContext, ERXQ.equals(keyName, value));
  }

  public static CheckInOut fetchRequiredCheckInOut(EOEditingContext editingContext, EOQualifier qualifier) {
    CheckInOut eoObject = _CheckInOut.fetchCheckInOut(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no CheckInOut that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static CheckInOut localInstanceIn(EOEditingContext editingContext, CheckInOut eo) {
    CheckInOut localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
