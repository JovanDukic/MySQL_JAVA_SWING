package interaface;

import java.util.LinkedHashSet;

public interface SetTableNames {

	public void setTableNames(LinkedHashSet<String> tableNames);
	
	public void addTableNames(String tableName);
	
	public void removeTableNames(String tableName);

}
