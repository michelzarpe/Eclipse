/**
 * DemonstracaoSomaOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.senior.services;

public class DemonstracaoSomaOut  implements java.io.Serializable {
    private java.lang.String erroExecucao;

    private java.lang.Integer total;

    public DemonstracaoSomaOut() {
    }

    public DemonstracaoSomaOut(
           java.lang.String erroExecucao,
           java.lang.Integer total) {
           this.erroExecucao = erroExecucao;
           this.total = total;
    }


    /**
     * Gets the erroExecucao value for this DemonstracaoSomaOut.
     * 
     * @return erroExecucao
     */
    public java.lang.String getErroExecucao() {
        return erroExecucao;
    }


    /**
     * Sets the erroExecucao value for this DemonstracaoSomaOut.
     * 
     * @param erroExecucao
     */
    public void setErroExecucao(java.lang.String erroExecucao) {
        this.erroExecucao = erroExecucao;
    }


    /**
     * Gets the total value for this DemonstracaoSomaOut.
     * 
     * @return total
     */
    public java.lang.Integer getTotal() {
        return total;
    }


    /**
     * Sets the total value for this DemonstracaoSomaOut.
     * 
     * @param total
     */
    public void setTotal(java.lang.Integer total) {
        this.total = total;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DemonstracaoSomaOut)) return false;
        DemonstracaoSomaOut other = (DemonstracaoSomaOut) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.erroExecucao==null && other.getErroExecucao()==null) || 
             (this.erroExecucao!=null &&
              this.erroExecucao.equals(other.getErroExecucao()))) &&
            ((this.total==null && other.getTotal()==null) || 
             (this.total!=null &&
              this.total.equals(other.getTotal())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getErroExecucao() != null) {
            _hashCode += getErroExecucao().hashCode();
        }
        if (getTotal() != null) {
            _hashCode += getTotal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DemonstracaoSomaOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.senior.com.br", "demonstracaoSomaOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("erroExecucao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "erroExecucao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("total");
        elemField.setXmlName(new javax.xml.namespace.QName("", "total"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
