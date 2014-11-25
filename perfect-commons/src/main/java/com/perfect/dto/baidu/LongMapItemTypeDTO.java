package com.perfect.dto.baidu;

/**
 * Created by XiaoWei on 2014/11/25.
 */
public class LongMapItemTypeDTO {
    protected String key;
    protected long value;

    /**
     * Gets the value of the key property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the value property.
     *
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     */
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongMapItemType [key=" + key + ", value=" + value + "]";
    }
}
