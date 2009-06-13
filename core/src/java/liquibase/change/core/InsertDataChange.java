package liquibase.change.core;

import liquibase.database.Database;
import liquibase.statement.InsertStatement;
import liquibase.statement.SqlStatement;
import liquibase.util.StringUtils;
import liquibase.change.AbstractChange;
import liquibase.change.ChangeWithColumns;
import liquibase.change.ColumnConfig;
import liquibase.change.ChangeMetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * Inserts data into an existing table.
 */
public class InsertDataChange extends AbstractChange implements ChangeWithColumns {

    private String schemaName;
    private String tableName;
    private List<ColumnConfig> columns;

    public InsertDataChange() {
        super("insert", "Insert Row", ChangeMetaData.PRIORITY_DEFAULT);
        columns = new ArrayList<ColumnConfig>();
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = StringUtils.trimToNull(schemaName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnConfig> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnConfig> columns) {
        this.columns = columns;
    }

    public void addColumn(ColumnConfig column) {
        columns.add(column);
    }

    public void removeColumn(ColumnConfig column) {
        columns.remove(column);
    }

    public SqlStatement[] generateStatements(Database database) {

        InsertStatement statement = new InsertStatement(getSchemaName() == null?database.getDefaultSchemaName():getSchemaName(), getTableName());

        for (ColumnConfig column : columns) {
            statement.addColumnValue(column.getName(), column.getValueObject());
        }

        return new SqlStatement[]{
                statement
        };
    }

    /**
     * @see liquibase.change.Change#getConfirmationMessage()
     */
    public String getConfirmationMessage() {
        return "New row inserted into " + getTableName();
    }
}