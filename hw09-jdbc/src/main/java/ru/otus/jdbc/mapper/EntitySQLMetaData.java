package ru.otus.jdbc.mapper;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLMetaData {
    String getSelectAllSql(String tableName);

    String getSelectByIdSql();

    String getInsertSql();

    String getUpdateSql();
}
