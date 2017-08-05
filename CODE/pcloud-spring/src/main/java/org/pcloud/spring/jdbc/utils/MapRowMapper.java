package org.pcloud.spring.jdbc.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MapRowMapper implements RowMapper<Map<String, Object>>
{

public Map<String, Object> mapRow(ResultSet rs, int rowNum)
    throws SQLException
  {
    ResultSetMetaData rsmd = rs.getMetaData();
    int columnCount = rsmd.getColumnCount();
    Map mapOfColValues = createColumnMap(columnCount);
    for (int i = 1; i <= columnCount; i++) {
      String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
      Object obj = getColumnValue(rs, i);
      mapOfColValues.put(key.toLowerCase(), obj);
    }
    return mapOfColValues;
  }

protected Map<String, Object> createColumnMap(int columnCount)
  {
    return new LinkedCaseInsensitiveMap(columnCount);
  }

  protected String getColumnKey(String columnName)
  {
    return columnName;
  }

  protected Object getColumnValue(ResultSet rs, int index)
    throws SQLException
  {
    return JdbcUtils.getResultSetValue(rs, index);
  }
}