package com.example.technology;

import com.example.technology.domain.spi.TechnologyPersistencePort;
import com.example.technology.domain.usecase.TechnologyUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = TechnologyMfApplication.class)
class TechnologyMfApplicationTests {

	@MockBean
	private TechnologyPersistencePort technologyPersistencePort;

	@Autowired
	private TechnologyUseCase technologyUseCase;

	@Test
	void contextLoads() {
	}

}
