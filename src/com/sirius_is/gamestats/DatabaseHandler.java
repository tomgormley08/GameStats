package com.sirius_is.gamestats;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "gamestatsDB";

	// Contacts table name
	private static final String TABLE_CONTACTS = "contacts";

	// Contacts Table Columns names
	private static final String CONTACT_ID = "id";

	private static final String CONTACT_N0 = "number";
	private static final String CONTACT_NICKNAME = "nickname";
	private static final String CONTACT_FIRSTNAME = "firstname";
	private static final String CONTACT_LASTNAME = "lastname";
	private static final String CONTACT_PH_NO = "phone_number";
	private static final String CONTACT_EMAIL = "email";

	// Game statisics events table name
	private static final String TABLE_GAMEEVENTS = "gameevents";

	// Contacts Table Columns names
	private static final String EVENT_CONTACT_ID = "id";
	private static final String EVENT_CONTACT_N0 = "number";
	private static final String EVENT_CONTACT_NICKNAME = "nickname";
	private static final String EVENT_DESCRIPTOR = "descriptor";
	private static final String EVENT_FIELDPOSITION = "fieldpositon";
	private static final String EVENT_FIELDSIDE = "fieldside";
	private static final String EVENT_COMMENT = "comment";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Contact Table
		String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
				+ CONTACT_ID + " INTEGER PRIMARY KEY," 
				+ CONTACT_N0 + " TEXT,"
				+ CONTACT_NICKNAME + " TEXT,"
				+ CONTACT_FIRSTNAME + " TEXT,"
				+ CONTACT_LASTNAME + " TEXT,"
				+ CONTACT_PH_NO + " TEXT,"
				+ CONTACT_EMAIL + " TEXT" + ")";
		db.execSQL(CREATE_CONTACT_TABLE);

		// Game events table
		String CREATE_GAMEEVENTS_TABLE = "CREATE TABLE " + TABLE_GAMEEVENTS + "("
				+ EVENT_CONTACT_ID + " INTEGER PRIMARY KEY," 
				+ EVENT_CONTACT_N0 + " TEXT,"
				+ EVENT_CONTACT_NICKNAME + " TEXT,"
				+ EVENT_DESCRIPTOR + " TEXT,"
				+ EVENT_FIELDPOSITION + " TEXT,"
				+ EVENT_FIELDSIDE + " TEXT."
				+ EVENT_COMMENT + " TEXT" + ")";
		db.execSQL(CREATE_GAMEEVENTS_TABLE);

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMEEVENTS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CONTACT_NICKNAME, Contact.getNickname()); // Contact Name
		values.put(CONTACT_PH_NO, Contact.getPhoneNumber()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_CONTACTS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS, new String[] { CONTACT_ID,
				CONTACT_NICKNAME, CONTACT_PH_NO }, CONTACT_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2));
		// return contact
		return contact;
	}
	
	// Getting All Contacts
	public List<Contact> getAllContacts() {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setNickname(cursor.getString(1));
				contact.setPhoneNumber(cursor.getString(2));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CONTACT_NICKNAME, contact.getNickname());
		values.put(CONTACT_PH_NO, contact.getPhoneNumber());

		// updating row
		return db.update(TABLE_CONTACTS, values, CONTACT_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS, CONTACT_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}


	// Getting contact Count
	public int getContactCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
