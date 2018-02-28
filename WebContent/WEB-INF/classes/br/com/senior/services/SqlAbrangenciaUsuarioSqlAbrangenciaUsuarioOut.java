/**
 * SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.senior.services;

public class SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut  implements java.io.Serializable {
    private java.lang.String erroExecucao;

    private br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOutSQL[] SQL;

    public SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut() {
    }

    public SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut(
           java.lang.String erroExecucao,
           br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOutSQL[] SQL) {
           this.erroExecucao = erroExecucao;
           this.SQL = SQL;
    }


    /**
     * Gets the erroExecucao value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut.
     * 
     * @return erroExecucao
     */
    public java.lang.String getErroExecucao() {
        return erroExecucao;
    }


    /**
     * Sets the erroExecucao value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut.
     * 
     * @param erroExecucao
     */
    public void setErroExecucao(java.lang.String erroExecucao) {
        this.erroExecucao = erroExecucao;
    }


    /**
     * Gets the SQL value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut.
     * 
     * @return SQL
     */
    public br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOutSQL[] getSQL() {
        return SQL;
    }


    /**
     * Sets the SQL value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut.
     * 
     * @param SQL
     */
    public void setSQL(br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOutSQL[] SQL) {
        this.SQL = SQL;
    }

    public br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOutSQL getSQL(int i) {
        return this.SQL[i];
    }

    public void setSQL(int i, br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOutSQL _value) {
        this.SQL[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut)) return false;
        SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut other = (SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut) obj;
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
            ((this.SQL==null && other.getSQL()==null) || 
             (this.SQL!=null &&
              java.util.Arrays.equals(this.SQL, other.getSQL())));
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
        if (getSQL() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSQL());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSQL(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.senior.com.br", "sqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOut"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("erroExecucao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "erroExecucao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SQL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://services.senior.com.br", "sqlAbrangenciaUsuarioSqlAbrangenciaUsuarioOutSQL"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
