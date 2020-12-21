package pl.polsl.staneczek.service.mapper;

import pl.polsl.staneczek.model.Address;
import pl.polsl.staneczek.model.User;
import pl.polsl.staneczek.service.dto.UserSecondDto;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper {

    public UserSecondDto toDto(User user) {

        UserSecondDto newUser=new UserSecondDto();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setAddress(user.getAddress());

        return newUser;
    }

    public User toEntity(UserSecondDto userDto)
    {
        Address address=new Address();
        User user=new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        //address=userDto.getAddress();
        //user.setAddress(address);

        return user;
    }

}
