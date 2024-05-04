package br.com.sgc.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vinculo_veiculo")
public class VinculoVeiculo implements Serializable {
	
	private static final long serialVersionUID = 3960436649365666214L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long      id;
	
	@ManyToOne
	@JoinColumn(name="veiculo_id")
	private Veiculo   veiculo;
	
    @ManyToOne
    @JoinColumn(name = "visitante_id")
	private Visitante visitante;
    
    @Column(name = "data_vinculo", nullable = true)
	private Date      dataVinculo;
    
    @Column(name = "posicao", nullable = false)
	private Long      posicao;
    
    @Column(name = "guide", nullable = false)
	private String    guide;
	
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataVinculo = atual;
        posicao = 1L;
    }
}
