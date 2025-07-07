package ec.edu.espe.msanalizadorsalud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.msanalizadorsalud.DTO.MedicalAlertDTO;
import ec.edu.espe.msanalizadorsalud.entity.MedicalAlert;
import ec.edu.espe.msanalizadorsalud.repository.MedicalAlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MedicalAlertService {
    @Autowired
    private MedicalAlertRepository medicalAlertRepository;

    @Autowired
    private ObjectMapper mapper;

    public List<MedicalAlert> getMedicalAlerts(){
        return medicalAlertRepository.findAll();
    }

    public MedicalAlert guardarMedicalAlert(MedicalAlertDTO medicalAlertDTO){
        MedicalAlert medicalAlert = new MedicalAlert();

        medicalAlert.setAlertId(medicalAlertDTO.getAlertId());
        medicalAlert.setDispositivoId(medicalAlertDTO.getDispositivoId());
        medicalAlert.setTipo(medicalAlertDTO.getTipo());


    }

}

