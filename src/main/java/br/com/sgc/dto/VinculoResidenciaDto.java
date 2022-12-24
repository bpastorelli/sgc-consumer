package br.com.sgc.dto;

import java.io.Serializable;

import javax.persistence.Transient;

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
public class VinculoResidenciaDto implements Serializable {
	
	private static final long serialVersionUID = -5754246207015712519L;
	
	private Long residenciaId;
	
	private Long moradorId;
	
	@Transient
	private String guide;

}
