package interaface;

public interface Warnings {

	public void warningDB();

	public void SameTableName(String tableName);

	public void noTableName(String tableName);
	
	public void printMessage(String message);

}
