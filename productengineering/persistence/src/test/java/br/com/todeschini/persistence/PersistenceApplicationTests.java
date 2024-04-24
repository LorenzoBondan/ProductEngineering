package br.com.todeschini.persistence;

import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.color.spi.CrudColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PersistenceApplicationConfigTests.class)
@EnableAutoConfiguration
class PersistenceApplicationTests {

    @Autowired
    private CrudColor crudColor;

	@Test
	void CRUDColorTests() {
		DColor newObject = crudColor.insert(DColor.builder()
				.code(123L)
				.name("Test")
				.build());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DColor updatedObject = crudColor.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setName("Updated Object");
		crudColor.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getName(), crudColor.find(updatedObject.getCode()).getName());

		/*
		crudColor.delete(updatedObject.getCode());
		Assertions.assertNull(crudColor.find(updatedObject.getCode()));
		*/
	}

}
