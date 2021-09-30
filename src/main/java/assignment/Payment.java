package assignment;
class Payment {
	private int id;
	public int getId() {
		return id;
	}
	
	private String method;
	public String getMethod() {
		return method;
	}
	
	private double totalPrice;
	public double getTotalPrice() {
		return totalPrice;
	}
	
	private int orderId;
	public int getOrderId() {
		return orderId;
	}
	
	private boolean status;
	public boolean getStatus() {
		return status;
	}

	
	public Payment(int id, String method, int orderId, double totalPrice) {
		this.id = id;
		this.method = method;
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.status = false;
	}
	
	public Payment(Payment payment) {
		this.id = payment.getId();
		this.orderId = payment.getOrderId();
		this.method = payment.getMethod();
		this.totalPrice = payment.getTotalPrice();
		this.status = payment.getStatus();
	}


	public boolean Pay() {
		/*
		 * Code to retrieve data from Payment Gateway
		 * and return the paymentGatewayFlag 
		 * to indicate the payment is successful or not
		 */
		boolean paymentGatewayFlag = false;
		if (paymentGatewayFlag) {
			this.status = true;
		} else {
			this.status = false;
		}
		return this.status;
	}
}
