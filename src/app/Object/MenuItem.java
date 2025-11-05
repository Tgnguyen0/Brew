package app.Object;

import java.io.Serializable;
import java.util.Objects;

public class MenuItem implements Serializable {
    private String itemId;
    private String name;
    private float price;
    private String category;
    private String description;

    public MenuItem() {
        this.itemId = "";
        this.name = "";
        this.price = 0;
        this.category = "";
        this.description = "";
    }

    public MenuItem(String itemId, String name, float price, String category, String description) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    public MenuItem(String name, float price, String category, String description) {
        this.itemId = "";
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }


    public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public int hashCode() {
        return Objects.hashCode(itemId);
    }

}
