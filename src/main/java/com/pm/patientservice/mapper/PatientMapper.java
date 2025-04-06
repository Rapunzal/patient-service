package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

public class PatientMapper {
	
	public static PatientResponseDTO toDTO(Patient patient) {
		PatientResponseDTO patientDTO = new PatientResponseDTO();
		patientDTO.setId(patient.getId().toString());
		patientDTO.setName(patient.getName().toString());
		patientDTO.setAddress(patient.getAddress().toString());
		patientDTO.setEmail(patient.getEmail().toString());
		patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
		return patientDTO;
	}

}
