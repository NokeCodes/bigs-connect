// DO NOT EDIT.  Make changes to Big.java instead.
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
public abstract class _Big extends  ERXGenericRecord {
  public static final String ENTITY_NAME = "Big";

  // Attribute Keys
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> PASSWD = new ERXKey<String>("passwd");
  public static final ERXKey<Boolean> STATUS = new ERXKey<Boolean>("status");

  // Relationship Keys
  public static final ERXKey<org.bbbs.component.CheckInOut> CHECK_IN_OUTS = new ERXKey<org.bbbs.component.CheckInOut>("checkInOuts");

  // Attributes
  public static final String NAME_KEY = NAME.key();
  public static final String PASSWD_KEY = PASSWD.key();
  public static final String STATUS_KEY = STATUS.key();

  // Relationships
  public static final String CHECK_IN_OUTS_KEY = CHECK_IN_OUTS.key();

  private static final Logger log = LoggerFactory.getLogger(_Big.class);

  public Big localInstanceIn(EOEditingContext editingContext) {
    Big localInstance = (Big)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String name() {
    return (String) storedValueForKey(_Big.NAME_KEY);
  }

  public void setName(String value) {
    log.debug( "updating name from {} to {}", name(), value);
    takeStoredValueForKey(value, _Big.NAME_KEY);
  }

  public String passwd() {
    return (String) storedValueForKey(_Big.PASSWD_KEY);
  }

  public void setPasswd(String value) {
    log.debug( "updating passwd from {} to {}", passwd(), value);
    takeStoredValueForKey(value, _Big.PASSWD_KEY);
  }

  public Boolean status() {
    return (Boolean) storedValueForKey(_Big.STATUS_KEY);
  }

  public void setStatus(Boolean value) {
    log.debug( "updating status from {} to {}", status(), value);
    takeStoredValueForKey(value, _Big.STATUS_KEY);
  }

  public NSArray<org.bbbs.component.CheckInOut> checkInOuts() {
    return (NSArray<org.bbbs.component.CheckInOut>)storedValueForKey(_Big.CHECK_IN_OUTS_KEY);
  }

  public NSArray<org.bbbs.component.CheckInOut> checkInOuts(EOQualifier qualifier) {
    return checkInOuts(qualifier, null, false);
  }

  public NSArray<org.bbbs.component.CheckInOut> checkInOuts(EOQualifier qualifier, boolean fetch) {
    return checkInOuts(qualifier, null, fetch);
  }

  public NSArray<org.bbbs.component.CheckInOut> checkInOuts(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<org.bbbs.component.CheckInOut> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = ERXQ.equals(org.bbbs.component.CheckInOut.BIG_KEY, this);

      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        fullQualifier = ERXQ.and(qualifier, inverseQualifier);
      }

      results = org.bbbs.component.CheckInOut.fetchCheckInOuts(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = checkInOuts();
      if (qualifier != null) {
        results = (NSArray<org.bbbs.component.CheckInOut>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<org.bbbs.component.CheckInOut>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }

  public void addToCheckInOuts(org.bbbs.component.CheckInOut object) {
    includeObjectIntoPropertyWithKey(object, _Big.CHECK_IN_OUTS_KEY);
  }

  public void removeFromCheckInOuts(org.bbbs.component.CheckInOut object) {
    excludeObjectFromPropertyWithKey(object, _Big.CHECK_IN_OUTS_KEY);
  }

  public void addToCheckInOutsRelationship(org.bbbs.component.CheckInOut object) {
    log.debug("adding {} to checkInOuts relationship", object);
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
      addToCheckInOuts(object);
    }
    else {
      addObjectToBothSidesOfRelationshipWithKey(object, _Big.CHECK_IN_OUTS_KEY);
    }
  }

  public void removeFromCheckInOutsRelationship(org.bbbs.component.CheckInOut object) {
    log.debug("removing {} from checkInOuts relationship", object);
    if (er.extensions.eof.ERXGenericRecord.InverseRelationshipUpdater.updateInverseRelationships()) {
      removeFromCheckInOuts(object);
    }
    else {
      removeObjectFromBothSidesOfRelationshipWithKey(object, _Big.CHECK_IN_OUTS_KEY);
    }
  }

  public org.bbbs.component.CheckInOut createCheckInOutsRelationship() {
    EOEnterpriseObject eo = EOUtilities.createAndInsertInstance(editingContext(),  org.bbbs.component.CheckInOut.ENTITY_NAME );
    addObjectToBothSidesOfRelationshipWithKey(eo, _Big.CHECK_IN_OUTS_KEY);
    return (org.bbbs.component.CheckInOut) eo;
  }

  public void deleteCheckInOutsRelationship(org.bbbs.component.CheckInOut object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, _Big.CHECK_IN_OUTS_KEY);
    editingContext().deleteObject(object);
  }

  public void deleteAllCheckInOutsRelationships() {
    Enumeration<org.bbbs.component.CheckInOut> objects = checkInOuts().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteCheckInOutsRelationship(objects.nextElement());
    }
  }


  public static Big createBig(EOEditingContext editingContext, String name
, String passwd
, Boolean status
) {
    Big eo = (Big) EOUtilities.createAndInsertInstance(editingContext, _Big.ENTITY_NAME);
    eo.setName(name);
    eo.setPasswd(passwd);
    eo.setStatus(status);
    return eo;
  }

  public static ERXFetchSpecification<Big> fetchSpec() {
    return new ERXFetchSpecification<Big>(_Big.ENTITY_NAME, null, null, false, true, null);
  }

  public static NSArray<Big> fetchAllBigs(EOEditingContext editingContext) {
    return _Big.fetchAllBigs(editingContext, null);
  }

  public static NSArray<Big> fetchAllBigs(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _Big.fetchBigs(editingContext, null, sortOrderings);
  }

  public static NSArray<Big> fetchBigs(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    ERXFetchSpecification<Big> fetchSpec = new ERXFetchSpecification<Big>(_Big.ENTITY_NAME, qualifier, sortOrderings);
    NSArray<Big> eoObjects = fetchSpec.fetchObjects(editingContext);
    return eoObjects;
  }

  public static Big fetchBig(EOEditingContext editingContext, String keyName, Object value) {
    return _Big.fetchBig(editingContext, ERXQ.equals(keyName, value));
  }

  public static Big fetchBig(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<Big> eoObjects = _Big.fetchBigs(editingContext, qualifier, null);
    Big eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Big that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Big fetchRequiredBig(EOEditingContext editingContext, String keyName, Object value) {
    return _Big.fetchRequiredBig(editingContext, ERXQ.equals(keyName, value));
  }

  public static Big fetchRequiredBig(EOEditingContext editingContext, EOQualifier qualifier) {
    Big eoObject = _Big.fetchBig(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Big that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Big localInstanceIn(EOEditingContext editingContext, Big eo) {
    Big localInstance = (eo == null) ? null : ERXEOControlUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
