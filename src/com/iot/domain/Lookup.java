/*
 * package com.iot.domain;
 * 
 * import java.io.Serializable;
 * 
 * import com.google.gwt.i18n.client.HasDirection.Direction; public class Lookup
 * extends PersistantObject implements Serializable {
 * 
 * 
 *//**
	* 
	*//*
		 * private static final long serialVersionUID = 1L; private Integer lookupId;
		 * private Category category; private String name;
		 * 
		 * public Lookup(){} public Lookup(String itemText) { // TODO Auto-generated
		 * constructor stub }
		 * 
		 * public Integer getLookupId() { return lookupId; } public void
		 * setLookupId(Integer lookupId) { this.lookupId = lookupId; } public Category
		 * getCategory() { return category; } public void setCategory(Category category)
		 * { this.category = category; } public String getName() { return name; } public
		 * void setName(String name) { this.name = name; } public Direction getValue() {
		 * // TODO Auto-generated method stub return null; }
		 * 
		 * // @Override // public void detach() { // super.detach(); //
		 * category.detach(); // // } }
		 * 
		 * 
		 */
package com.iot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Only include non-null fields in JSON
public class Lookup extends PersistantObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("lookupId")
    private Integer lookupId;

    @JsonProperty("category")
    private Category category;  // Assuming Category is another class

    @JsonProperty("name")
    private String name;

    public Lookup() {}

    public Lookup(String itemText) {
        this.name = itemText;  // Set the name or item text
    }

    public Integer getLookupId() {
        return lookupId;
    }

    public void setLookupId(Integer lookupId) {
        this.lookupId = lookupId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // You can override toString to print a readable format for Lookup if needed
    @Override
    public String toString() {
        return "Lookup [lookupId=" + lookupId + ", category=" + category + ", name=" + name + "]";
    }
}
