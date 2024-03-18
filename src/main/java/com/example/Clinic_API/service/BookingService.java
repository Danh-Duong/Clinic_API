package com.example.Clinic_API.service;

import com.example.Clinic_API.entities.Appointment;
import com.example.Clinic_API.entities.Booking;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.payload.BookingRequest;
import com.example.Clinic_API.repository.AppointmentRepository;
import com.example.Clinic_API.repository.BookingRepository;
import com.example.Clinic_API.repository.RoleRepository;
import com.example.Clinic_API.repository.UserRepository;
import com.example.Clinic_API.security.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BookingService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    private final CurrentUser currentUser=new CurrentUser();

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EmailService emailService;

    public void createBookking(BookingRequest request){
        try {
            Booking booking = modelMapper.map(request, Booking.class);

            User doctor = userRepository.findById(request.getDoctorId()).orElseThrow(() -> new RuntimeException("This user doesn't exsit"));
            if (doctor.getRoles().contains(roleRepository.findByCode("DOCTOR")))
                throw new RuntimeException("This user isn't doctor!");
            // kiểm tra ngày đặt lịch
            Appointment appointment = appointmentRepository.findById(request.getAppointmentId()).orElseThrow(() -> new RuntimeException("This time is invalid"));
            booking.setDateBooking(new Date());
            booking.setDoctor(doctor);
            booking.setStatus("Responsing");
            currentUser.getInfoUser();
            booking.setUser(currentUser.getUser());
            booking.setAppointment(appointment);

            bookingRepository.save(booking);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public void confirmBooking(Long bookingId){
        // kiểm tra đúng bác sĩ được đặt khám hay không
        currentUser.getInfoUser();
        User doctor = currentUser.getUser();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("This booking doesn't exsit"));
        if (doctor!=booking.getDoctor())
            throw new RuntimeException("This action is banned");
        booking.setStatus("DONE");
        bookingRepository.save(booking);
        ExecutorService executor= Executors.newFixedThreadPool(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String time = booking.getAppointment().getFromTime() + " đến " + booking.getAppointment().getToTime();
//                String destination = doctor.getClinics().get(0).getAddress();
                String destination="";
                emailService.sendconfirmBookingSucess(booking.getEmail(), time, destination);
            }
        });
    }

    public List<Booking> getAppointmentsByStatus(int limit, int page, String status){
        try {
            currentUser.getInfoUser();
            Pageable pg = PageRequest.of(page - 1, limit, Sort.by("dateBooking").descending());
            List<Booking> bookings = bookingRepository.findByStatusAndDoctor(status, currentUser.getUser(), pg);
            return bookings;
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
