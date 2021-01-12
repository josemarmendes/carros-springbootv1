package com.carros;

import com.carros.api.exception.ObjectNotFoundException;
import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosApplicationTests {

	@Autowired
	private CarroService carroService;

	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Porshe");
		carro.setTipo("esportivos");

		CarroDTO c = carroService.insert(carro);

		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		// Buscar o objeto
		c = carroService.getCarroById(id) ;
		assertNotNull(c);

		assertEquals("Porshe", c.getNome());
		assertEquals("esportivos", c.getTipo());

		// Deletar o objeto
		carroService.delete(id);

		// Verificar se deletou
		try {
			assertNull(carroService.getCarroById(id));
			fail("O carro não foi excluído");
		} catch (ObjectNotFoundException e) {
			// OK
		}
	}

	@Test
	public void testLista() {
		List<CarroDTO> carros = carroService.getCarros();
		assertEquals(30, carros.size());
	}
	
	@Test
	public void testPorTipo() {
		assertEquals(10, carroService.getCarrosByTipo("esportivos").size());
		assertEquals(10, carroService.getCarrosByTipo("classicos").size());
		assertEquals(10, carroService.getCarrosByTipo("luxo").size());
		
		assertEquals(0, carroService.getCarrosByTipo("x").size());
	}
	
	@Test
	public void testGet() {
		CarroDTO c = carroService.getCarroById(11L);
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());
	}
}
