package com.mxcoogi.services;

import com.mxcoogi.components.ConnectionFactory;
import com.mxcoogi.dtos.*;
import com.mxcoogi.eums.DatabaseType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class DatasourceConnectionTestService {
    private final ConnectionFactory connectionFactory;

    public Object testConnection(ConnectionDto request){

        Map<String,Object> map = new HashMap<>();

        Connection sourceConnection = connectionFactory.create(
                DatabaseType.valueOf(request.getSourceType()),
                request.getSourceUrl(),
                request.getSourceUsername(),
                request.getSourcePassword());
        log.info("Connection established : {}", request.getSourceUrl());
        List<TableMetaDto> sourceTable = getTables(sourceConnection);
        map.put("sourceTable", sourceTable);

        Connection targetConnection = connectionFactory.create(
                DatabaseType.valueOf(request.getTargetType()),
                request.getTargetUrl(),
                request.getTargetUsername(),
                request.getTargetPassword());
        log.info("Connection established : {}", request.getTargetUrl());
        List<TableMetaDto> targetTable = getTables(targetConnection);
        map.put("targetTable", targetTable);
        System.out.println(map.toString());
        return map;
    }
    public List<TableMetaDto> getTables(Connection connection) {
        try {
            DatabaseMetaData meta = connection.getMetaData();

            try (ResultSet tables = meta.getTables(
                    connection.getCatalog(),
                    null,
                    "%",
                    new String[]{"TABLE"}
            )) {

                List<TableMetaDto> result = new ArrayList<>();

                while (tables.next()) {
                    String schema = tables.getString("TABLE_SCHEM");
                    String table = tables.getString("TABLE_NAME");

                    result.add(loadTable(meta, connection, schema, table));
                }

                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private TableMetaDto loadTable(
            DatabaseMetaData meta,
            Connection connection,
            String schema,
            String table
    ) throws SQLException {

        TableMetaDto tableMeta = new TableMetaDto();
        tableMeta.setSchema(schema);
        tableMeta.setTableName(table);

        tableMeta.setColumns(loadColumns(meta, connection, schema, table));
        tableMeta.setPrimaryKeys(loadPrimaryKeys(meta, connection, schema, table));
        tableMeta.setForeignKeys(loadForeignKeys(meta, connection, schema, table));
        tableMeta.setIndexes(loadIndexes(meta, connection, schema, table));

        return tableMeta;
    }

    private List<ColumnMetaDto> loadColumns(
            DatabaseMetaData meta,
            Connection conn,
            String schema,
            String table
    ) throws SQLException {

        List<ColumnMetaDto> columns = new ArrayList<>();

        try (ResultSet rs = meta.getColumns(
                conn.getCatalog(),
                schema,
                table,
                "%"
        )) {
            while (rs.next()) {
                ColumnMetaDto col = new ColumnMetaDto();

                col.setName(rs.getString("COLUMN_NAME"));
                col.setType(rs.getString("TYPE_NAME"));
                col.setSize(rs.getInt("COLUMN_SIZE"));
                col.setNullable("YES".equals(rs.getString("IS_NULLABLE")));
                col.setDefaultValue(rs.getString("COLUMN_DEF"));
                col.setPosition(rs.getInt("ORDINAL_POSITION"));

                columns.add(col);
            }
        }
        return columns;
    }
    private List<PrimaryKeyDto> loadPrimaryKeys(
            DatabaseMetaData meta,
            Connection conn,
            String schema,
            String table
    ) throws SQLException {

        List<PrimaryKeyDto> pks = new ArrayList<>();

        try (ResultSet rs = meta.getPrimaryKeys(
                conn.getCatalog(),
                schema,
                table
        )) {
            while (rs.next()) {
                PrimaryKeyDto pk = new PrimaryKeyDto();
                pk.setColumnName(rs.getString("COLUMN_NAME"));
                pk.setKeySeq(rs.getShort("KEY_SEQ"));
                pk.setPkName(rs.getString("PK_NAME"));

                pks.add(pk);
            }
        }
        return pks;
    }
    private List<ForeignKeyDto> loadForeignKeys(
            DatabaseMetaData meta,
            Connection conn,
            String schema,
            String table
    ) throws SQLException {

        List<ForeignKeyDto> fks = new ArrayList<>();

        try (ResultSet rs = meta.getImportedKeys(
                conn.getCatalog(),
                schema,
                table
        )) {
            while (rs.next()) {
                ForeignKeyDto fk = new ForeignKeyDto();

                fk.setFkColumn(rs.getString("FKCOLUMN_NAME"));
                fk.setPkTable(rs.getString("PKTABLE_NAME"));
                fk.setPkColumn(rs.getString("PKCOLUMN_NAME"));
                fk.setFkName(rs.getString("FK_NAME"));
                fk.setKeySeq(rs.getShort("KEY_SEQ"));

                fks.add(fk);
            }
        }
        return fks;
    }
    private List<IndexMetaDto> loadIndexes(
            DatabaseMetaData meta,
            Connection conn,
            String schema,
            String table
    ) throws SQLException {

        List<IndexMetaDto> indexes = new ArrayList<>();

        try (ResultSet rs = meta.getIndexInfo(
                conn.getCatalog(),
                schema,
                table,
                false,
                false
        )) {
            while (rs.next()) {
                if (rs.getString("INDEX_NAME") == null) continue;

                IndexMetaDto idx = new IndexMetaDto();
                idx.setIndexName(rs.getString("INDEX_NAME"));
                idx.setColumnName(rs.getString("COLUMN_NAME"));
                idx.setUnique(!rs.getBoolean("NON_UNIQUE"));
                idx.setOrdinalPosition(rs.getShort("ORDINAL_POSITION"));

                indexes.add(idx);
            }
        }
        return indexes;
    }

}
