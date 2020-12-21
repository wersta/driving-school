package com.example.demo.controllers;

import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notification")
@ResponseBody
@CrossOrigin("http://localhost:8080")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


//    @PostMapping("/check")
//    public ResponseEntity<HttpStatus> createStudentNotification(@RequestParam Integer studentId, @RequestParam Integer lessonId) {
//        try {
//            notificationService.createStudentNotification(studentId, lessonId);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
//        }
//    }

}
