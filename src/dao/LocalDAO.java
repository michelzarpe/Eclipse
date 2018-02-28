package dao;

import bean.Local;

public interface LocalDAO extends BaseDAO<Local> {
	
	Local loadByNumLoc(int tabOrg, int numLoc);
}
