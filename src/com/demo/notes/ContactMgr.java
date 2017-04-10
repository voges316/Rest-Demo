package com.demo.notes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactMgr {

    private static List<Contact> contactList;
    
    // Implement Singleton
    private static ContactMgr instance;
    
    private ContactMgr() { 
        setupContactList();
    }
    
    public static synchronized ContactMgr getInstance() {
        if (instance == null) {
            instance = new ContactMgr();
        }
        
        return instance;
    }
    
    public Contact create(Contact contact) {
        contactList.add(contact);
        return contact;
    }
    
    public Contact get(int id) {
        Contact result = null;
        for (Contact n : contactList) {
            if (n.getId() == id) {
                result = n;
                break;
            }
        }        
        
        return result;
    }
    
    public Contact update(Contact contact) {
        Contact result = null;
        int lookupId = contact.getId();
        
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId() == lookupId) {
                contactList.set(i, contact);
                result = contact;
                break;
            }
        }
        
        return result;
    }
    
    public Contact delete(int id) {
    	Contact result = null;
    	
    	Iterator<Contact> it = contactList.iterator();
    	while (it.hasNext()) {
    		Contact c = it.next();
    		if (c.getId() == id) {
    			result = c;
    			it.remove();
    		}
    	}
    	
    	return result;
    }
    
    public List<Contact> getContacts() {
        return contactList;
    }
    
    private static void setupContactList() {
        contactList = new ArrayList<>();
        Contact contact = new Contact(1, "bart", "simpson");
        contactList.add(contact);
        
        contact = new Contact(2, "homer", "simpson");
        contactList.add(contact);
        
        contact = new Contact(3, "marge", "simpson");
        contactList.add(contact);
    } 	
}
