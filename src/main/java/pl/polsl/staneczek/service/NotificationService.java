package pl.polsl.staneczek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.staneczek.model.*;
import pl.polsl.staneczek.repository.NotificationRepository;

import java.time.LocalDate;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;
    @Autowired
    private UserService userService;

    public Notification createStudentNotification(Integer studentId, Integer lessonId) {
        Student student = studentService.findById(studentId);
        Lesson lesson=studentService.findbyStudent(studentId,lessonId);
        if (student == null) return null;

        Notification notification;
        if (lesson.getLessonStatus() == LessonStatus.ACCEPTED) {
            notification = new Notification(null, "Status lekcji jazdy", "Zaakceptowano! Data lekcji: "+lesson.getDate() +" Miejsce: "+lesson.getPlace()+ " Instruktor: "+lesson.getInstructor().getUser().getFirstName()+ " "+lesson.getInstructor().getUser().getLastName(), false);
        } else {
            notification = new Notification(null, "Status lekcji jazdy", "Odrzucono! Data lekcji: "+lesson.getDate()+" Miejsce: "+lesson.getPlace()+ " Instruktor: "+lesson.getInstructor().getUser().getFirstName()+ " "+lesson.getInstructor().getUser().getLastName(), false);
        }

        notificationRepository.save(notification);
        studentService.addNotification(student, notification);
        return notification;
    }

    public void createNotifications(Integer userId, UserRole userRole) {

        User user = userService.findById(userId);

        if(user.getLastLogin() != null) {

            LocalDate obecnaData = LocalDate.now();
            if (user.getLastLogin().isBefore(obecnaData)) {
                if (userRole.equals(UserRole.INSTRUCTOR)) {
                    instructorService.check(instructorService.findByUserId(userId));
                } else if (userRole.equals(UserRole.STUDENT)) {
                    studentService.check(studentService.findByUserId(userId));
                }
            }
        }

    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}
