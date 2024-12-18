/*
 * package com.iot.domain;
 * 
 * import java.io.Serializable; import java.util.Objects;
 * 
 * public class Category extends PersistantObject implements Serializable {
 * 
 * private static final long serialVersionUID = 1L;
 * 
 * private Integer categoryId; private String name;
 * 
 * public Integer getCategoryId() { return categoryId; }
 * 
 * public void setCategoryId(Integer categoryId) { this.categoryId = categoryId;
 * }
 * 
 * public String getName() { return name; }
 * 
 * public void setName(String name) { this.name = name; }
 * 
 * @Override public boolean equals(Object o) { if (this == o) return true; if (o
 * == null || getClass() != o.getClass()) return false; Category category =
 * (Category) o; return name != null && name.equals(category.name); }
 * 
 * @Override public int hashCode() { return Objects.hash(name); } }
 * 
 */

package com.iot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Only include non-null fields in JSON
public class Category extends PersistantObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("categoryId")
    private Integer categoryId;

    @JsonProperty("name")
    private String name;

    // Default constructor
    public Category() {}

    public Category(Integer categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name != null && name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
