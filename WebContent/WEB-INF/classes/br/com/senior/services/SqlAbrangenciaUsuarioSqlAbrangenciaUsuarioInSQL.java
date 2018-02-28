/**
 * SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.senior.services;

public class SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL  implements java.io.Serializable {
    private java.lang.String comandoSQL;

    private java.lang.String mensagem;

    private java.lang.Integer status;

    public SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL() {
    }

    public SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL(
           java.lang.String comandoSQL,
           java.lang.String mensagem,
           java.lang.Integer status) {
           this.comandoSQL = comandoSQL;
           this.mensagem = mensagem;
           this.status = status;
    }


    /**
     * Gets the comandoSQL value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.
     * 
     * @return comandoSQL
     */
    public java.lang.String getComandoSQL() {
        return comandoSQL;
    }


    /**
     * Sets the comandoSQL value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.
     * 
     * @param comandoSQL
     */
    public void setComandoSQL(java.lang.String comandoSQL) {
        this.comandoSQL = comandoSQL;
    }


    /**
     * Gets the mensagem value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the status value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.
     * 
     * @return status
     */
    public java.lang.Integer getStatus() {
        return status;
    }


    /**
     * Sets the status value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.
     * 
     * @param status
     */
    public void setStatus(java.lang.Integer status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL)) return false;
        SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL other = (SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comandoSQL==null && other.getComandoSQL()==null) || 
             (this.comandoSQL!=null &&
              this.comandoSQL.equals(other.getComandoSQL()))) &&
            ((this.mensagem==null && other.getMensagem()==null) || 
             (this.mensagem!=null &&
              this.mensagem.equals(other.getMensagem()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getComandoSQL() != null) {
            _hashCode += getComandoSQL().hashCode();
        }
        if (getMensagem() != null) {
            _hashCode += getMensagem().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.senior.com.br", "sqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comandoSQL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comandoSQL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensagem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensagem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
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
