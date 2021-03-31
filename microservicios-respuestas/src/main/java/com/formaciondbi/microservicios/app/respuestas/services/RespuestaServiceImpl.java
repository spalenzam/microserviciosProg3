package com.formaciondbi.microservicios.app.respuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formaciondbi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formaciondbi.microservicios.app.respuestas.models.repository.RespuestaRepository;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository repository;
	
	//@Autowired
	//private ExamenFeignClient examenClient;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {

		return repository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		/*
		//obtenemos el examen mediante microservicio
		Examen examen = examenClient.obtenerExamenPorId(examenId);
		//obtenemos las preguntas mediante el examen
		List<Pregunta> preguntas = examen.getPreguntas();
		//obtenemos preguntasId modificando el list de preguntas mediante api stream usnado map
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		//vamos al reposiotory buscando respuestas por alumno por preguntas id, consiguiendo las respuestas
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntaIds);
		 
		respuestas = respuestas.stream().map(r ->{
			preguntas.forEach(p ->{
				// si los id de las preguntas son iguales a las preguntasId de la respuesta
				if(p.getId() == r.getPreguntaId()) {
					//editamos las respuestas, agregando las preguntas 
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList()); */
		
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);

		return respuestas;
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		/*List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
		List<Long> examenIds = Collections.emptyList();
				
 		if(respuestasAlumno.size() > 0 ) {
			List<Long> preguntaIds = respuestasAlumno.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());
			examenIds = examenClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
 		}*/
		
		List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
		List<Long> examenIds = respuestasAlumno.stream().map(r -> r.getPregunta().getExamen().getId())
				.distinct()
				.collect(Collectors.toList());
		
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return repository.findByAlumnoId(alumnoId);
	}

}
