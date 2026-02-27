package com.ruoyi.web.test;

import com.clickhouse.jdbc.ClickHouseDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j; // 若依通常自带 Lombok 或 Slf4j

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.ruoyi.framework.datasource.DynamicDataSourceContextHolder.log;

/**
 * ClickHouse 工具类
 * 独立于若依默认数据源，专门用于连接 ClickHouse
 */
@Slf4j
@Component
public class ClickHouseUtil {

    @Value("${clickhouse.url}")
    private String url;

    @Value("${clickhouse.username}")
    private String username;

    @Value("${clickhouse.password}")
    private String password;

    private ClickHouseDataSource dataSource;

    /**
     * 初始化连接池
     */
    @PostConstruct
    public void init() {
        log.info(">>> 开始初始化 ClickHouse 连接...");
        log.info("URL: {}", url);
        log.info("用户名: {}", username);

        try {
            Properties properties = new Properties();
            properties.setProperty("user", username);
            properties.setProperty("password", password);

            // 关键兼容性配置
            properties.setProperty("compress", "false"); // 关闭压缩，避免某些网络环境问题
            properties.setProperty("decompress", "false");
            properties.setProperty("ssl", "false");      // 强制不使用 SSL (除非你配置了)
            properties.setProperty("connect_timeout", "30000"); // 毫秒单位更准确 (30秒)
            properties.setProperty("query_timeout", "60000");   // 60秒

            log.info(">>> 正在创建 ClickHouse 数据源...");
            this.dataSource = new ClickHouseDataSource(url, properties);

            // 测试连接
            log.info(">>> 正在测试连接...");
            try (Connection conn = dataSource.getConnection()) {
                if (conn != null && !conn.isClosed()) {
                    log.info(">>> ✅ ClickHouse 连接初始化成功!");
                } else {
                    throw new RuntimeException("连接对象为空或已关闭");
                }
            }
        } catch (Exception e) {
            log.error(">>> ❌ ClickHouse 初始化失败!", e);
            log.error("错误信息: {}", e.getMessage());
            log.error("请检查: 1.腾讯云安全组是否开放8123端口 2.账号密码是否正确 3.ClickHouse服务是否启动");
            this.dataSource = null;
        }
    }

    /**
     * 执行查询 SQL
     */
    public List<Map<String, Object>> query(String sql) {
        return query(sql, null);
    }

    /**
     * 执行查询 SQL (带参数)
     */
    public List<Map<String, Object>> query(String sql, Object[] params) {
        List<Map<String, Object>> result = new ArrayList<>();

        if (dataSource == null) {
            throw new RuntimeException("ClickHouse 数据源未初始化 (可能是连接超时或配置错误)，请查看启动日志");
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 设置参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>(columnCount);
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i); // 使用别名优先
                        if (columnName == null || columnName.isEmpty()) {
                            columnName = metaData.getColumnName(i);
                        }

                        Object value = rs.getObject(i);
                        // 特殊处理：如果值是 ClickHouse 特有类型，转为字符串
                        if (value != null && value.getClass().getName().startsWith("com.clickhouse")) {
                            value = value.toString();
                        }
                        row.put(columnName, value);
                    }
                    result.add(row);
                }
            }
        } catch (Exception e) {
            log.error("ClickHouse 查询执行失败 | SQL: {}", sql, e);
            throw new RuntimeException("查询失败: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 检查连接状态
     */
    public boolean isAlive() {
        if (dataSource == null) return false;
        try (Connection conn = dataSource.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (Exception e) {
            return false;
        }
    }
}