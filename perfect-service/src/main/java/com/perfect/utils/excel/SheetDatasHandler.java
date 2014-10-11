package com.perfect.utils.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-10-10.
 */
class SheetDatasHandler extends RowMapper {

    private int bufRowSize, curSheetIndex = -1;
    private List<List<List<Object>>> sheetDatas = new ArrayList<>();
    private List<List<Object>> sheetData;

    SheetDatasHandler(int bufRowSize) {
        this.bufRowSize = bufRowSize;
    }

    public List<List<List<Object>>> getSheetDatas() {
        return sheetDatas;
    }

    public List<List<Object>> getSheetData(int sheetIndex) {
        return sheetDatas.get(sheetIndex);
    }

    @Override
    protected void mapRow(int sheetIndex, int rowIndex, List<Object> row) {
        if (curSheetIndex != sheetIndex) {
            sheetData = new ArrayList<>(sheetIndex == 0 ? bufRowSize : sheetData.size() / 2);
            sheetDatas.add(sheetData);
            curSheetIndex = sheetIndex;
        }

        sheetData.add(row);
    }
}
