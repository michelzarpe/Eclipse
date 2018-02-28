package br.com.senior.services;

public class Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioProxy implements br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario {
  private String _endpoint = null;
  private br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario = null;
  
  public Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioProxy() {
    _initRubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioProxy();
  }
  
  public Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioProxy(String endpoint) {
    _endpoint = endpoint;
    _initRubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioProxy();
  }
  
  private void _initRubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioProxy() {
    try {
      rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario = (new br.com.senior.services.G5SeniorServicesLocator()).getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort();
      if (rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario != null)
      ((javax.xml.rpc.Stub)rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario getRubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario() {
    if (rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario == null)
      _initRubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioProxy();
    return rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario;
  }
  
  
}