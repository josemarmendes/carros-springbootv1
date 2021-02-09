package com.carros.api.carros;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.carros.api.infra.exception.ObjectNotFoundException;

@Service
public class CarroService {

	@Autowired
	private CarroRepository repository;

	public List<CarroDTO> getCarros() {
		List<CarroDTO> list = repository.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
		return list;
	}

	public CarroDTO getCarroById(Long id) {
		Optional<Carro> carro = repository.findById(id);
		return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
	}

	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return repository.findByTipo(tipo).stream().map(CarroDTO::new).collect(Collectors.toList());
	}

	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(),"Não foi possível inserir o registro");

		return CarroDTO.create(repository.save(carro));
	}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "Não foi possível atualizar o registro");

		Optional<Carro> optional = repository.findById(id);

		if (optional.isPresent()) {
			Carro db = optional.get();
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			repository.save(db);
			return CarroDTO.create(db);

		} else {
			return null;
			// throw new RuntimeException("Não foi possível atualizar o registro");
		}

	}

	public void delete(Long id) {
		repository.deleteById(id);
				
	}

}
