package com.formaciondbi.microservicios.commons.examenes.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="examenes")
public class Examen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min = 4, max=30)
	private String nombre;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at")
	private Date createAt;

	@JsonIgnoreProperties(value = {"examen"}, allowSetters = true) 
	@OneToMany(mappedBy = "examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Pregunta> preguntas;	
	
	@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Asignatura asignaturaPadre;
	
	@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	private Asignatura asignaturaHija;
	
	@Transient
	private boolean respondido;
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	
	//Constructor
	public Examen() {
		this.preguntas = new ArrayList <>();
	}


	//Getter and Setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createdAt) {
		this.createAt = createdAt;
	}


	public List<Pregunta> getPreguntas() {
		return preguntas;
	}


	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas.clear();
		
		//Agregamos esto para crear la relación inversa con examen, a cada elemento del array lo va agregando al examen
		preguntas.forEach(this::addPregunta);
	}
	
	public void addPregunta(Pregunta pregunta) {
		this.preguntas.add(pregunta);
		//esto establece la relación inversa con examen, a cada pregunta le corresponde un examen
		pregunta.setExamen(this);
	}
	
	public void removePregunta(Pregunta pregunta) {
		this.preguntas.remove(pregunta);
		//quitamos la referencia a examen, queda nula
		pregunta.setExamen(null);
	}
	
	
	public Asignatura getAsignaturaPadre() {
		return asignaturaPadre;
	}


	public void setAsignaturaPadre(Asignatura asignaturaPadre) {
		this.asignaturaPadre = asignaturaPadre;
	}


	public Asignatura getAsignaturaHija() {
		return asignaturaHija;
	}


	public void setAsignaturaHija(Asignatura asignaturaHija) {
		this.asignaturaHija = asignaturaHija;
	}


	@Override
	public boolean equals(Object obj) {
		//esta instancia es == a object
				if(this == obj) {
					return true;
				}
				
				//evalua si el objeto es del mismo tipo, es decir Alumno
				if(!(obj instanceof Examen)) {
					return false;
				}
				
				//Casteo del tipo object a Alumno 
				Examen a = (Examen) obj;
				
				//evaluo primero que el ID sea distinto a nulo, y lo comparamos con el ID de "a"
				//si todo esto se cumple devuelve True, el mismo obj con el mismo ID
				//pero si alguna de las dos condiciones falla va a seguir buscando en la lista de los ID para ver que coincida y encontrarlo
				return this.id != null && this.id.equals(a.getId());
	}


	public boolean isRespondido() {
		return respondido;
	}


	public void setRespondido(boolean respondido) {
		this.respondido = respondido;
	}
	
	
}
