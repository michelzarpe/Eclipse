package br.com.senior.services;

public class Rubi_Synccom_senior_g5_rh_fp_demonstracaoProxy implements br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_demonstracao {
  private String _endpoint = null;
  private br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_demonstracao rubi_Synccom_senior_g5_rh_fp_demonstracao = null;
  
  public Rubi_Synccom_senior_g5_rh_fp_demonstracaoProxy() {
    _initRubi_Synccom_senior_g5_rh_fp_demonstracaoProxy();
  }
  
  public Rubi_Synccom_senior_g5_rh_fp_demonstracaoProxy(String endpoint) {
    _endpoint = endpoint;
    _initRubi_Synccom_senior_g5_rh_fp_demonstracaoProxy();
  }
  
  private void _initRubi_Synccom_senior_g5_rh_fp_demonstracaoProxy() {
    try {
      rubi_Synccom_senior_g5_rh_fp_demonstracao = (new br.com.senior.services.G5SeniorServicesLocator()).getrubi_Synccom_senior_g5_rh_fp_demonstracaoPort();
      if (rubi_Synccom_senior_g5_rh_fp_demonstracao != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)rubi_Synccom_senior_g5_rh_fp_demonstracao)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)rubi_Synccom_senior_g5_rh_fp_demonstracao)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (rubi_Synccom_senior_g5_rh_fp_demonstracao != null)
      ((javax.xml.rpc.Stub)rubi_Synccom_senior_g5_rh_fp_demonstracao)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_demonstracao getRubi_Synccom_senior_g5_rh_fp_demonstracao() {
    if (rubi_Synccom_senior_g5_rh_fp_demonstracao == null)
      _initRubi_Synccom_senior_g5_rh_fp_demonstracaoProxy();
    return rubi_Synccom_senior_g5_rh_fp_demonstracao;
  }
  
  public br.com.senior.services.DemonstracaoSomaOut soma(java.lang.String user, java.lang.String password, int encryption, br.com.senior.services.DemonstracaoSomaIn parameters) throws java.rmi.RemoteException{
    if (rubi_Synccom_senior_g5_rh_fp_demonstracao == null)
      _initRubi_Synccom_senior_g5_rh_fp_demonstracaoProxy();
    return rubi_Synccom_senior_g5_rh_fp_demonstracao.soma(user, password, encryption, parameters);
  }
  
  
}