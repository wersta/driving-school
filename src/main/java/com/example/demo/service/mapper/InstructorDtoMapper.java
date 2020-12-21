package com.example.demo.service.mapper;

import com.example.demo.model.Address;
import com.example.demo.model.Instructor;
import com.example.demo.service.dto.UserSecondDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorDtoMapper {

    public UserSecondDto toDto(Instructor instructor)
    {
        UserSecondDto user=new UserSecondDto();
        Address address=new Address();

        user.setFirstName( instructor.getUser().getFirstName());
        user.setLastName(instructor.getUser().getLastName());
        user.setId(instructor.getId());
        user.setPhoneNumber(instructor.getUser().getPhoneNumber());
        user.setEmail(instructor.getUser().getEmail());
        address.setCity(instructor.getUser().getAddress().getCity());
        address.setStreet(instructor.getUser().getAddress().getStreet());
        address.setPostalCode(instructor.getUser().getAddress().getPostalCode());
        address.setHomeNumber(instructor.getUser().getAddress().getHomeNumber());
        address.setId(instructor.getUser().getId());
        user.setAddress(address);

        return user;
    }

    public List<UserSecondDto> toDtoInstructorList(List<Instructor>instructorList )
    {
        List<UserSecondDto> instructorListDto = new ArrayList<>();
        instructorList.forEach(instructor -> instructorListDto.add(this.toDto(instructor)));
        return instructorListDto;
    }

}
