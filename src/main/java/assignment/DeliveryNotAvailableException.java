package assignment;
class DeliveryNotAvailableException extends Exception{
	public DeliveryNotAvailableException() {
		super("\nWe are sorry to inform that we only accept order within Melaka state.");
	}
}

