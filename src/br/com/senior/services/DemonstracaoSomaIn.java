/**
 * DemonstracaoSomaIn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.com.senior.services;

public class DemonstracaoSomaIn  implements java.io.Serializable {
    private java.lang.Integer a;

    private java.lang.Integer b;

    private java.lang.String flowInstanceID;

    private java.lang.String flowName;

    public DemonstracaoSomaIn() {
    }

    public DemonstracaoSomaIn(
           java.lang.Integer a,
           java.lang.Integer b,
           java.lang.String flowInstanceID,
           java.lang.String flowName) {
           this.a = a;
           this.b = b;
           this.flowInstanceID = flowInstanceID;
           this.flowName = flowName;
    }


    /**
     * Gets the a value for this DemonstracaoSomaIn.
     * 
     * @return a
     */
    public java.lang.Integer getA() {
        return a;
    }


    /**
     * Sets the a value for this DemonstracaoSomaIn.
     * 
     * @param a
     */
    public void setA(java.lang.Integer a) {
        this.a = a;
    }


    /**
     * Gets the b value for this DemonstracaoSomaIn.
     * 
     * @return b
     */
    public java.lang.Integer getB() {
        return b;
    }


    /**
     * Sets the b value for this DemonstracaoSomaIn.
     * 
     * @param b
     */
    public void setB(java.lang.Integer b) {
        this.b = b;
    }


    /**
     * Gets the flowInstanceID value for this DemonstracaoSomaIn.
     * 
     * @return flowInstanceID
     */
    public java.lang.String getFlowInstanceID() {
        return flowInstanceID;
    }


    /**
     * Sets the flowInstanceID value for this DemonstracaoSomaIn.
     * 
     * @param flowInstanceID
     */
    public void setFlowInstanceID(java.lang.String flowInstanceID) {
        this.flowInstanceID = flowInstanceID;
    }


    /**
     * Gets the flowName value for this DemonstracaoSomaIn.
     * 
     * @return flowName
     */
    public java.lang.String getFlowName() {
        return flowName;
    }


    /**
     * Sets the flowName value for this DemonstracaoSomaIn.
     * 
     * @param flowName
     */
    public void setFlowName(java.lang.String flowName) {
        this.flowName = flowName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DemonstracaoSomaIn)) return false;
        DemonstracaoSomaIn other = (DemonstracaoSomaIn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.a==null && other.getA()==null) || 
             (this.a!=null &&
              this.a.equals(other.getA()))) &&
            ((this.b==null && other.getB()==null) || 
             (this.b!=null &&
              this.b.equals(other.getB()))) &&
            ((this.flowInstanceID==null && other.getFlowInstanceID()==null) || 
             (this.flowInstanceID!=null &&
              this.flowInstanceID.equals(other.getFlowInstanceID()))) &&
            ((this.flowName==null && other.getFlowName()==null) || 
             (this.flowName!=null &&
              this.flowName.equals(other.getFlowName())));
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
        if (getA() != null) {
            _hashCode += getA().hashCode();
        }
        if (getB() != null) {
            _hashCode += getB().hashCode();
        }
        if (getFlowInstanceID() != null) {
            _hashCode += getFlowInstanceID().hashCode();
        }
        if (getFlowName() != null) {
            _hashCode += getFlowName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DemonstracaoSomaIn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.senior.com.br", "demonstracaoSomaIn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("a");
        elemField.setXmlName(new javax.xml.namespace.QName("", "a"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("b");
        elemField.setXmlName(new javax.xml.namespace.QName("", "b"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
