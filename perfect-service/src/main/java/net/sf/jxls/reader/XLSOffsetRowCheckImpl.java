package net.sf.jxls.reader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonid Vysochyn
 * 2014-11-24 refactor
 * @deprecated
 */
public class XLSOffsetRowCheckImpl implements OffsetRowCheck {
    List cellChecks = new ArrayList();
    int offset;


    public XLSOffsetRowCheckImpl() {
    }

    public XLSOffsetRowCheckImpl(int offset) {
        this.offset = offset;
    }

    public XLSOffsetRowCheckImpl(List cellChecks) {
        this.cellChecks = cellChecks;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List getCellChecks() {
        return cellChecks;
    }

    public void setCellChecks(List cellChecks) {
        this.cellChecks = cellChecks;
    }

    public boolean isCheckSuccessful(Row row) {
        if (cellChecks.isEmpty()) {
            return isRowEmpty(row);
        }
        for (int i = 0; i < cellChecks.size(); i++) {
            OffsetCellCheck offsetCellCheck = (OffsetCellCheck) cellChecks.get(i);
            if (!offsetCellCheck.isCheckSuccessful(row)) {
                return false;
            }
        }
        return true;
    }

    public boolean isCheckSuccessful(XLSRowCursor cursor) {
        if (!cursor.hasNext()) {
            return isCellChecksEmpty();
        }
        Row row = cursor.getSheet().getRow(offset + cursor.getCurrentRowNum());
        if (row == null) {
            return true;
        }
        return isCheckSuccessful(row);
    }

    private boolean isCellChecksEmpty() {
        if (cellChecks.isEmpty()) {
            return true;
        }
        for (int i = 0; i < cellChecks.size(); i++) {
            OffsetCellCheck offsetCellCheck = (OffsetCellCheck) cellChecks.get(i);
            if (!isCellCheckEmpty(offsetCellCheck)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCellCheckEmpty(OffsetCellCheck cellCheck) {
        if (cellCheck.getValue() == null) {
            return true;
        }
        if (cellCheck.getValue().toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void addCellCheck(OffsetCellCheck cellCheck) {
        cellChecks.add(cellCheck);
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (short i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (!isCellEmpty(cell)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCellEmpty(Cell cell) {
        if (cell == null) {
            return true;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return true;
            case Cell.CELL_TYPE_STRING:
                String cellValue = cell.getRichStringCellValue().getString();
                return cellValue == null || cellValue.length() == 0 || cellValue.trim().length() == 0;
            default:
                return false;
        }
    }
}
