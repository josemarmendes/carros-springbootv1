package com.carros.api.carros;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class CarroDTO {
	private Long id;
	private String nome;
	private String tipo;

	public CarroDTO(Carro carro) {
		this.id = carro.getId();
		this.nome = carro.getNome();
		this.tipo = carro.getTipo();
	}
	
	public CarroDTO() {
		
	}

	public static CarroDTO create(Carro carro) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(carro, CarroDTO.class);
	}

}
