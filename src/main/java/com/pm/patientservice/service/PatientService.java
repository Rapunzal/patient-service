package com.pm.patientservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	public List<PatientResponseDTO> getPatient(){
		List<Patient> patients =  patientRepository.findAll();
		List<PatientResponseDTO> patientResponseDto = patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
		return patientResponseDto;
	}
	
	public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
		if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
			throw new EmailAlreadyExistsException("A patient with this email already exists "+patientRequestDTO.getEmail());
		}
		Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
		return PatientMapper.toDTO(newPatient);
	}
	
	public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO patientRequestDTO) {
		Patient patient = patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("Patient not foind with ID: "+ id));
		if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)) {
			throw new EmailAlreadyExistsException("A patient with this email already exists "+patientRequestDTO.getEmail());
		}
		
		patient.setName(patientRequestDTO.getName());
		patient.setEmail(patientRequestDTO.getEmail());
		patient.setAddress(patientRequestDTO.getAddress());
		patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
		
		Patient updatedPatient = patientRepository.save(patient);
		return PatientMapper.toDTO(updatedPatient);
	}

	public void deletePatient(UUID id) {
		patientRepository.deleteById(id);
	}
	
}
