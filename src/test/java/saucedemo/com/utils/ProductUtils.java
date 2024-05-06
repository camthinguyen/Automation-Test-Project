package saucedemo.com.utils;

public class ProductUtils {
	public static String convertProductNameToAddProductButtonId(String name) {
		String prefix = "add-to-cart-";
		if(name != null) {
			prefix = prefix.concat(name.replace(" ", "-").toLowerCase());
		}
		return prefix;
	}
	
	public static String convertProductNameToRemoveProductButtonId(String name) {
		String prefix = "remove-";
		if(name != null) {
			prefix = prefix.concat(name.replace(" ", "-").toLowerCase());
		}
		return prefix;
	}
	
	public static void main(String[] args) {
		String s =  "";
		Integer.parseInt(s);
	}
}
