package br.com.sgc.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoCadastroDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonUnwrapped
	private MoradorDto morador;
	
	private String guide;

}
