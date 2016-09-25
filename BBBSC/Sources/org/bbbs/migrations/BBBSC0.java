package org.bbbs.migrations;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.ERXModelVersion;

public class BBBSC0 extends ERXMigrationDatabase.Migration {
	@Override
	public NSArray<ERXModelVersion> modelDependencies() {
		return null;
	}

	@Override
	public void downgrade(EOEditingContext editingContext,
			ERXMigrationDatabase database) throws Throwable {
		// DO NOTHING
	}

	@Override
	public void upgrade(EOEditingContext editingContext,
			ERXMigrationDatabase database) throws Throwable {
		ERXMigrationTable bigTable = database.newTableNamed("Big");
		bigTable.newLargeStringColumn("email", NOT_NULL);
		bigTable.newLargeStringColumn("name", NOT_NULL);
		bigTable.newLargeStringColumn("passwd", NOT_NULL);
		bigTable.newBooleanColumn("status", NOT_NULL);
		bigTable.create();
		bigTable.setPrimaryKey("email");

		ERXMigrationTable checkInOutTable = database
				.newTableNamed("CheckInOut");
		checkInOutTable.newLargeStringColumn("bigID", NOT_NULL);
		checkInOutTable.newIntegerColumn("id", NOT_NULL);
		checkInOutTable.newBooleanColumn("isCheckout", NOT_NULL);
		checkInOutTable.newLargeStringColumn("location", NOT_NULL);
		checkInOutTable.newTimestampColumn("mark", NOT_NULL);
		checkInOutTable.create();
		checkInOutTable.setPrimaryKey("id");

		checkInOutTable.addForeignKey("bigID", "Big", "email");
	}
}