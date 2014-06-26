package com.perfect.autosdk.common;

import java.util.ArrayList;
import java.util.List;

public class OptType {

    protected List<StringMapItemType> optString;
    protected List<IntMapItemType> optInt;
    protected List<LongMapItemType> optLong;
    protected List<FloatMapItemType> optFloat;
    protected List<DoubleMapItemType> optDouble;

    /**
     * Gets the value of the optString property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optString property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptString().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMapItemType }
     * 
     * 
     */
    public List<StringMapItemType> getOptString() {
        if (optString == null) {
            optString = new ArrayList<StringMapItemType>();
        }
        return this.optString;
    }

    /**
     * Gets the value of the optInt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optInt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptInt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntMapItemType }
     * 
     * 
     */
    public List<IntMapItemType> getOptInt() {
        if (optInt == null) {
            optInt = new ArrayList<IntMapItemType>();
        }
        return this.optInt;
    }

    /**
     * Gets the value of the optLong property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optLong property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptLong().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LongMapItemType }
     * 
     * 
     */
    public List<LongMapItemType> getOptLong() {
        if (optLong == null) {
            optLong = new ArrayList<LongMapItemType>();
        }
        return this.optLong;
    }

    /**
     * Gets the value of the optFloat property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optFloat property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptFloat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FloatMapItemType }
     * 
     * 
     */
    public List<FloatMapItemType> getOptFloat() {
        if (optFloat == null) {
            optFloat = new ArrayList<FloatMapItemType>();
        }
        return this.optFloat;
    }

    /**
     * Gets the value of the optDouble property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optDouble property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptDouble().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DoubleMapItemType }
     * 
     * 
     */
    public List<DoubleMapItemType> getOptDouble() {
        if (optDouble == null) {
            optDouble = new ArrayList<DoubleMapItemType>();
        }
        return this.optDouble;
    }

    @Override
    public String toString() {
        return "OptType [optString=" + optString + ", optInt=" + optInt + ", optLong=" + optLong + ", optFloat="
                + optFloat + ", optDouble=" + optDouble + "]";
    }

}
