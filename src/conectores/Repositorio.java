package conectores;

import java.util.ArrayList;

abstract class Repositorio {

	private ArrayList<String> SQLScripts = new ArrayList<String>();
	
	public ArrayList<String> getSQLScripts() {
		return SQLScripts;
	}
	public void setSQLScripts(ArrayList<String> sQLScripts) {
		SQLScripts = sQLScripts;
	}
	
	public abstract boolean insert(Object nuevo);
	public abstract boolean delete(Object aBorrar);
	public abstract boolean update(Object modificaciones);
	public abstract boolean check(Object objeto);
	public abstract Object get(String primaryKey);

}
