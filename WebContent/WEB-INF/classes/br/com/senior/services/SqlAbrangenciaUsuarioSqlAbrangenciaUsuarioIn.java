/**
 * SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.senior.services;

public class SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn  implements java.io.Serializable {
    private java.lang.String flowInstanceID;

    private java.lang.String flowName;

    private br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL[] SQL;

    public SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn() {
    }

    public SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn(
           java.lang.String flowInstanceID,
           java.lang.String flowName,
           br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL[] SQL) {
           this.flowInstanceID = flowInstanceID;
           this.flowName = flowName;
           this.SQL = SQL;
    }


    /**
     * Gets the flowInstanceID value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.
     * 
     * @return flowInstanceID
     */
    public java.lang.String getFlowInstanceID() {
        return flowInstanceID;
    }


    /**
     * Sets the flowInstanceID value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.
     * 
     * @param flowInstanceID
     */
    public void setFlowInstanceID(java.lang.String flowInstanceID) {
        this.flowInstanceID = flowInstanceID;
    }


    /**
     * Gets the flowName value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.
     * 
     * @return flowName
     */
    public java.lang.String getFlowName() {
        return flowName;
    }


    /**
     * Sets the flowName value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.
     * 
     * @param flowName
     */
    public void setFlowName(java.lang.String flowName) {
        this.flowName = flowName;
    }


    /**
     * Gets the SQL value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.
     * 
     * @return SQL
     */
    public br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL[] getSQL() {
        return SQL;
    }


    /**
     * Sets the SQL value for this SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.
     * 
     * @param SQL
     */
    public void setSQL(br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL[] SQL) {
        this.SQL = SQL;
    }

    public br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL getSQL(int i) {
        return this.SQL[i];
    }

    public void setSQL(int i, br.com.senior.services.SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL _value) {
        this.SQL[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn)) return false;
        SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn other = (SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.flowInstanceID==null && other.getFlowInstanceID()==null) || 
             (this.flowInstanceID!=null &&
              this.flowInstanceID.equals(other.getFlowInstanceID()))) &&
            ((this.flowName==null && other.getFlowName()==null) || 
             (this.flowName!=null &&
              this.flowName.equals(other.getFlowName()))) &&
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
        if (getFlowInstanceID() != null) {
            _hashCode += getFlowInstanceID().hashCode();
        }
        if (getFlowName() != null) {
            _hashCode += getFlowName().hashCode();
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
        new org.apache.axis.description.TypeDesc(SqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.senior.com.br", "sqlAbrangenciaUsuarioSqlAbrangenciaUsuarioIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flowInstanceID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flowInstanceID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flowName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flowName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SQL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SQL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://services.senior.com.br", "sqlAbrangenciaUsuarioSqlAbrangenciaUsuarioInSQL"));
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
