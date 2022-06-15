package com.kawmal.simpleorm;

import com.kawmal.simpleorm.annotation.Entity;
import com.kawmal.simpleorm.annotation.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ORMSessionFactory {
        private final List<Class<?>> entityClassList=new ArrayList<>();
        private Connection connection;

        public ORMSessionFactory addAnnotatedClass(Class<?> entityClass){
            if(entityClass.getDeclaredAnnotation(Entity.class)==null){
                throw new RuntimeException("Invalid entity class");
            }
            entityClassList.add(entityClass);
            return this;
        }

        public ORMSessionFactory setConnection(Connection connection){
            this.connection=connection;
            return this;
        }

        public void bootstrap() throws SQLException {
            String tableName = null;
            List<String> columns = null;
            for (Class<?> entity : entityClassList) {
                tableName = entity.getDeclaredAnnotation(Entity.class).value();

                if (tableName.trim().isEmpty()) {
                    tableName=entity.getSimpleName();
                }

                columns = new ArrayList<>();
                String primaryKey = null;

                Field[] fields = entity.getDeclaredFields();

                for (Field field : fields) {
                    Id primaryKeyField = field.getDeclaredAnnotation(Id.class);
                    if (primaryKeyField != null) {
                        primaryKey = field.getName();
                        continue;
                    }
                    String columnName = field.getName();
                    columns.add(columnName);
                }
            }

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CREATE TABLE IF EXISTS ").append(tableName).append("(");
            for (String column : columns) {
                stringBuilder.append(column).append(" VARCHAR(255) PRIMARY KEY)");

            }
            System.out.println(stringBuilder);
            Statement statement=connection.createStatement();
            statement.execute(stringBuilder.toString());
        }


}
