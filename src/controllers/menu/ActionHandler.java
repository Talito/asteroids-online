package controllers.menu;


abstract public class ActionHandler {

	private String error;
	private boolean success;

	public boolean failed() {
		return !success;
	}
	
	public boolean success() {
		return success;
	}
	
	public void setError(String err) {
		this.error = err;
	}
	
	public String getError() {
		return this.error;
	}
	
	public void setResult(boolean result) {
		this.success = result;
	}
	
}