package com.example.sendmail.configurations;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.example.sendmail.*")
@EnableJpaRepositories(basePackages = "com.example.sendmail.repositories")
@EntityScan("com.example.sendmail.entities")
public class EclipseLinkConfig extends JpaBaseConfiguration {
	protected EclipseLinkConfig(DataSource ds, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtm) {
		super(ds, properties, jtm);
	}

	@Override
	protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
		return new EclipseLinkJpaVendorAdapter();
	}

	@Override
	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> map = new HashMap<>();
		map.put(PersistenceUnitProperties.WEAVING, "false");
		map.put(PersistenceUnitProperties.DDL_GENERATION, "create-or-extend-tables");

		map.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
		map.put(PersistenceUnitProperties.DDL_GENERATION_MODE, PersistenceUnitProperties.DDL_BOTH_GENERATION);
		map.put(PersistenceUnitProperties.CREATE_JDBC_DDL_FILE, "create.sql");
		return map;
	}
}