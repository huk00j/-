package 옥션;

public class marketDTO {

	private String id;
	private String name;
	private String product;
	private String amount;
	private String cost;
	private String no;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
	public String[] saveDB() { // 배열 자체
		String returnData[] = new String[6];
		returnData[0] = this.no;
		returnData[1] = this.id;
		returnData[2] = this.name;
		returnData[3] = this.product;
		returnData[4] = this.amount;
		returnData[5] = this.cost;
		
		return returnData;
		
	}
	
}
