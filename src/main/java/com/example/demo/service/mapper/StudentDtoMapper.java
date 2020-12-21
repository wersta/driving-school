package com.example.demo.service.mapper;

import com.example.demo.model.Address;
import com.example.demo.model.Student;
import com.example.demo.service.dto.UserSecondDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentDtoMapper {

    public UserSecondDto toDto(Student student)
    {
        UserSecondDto user=new UserSecondDto();
        Address address=new Address();

        user.setFirstName( student.getUser().getFirstName());
        user.setLastName(student.getUser().getLastName());
        user.setPhoneNumber(student.getUser().getPhoneNumber());
        user.setEmail(student.getUser().getEmail());
        user.setId(student.getId());
        address.setCity(student.getUser().getAddress().getCity());
        address.setStreet(student.getUser().getAddress().getStreet());
        address.setPostalCode(student.getUser().getAddress().getPostalCode());
        address.setHomeNumber(student.getUser().getAddress().getHomeNumber());
        address.setId(student.getUser().getId());
        user.setAddress(address);

        return user;
    }

    public List<UserSecondDto> toDtoStudentList(List<Student>studentList )
    {
        List<UserSecondDto> studentListDto = new ArrayList<>();
        studentList.forEach(student -> studentListDto.add(this.toDto(student)));
        return studentListDto;
    }

}
