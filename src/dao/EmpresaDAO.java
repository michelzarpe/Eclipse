package dao;

import bean.Empresa;

public interface EmpresaDAO extends BaseDAO<Empresa> {
	Empresa getEmpresaByEmpSap(int empSap);
}
