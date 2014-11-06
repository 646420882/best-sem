package com.perfect.utils.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baizz on 2014-10-10.
 *
 * @author baizz
 * @see com.perfect.utils.excel.XSSFSheetHandler
 * @deprecated
 */
public class SheetContentsHandler extends XSSFSheetHandler {

    private int bufRowSize, curSheetIndex = -1;
    private List<List<List<Object>>> sheetsData = new ArrayList<>();
    private List<List<Object>> sheetData;

    SheetContentsHandler(int bufRowSize) {
        this.bufRowSize = bufRowSize;
    }

    public List<List<List<Object>>> getSheetsData() {
        return sheetsData;
    }

    public List<List<Object>> getSheetData(int sheetIndex) {
        return sheetsData.get(sheetIndex);
    }

    @Override
    protected void rowMap(int sheetIndex, int rowIndex, List<Object> row) {
        if (curSheetIndex != sheetIndex) {
            sheetData = new ArrayList<>(sheetIndex == 0 ? bufRowSize : sheetData.size() / 2);
            sheetsData.add(sheetData);
            curSheetIndex = sheetIndex;
        }

        sheetData.add(row);
    }
}
