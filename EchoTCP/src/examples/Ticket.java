package examples;

import java.net.SocketAddress;

public class Ticket {
	
	private SocketAddress creator;
	private String type;
	private String stock_name;
	private int quantity;
	private int price;
	
	public Ticket(SocketAddress creator ,String s) {
		this.setCreator(creator);
		String delimiter = "[,]+";
		String[] tokens = s.split(delimiter);
		this.setType(tokens[0]);
		this.setStock_name(tokens[1]);
		this.setQuantity(Integer.parseInt(tokens[2]));
		this.setPrice(Integer.parseInt(tokens[3]));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public SocketAddress getCreator() {
		return creator;
	}

	public void setCreator(SocketAddress creator) {
		this.creator = creator;
	}
	
	public String toString(){
		return "Creator : "+this.creator+"; Type : "+this.type+"; Stock name : "+this.stock_name+"; Quantity : "+this.quantity+"; Price :"+this.price+";";
	}
	
}
