/**
 * G5SeniorServicesLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.senior.services;

public class G5SeniorServicesLocator extends org.apache.axis.client.Service implements br.com.senior.services.G5SeniorServices {

    public G5SeniorServicesLocator() {
    }


    public G5SeniorServicesLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public G5SeniorServicesLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort
    private java.lang.String rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort_address = "http://192.168.1.222:8080/g5-senior-services/rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario";

    public java.lang.String getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortAddress() {
        return rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortWSDDServiceName = "rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort";

    public java.lang.String getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortWSDDServiceName() {
        return rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortWSDDServiceName;
    }

    public void setrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortWSDDServiceName(java.lang.String name) {
        rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortWSDDServiceName = name;
    }

    public br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort(endpoint);
    }

    public br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortBindingStub _stub = new br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortBindingStub(portAddress, this);
            _stub.setPortName(getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortEndpointAddress(java.lang.String address) {
        rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuario.class.isAssignableFrom(serviceEndpointInterface)) {
                br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortBindingStub _stub = new br.com.senior.services.Rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortBindingStub(new java.net.URL(rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort_address), this);
                _stub.setPortName(getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort".equals(inputPortName)) {
            return getrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.senior.com.br", "g5-senior-services");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services.senior.com.br", "rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("rubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPort".equals(portName)) {
            setrubi_Synccom_senior_g5_rh_fp_SqlAbrangenciaUsuarioPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
