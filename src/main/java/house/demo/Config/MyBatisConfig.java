package house.demo.Config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
//开启数据库和Mapper中驼峰命名对应,但是数据库名与mapper里的名字必须一致，同时#{}里的属性名与URI相应的也要一致，不能相互替换。默认情况下数据库名和Javabean属性名必须一致
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {

            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
