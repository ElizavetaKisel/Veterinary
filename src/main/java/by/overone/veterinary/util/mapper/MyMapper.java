package by.overone.veterinary.util.mapper;


import by.overone.veterinary.dto.AppointmentDataDTO;
import by.overone.veterinary.model.Appointment;
import org.springframework.stereotype.Component;

@Component
public class MyMapper{

    public AppointmentDataDTO appointmentToDTO(Appointment appointment){
        AppointmentDataDTO appointmentDataDTO = new AppointmentDataDTO();
        appointmentDataDTO.setId(appointment.getId());
        appointmentDataDTO.setDateTime(appointment.getDateTime());
        appointmentDataDTO.setDoctorId(appointment.getDoctor().getId());
        appointmentDataDTO.setDateTime(appointment.getDateTime());
        if (appointment.getUser() != null){
            appointmentDataDTO.setUserId(appointment.getUser().getId());
        }else {
            appointmentDataDTO.setUserId(null);
        }
        if (appointment.getPet() != null){
            appointmentDataDTO.setPetId(appointment.getPet().getId());
        }else {
            appointmentDataDTO.setPetId(null);
        }
        appointmentDataDTO.setReason(appointment.getReason());
        appointmentDataDTO.setDiagnosis(appointment.getDiagnosis());
        return appointmentDataDTO;
    }

}
