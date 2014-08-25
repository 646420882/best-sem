package net.sf.jxls.reader;


import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Leonid Vysochyn
 */
public class XLSBlockReaderImpl extends BaseBlockReader implements XLSLoopBlockReader {
    protected final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    List beanCellMapping = new ArrayList();

    String items;
    String var;
    Class varType;
    List innerBlockReaders = new ArrayList();

    SectionCheck loopBreakCheck;

    public XLSBlockReaderImpl() {
    }

    public XLSBlockReaderImpl(int startRow, int endRow, String items, String var, Class varType) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.items = items;
        this.var = var;
        this.varType = varType;
    }

    @SuppressWarnings("unchecked")
    public XLSReadStatus read(XLSRowCursor cursor, Map beans) {
        readStatus.clear();
        JexlContext context = new MapContext(beans);
        ExpressionCollectionParser parser = new ExpressionCollectionParser(context, items + ";", true);
        if (beanCellMapping.size() == 0) {
            return readStatus;
        }
        int rowNum = cursor.getSheet().getPhysicalNumberOfRows();

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);

        XLSTask task = new XLSTask(cursor.getSheet(), beans, beanCellMapping, var, varType, 0, rowNum - 1);
        List<Object> result = pool.invoke(task);

        beans.put(items, result);

        cursor.setCurrentRowNum(rowNum - 1);

        pool.shutdown();
        return readStatus;

    }

    @SuppressWarnings("unchecked")
    private void createNewCollectionItem(Collection itemsCollection, Map beans) {
        Object obj = null;
        try {
            obj = varType.newInstance();
        } catch (final Exception e) {
            String message = "Can't create a new collection item for " + items + ". varType = " + varType.getName();
            readStatus.addMessage(new XLSReadMessage(message));
            if (!ReaderConfig.getInstance().isSkipErrors()) {
                readStatus.setStatusOK(false);
                throw new XLSDataReadException(message, readStatus);
            }
            if (log.isWarnEnabled()) {
                log.warn(message);
            }
        }
        itemsCollection.add(obj);
        beans.put(var, obj);
    }

    private void readInnerBlocks(XLSRowCursor cursor, Map beans) {
        for (Object o : innerBlockReaders) {
            XLSBlockReader xlsBlockReader = (XLSBlockReader) o;
            readStatus.mergeReadStatus(xlsBlockReader.read(cursor, beans));
            cursor.moveForward();
        }
    }

    @SuppressWarnings("unchecked")
    public void addBlockReader(XLSBlockReader reader) {
        innerBlockReaders.add(reader);
    }

    public List getBlockReaders() {
        return innerBlockReaders;
    }

    public SectionCheck getLoopBreakCondition() {
        return loopBreakCheck;
    }

    public void setLoopBreakCondition(SectionCheck sectionCheck) {
        this.loopBreakCheck = sectionCheck;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setVarType(Class varType) {
        this.varType = varType;
    }

    public String getItems() {
        return items;
    }

    public String getVar() {
        return var;
    }

    public Class getVarType() {
        return varType;
    }

    @SuppressWarnings("unchecked")
    public void addMapping(BeanCellMapping mapping) {
        beanCellMapping.add(mapping);
    }


    static class XLSTask extends RecursiveTask<List<Object>> {

        private final Sheet sheet;
        private final Map beans;
        private final int start;
        private final int end;
        private final List cellMappings;
        private final Class varType;
        private final String var;


        public XLSTask(Sheet sheet, Map beans, List cellMappings, String var, Class varType, int start, int end) {
            this.sheet = sheet;
            this.beans = beans;
            this.start = start;
            this.end = end;
            this.cellMappings = cellMappings;
            this.varType = varType;
            this.var = var;
        }


        @Override
        @SuppressWarnings("unchecked")
        protected List<Object> compute() {
            if (end - start > 3_000) {
                XLSTask aTask = new XLSTask(sheet, new HashMap(beans), cellMappings, var, varType, start, (end - start) / 2 + start);
                XLSTask bTask = new XLSTask(sheet, new HashMap(beans), cellMappings, var, varType, (end - start) / 2 + start + 1, end);

                invokeAll(aTask, bTask);
                try {
                    List<Object> aval = aTask.get();
                    List<Object> bval = bTask.get();

                    aval.addAll(bval);
                    return aval;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }


            } else {
                List<Object> objects = new ArrayList<>();

                for (int i = start; i <= end; i++) {
                    createNewCollectionItem(objects, beans);

                    Row row = sheet.getRow(i);
                    BeanCellMapping mapping;
                    for (Object cellMapping : cellMappings) {
                        mapping = (BeanCellMapping) cellMapping;
                        try {
                            String dataString = getCellString(row.getCell(mapping.getCol()));
                            mapping.populateBean(dataString, beans);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                }
                return objects;
            }

            return null;
        }

        private String getCellString(Cell cell) {
            String dataString = null;
            if (cell != null) {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        dataString = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        dataString = readNumericCell(cell);
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        dataString = Boolean.toString(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        // attempt to read formula cell as numeric cell
                        dataString = readNumericCell(cell);
                        break;
                    default:
                        break;
                }
            }
            return dataString;
        }

        private String readNumericCell(Cell cell) {
            double value;
            String dataString;
            value = cell.getNumericCellValue();
            if (((int) value) == value) {
                dataString = Integer.toString((int) value);
            } else {
                dataString = Double.toString(cell.getNumericCellValue());
            }
            return dataString;
        }

        @SuppressWarnings("unchecked")
        private Object createNewCollectionItem(Collection itemsCollection, Map beans) {
            Object obj = null;
            try {
                obj = varType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            itemsCollection.add(obj);
            beans.put(var, obj);
            return obj;
        }
    }
}

