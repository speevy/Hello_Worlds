package net.speevy.testing.helloWorlds;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.transaction.*;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import net.speevy.testing.helloWorlds.greetings.*;

@DataJdbcTest(includeFilters = {@Filter(type = ASSIGNABLE_TYPE, classes = {
		GreetingsRepository.class, 
		GreetingsSpringDataMapperJdbc.class
	})})
public class GreetingsSpringDataJdbcRepositoryTest extends GreetingsRepositoryTest {

	@Autowired
	GreetingsRepositorySpringDataJdbc springDataRepository;
	
	@Autowired
	GreetingsSpringDataMapperJdbc mapper;

	@Autowired
    private PlatformTransactionManager transactionManager;
	
	TransactionStatus transactionStatus;

	@Override
	protected GreetingsRepository buildRepository() {
		return new GreetingsRepositoryJdbc(springDataRepository, mapper);
	}

	@Override
	protected void startTransaction() {
		transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
	}

	@Override
	protected void rollbackTransaction() {
		transactionManager.rollback(transactionStatus);
		
	}
}
