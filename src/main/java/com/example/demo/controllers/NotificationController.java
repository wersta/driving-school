package com.example.demo.controllers;

import com.example.demo.model.UserRole;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/checkAll")
    public ResponseEntity<HttpStatus> createNotificationsForAll(@RequestParam Integer id, @RequestParam UserRole userRole) {
        try {
            notificationService.createNotifications(id, userRole);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/checkStudent")
    public ResponseEntity<HttpStatus> createStudentNotification(@RequestParam Integer studentId, @RequestParam boolean status) {
        try {
            notificationService.createStudentNotification(studentId, status);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
