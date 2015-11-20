
package com.perfect.autosdk_v4.core;

import java.util.ArrayList;
import java.util.List;

public class ResHeader {

    protected String desc;
    protected List<Failure> failures;
    protected Integer oprs;
    protected Integer oprtime;
    protected Integer quota;
    protected Integer rquota;
    protected int status;

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the failures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the failures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFailures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * 
     * 
     */
    public List<Failure> getFailures() {
        if (failures == null) {
            failures = new ArrayList<Failure>();
        }
        return this.failures;
    }

    /**
     * Gets the value of the oprs property.
     * 
     * @return
     *     possible object is
     *     
     */
    public Integer getOprs() {
        return oprs;
    }

    /**
     * Sets the value of the oprs property.
     * 
     * @param value
     *     allowed object is
     *     
     */
    public void setOprs(Integer value) {
        this.oprs = value;
    }

    /**
     * Gets the value of the oprtime property.
     * 
     * @return
     *     possible object is
     *     
     */
    public Integer getOprtime() {
        return oprtime;
    }

    /**
     * Sets the value of the oprtime property.
     * 
     * @param value
     *     allowed object is
     *     
     */
    public void setOprtime(Integer value) {
        this.oprtime = value;
    }

    /**
     * Gets the value of the quota property.
     * 
     * @return
     *     possible object is
     *     
     */
    public Integer getQuota() {
        return quota;
    }

    /**
     * Sets the value of the quota property.
     * 
     * @param value
     *     allowed object is
     *     
     */
    public void setQuota(Integer value) {
        this.quota = value;
    }

    /**
     * Gets the value of the rquota property.
     * 
     * @return
     *     possible object is
     *     
     */
    public Integer getRquota() {
        return rquota;
    }

    /**
     * Sets the value of the rquota property.
     * 
     * @param value
     *     allowed object is
     *     
     */
    public void setRquota(Integer value) {
        this.rquota = value;
    }

    /**
     * Gets the value of the status property.
     * 
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(int value) {
        this.status = value;
    }

	@Override
	public String toString() {
		return "ResHeader [desc=" + desc + ", failures=" + failures + ", oprs="
				+ oprs + ", oprtime=" + oprtime + ", quota=" + quota
				+ ", rquota=" + rquota + ", status=" + status + "]";
	}

}
