package com.formaciondbi.microservicios.app.cursos.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="cursos_alumnos")
public class CursoAlumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="alumno_id", unique = true)
	private Long alumnoId;
	
	@JsonIgnoreProperties(value= {"cursoAlumnos"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="curso_id")
	private Curso curso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public boolean equals(Object obj) {
		//esta instancia es == a object
		if(this == obj) {
			return true;
		}
		
		//evalua si el objeto es del mismo tipo, es decir Alumno
		if(!(obj instanceof CursoAlumno)) {
			return false;
		}
		
		//Casteo del tipo object a Alumno 
		CursoAlumno a = (CursoAlumno) obj;
		
		//evaluo primero que el ID sea distinto a nulo, y lo comparamos con el ID de "a"
		//si todo esto se cumple devuelve True, el mismo obj con el mismo ID
		//pero si alguna de las dos condiciones falla va a seguir buscando en la lista de los ID para ver que coincida y encontrarlo
		return this.alumnoId != null && this.alumnoId.equals(a.getAlumnoId());
	} 
	
}
