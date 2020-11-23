package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    public Notification createStudentNotification(Integer studentId, boolean status) {
        Student student = studentService.findById(studentId);
        if (student == null) return null;

        Notification notification;
        if (status == true) {
            notification = new Notification(null, "status", "Zaakceptowano", false);
        } else {
            notification = new Notification(null, "status", "Odrzucono", false);
        }

        notificationRepository.save(notification);
        studentService.addNotification(student, notification);
        return notification;
    }
    public Notification createNot(Integer studentId, Integer lessonId) {
        Student student = studentService.findById(studentId);
        Lesson lesson=studentService.findbyStudent(studentId,lessonId);
        if (student == null) return null;

        Notification notification;
        if (lesson.getLessonStatus()== LessonStatus.ACCEPTED) {
            notification = new Notification(null, "Status lekcji jazdy", "Zaakceptowano! Data lekcji: "+lesson.getDate(), false);
        } else {
            notification = new Notification(null, "Status lekcji jazdy", "Odrzucono! Data lekcji: "+lesson.getDate(), false);
        }

        notificationRepository.save(notification);
        studentService.addNotification(student, notification);
        return notification;
    }

    public void createNotifications(Integer id, UserRole userRole) {
        if (userRole.equals(UserRole.INSTRUCTOR)) {
            instructorService.check(id);
        } else if (userRole.equals(UserRole.STUDENT)) {
            studentService.check(id);
        }
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}
