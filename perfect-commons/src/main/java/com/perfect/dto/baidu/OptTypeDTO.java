package com.perfect.dto.baidu;

import com.perfect.dto.baidu.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 14/11/20.
 */
public class OptTypeDTO {
    protected List<StringMapItemTypeDTO> optString;
    protected List<IntMapItemTypeDTO> optInt;
    protected List<LongMapItemTypeDTO> optLong;
    protected List<FloatMapItemTypeDTO> optFloat;
    protected List<DoubleMapItemTypeDTO> optDouble;

    /**
     * Gets the value of the optString property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optString property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptString().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMapItemTypeDTO }
     */
    public List<StringMapItemTypeDTO> getOptString() {
        if (optString == null) {
            optString = new ArrayList<StringMapItemTypeDTO>();
        }
        return this.optString;
    }

    /**
     * Gets the value of the optInt property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optInt property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptInt().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IntMapItemTypeDTO }
     */
    public List<IntMapItemTypeDTO> getOptInt() {
        if (optInt == null) {
            optInt = new ArrayList<IntMapItemTypeDTO>();
        }
        return this.optInt;
    }

    /**
     * Gets the value of the optLong property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optLong property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptLong().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link LongMapItemTypeDTO }
     */
    public List<LongMapItemTypeDTO> getOptLong() {
        if (optLong == null) {
            optLong = new ArrayList<LongMapItemTypeDTO>();
        }
        return this.optLong;
    }

    /**
     * Gets the value of the optFloat property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optFloat property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptFloat().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link FloatMapItemTypeDTO }
     */
    public List<FloatMapItemTypeDTO> getOptFloat() {
        if (optFloat == null) {
            optFloat = new ArrayList<FloatMapItemTypeDTO>();
        }
        return this.optFloat;
    }

    /**
     * Gets the value of the optDouble property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optDouble property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptDouble().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link DoubleMapItemTypeDTO }
     */
    public List<DoubleMapItemTypeDTO> getOptDouble() {
        if (optDouble == null) {
            optDouble = new ArrayList<DoubleMapItemTypeDTO>();
        }
        return this.optDouble;
    }

    @Override
    public String toString() {
        return "OptType [optString=" + optString + ", optInt=" + optInt + ", optLong=" + optLong + ", optFloat="
                + optFloat + ", optDouble=" + optDouble + "]";
    }
}
