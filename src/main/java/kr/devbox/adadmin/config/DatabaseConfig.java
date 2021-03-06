package kr.devbox.adadmin.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

@Configuration
@MapperScan(value="kr.devbox.adadmin.mapper", sqlSessionFactoryRef="db1SqlSessionFactory")
public class DatabaseConfig {
    public static final String DATASOURCE = "DS1";
    private static final String SESSION_FACTORY = "db1SqlSessionFactory";


    @Bean(name = DATASOURCE, destroyMethod = "")
    @Primary
    public DataSource dataSourceQuizbuzz() throws URISyntaxException {
        String username = System.getenv("DATABASE_USER");
        String password = System.getenv("DATABASE_PASS");
        String dbUrl = System.getenv("DATABASE");
        return DataSourceBuilder
                .create()
                .username(username)
                .password(password)
                .url(dbUrl)
                .driverClassName("org.mariadb.jdbc.Driver")
//                .driverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy")
                .build();
    }

    @Bean(name = SESSION_FACTORY, destroyMethod = "")
    @Primary
    public SqlSessionFactoryBean db1SqlSessionFactory(@Qualifier(DATASOURCE) final DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        SqlSessionFactory sqlSessionFactory;
        sqlSessionFactory = sqlSessionFactoryBean.getObject();
        sqlSessionFactoryBean.setTypeAliasesPackage("kr.devbox.adadmin.entity");
        sqlSessionFactoryBean.setMapperLocations(pathResolver.getResources("classpath:mapper/ds1/*.xml"));
        sqlSessionFactory.getConfiguration().addMappers("kr.devbox.adadmin.mapper");
        // Various other SqlSessionFactory settings
        return sqlSessionFactoryBean;
    }
    @Bean(name = "sessionTemplate")
    @Primary
    public SqlSessionTemplate sessionTemplate(SqlSessionFactory db1SqlSessionFactory) throws Exception {

        return new SqlSessionTemplate(db1SqlSessionFactory);
    }
    @Bean
    public PlatformTransactionManager transactionManager() throws URISyntaxException, GeneralSecurityException, ParseException, IOException {
        return new DataSourceTransactionManager(dataSourceQuizbuzz());
    }
}
