package ru.practicum.explorewithme.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.dto.UserShortDto;
import ru.practicum.explorewithme.user.model.User;

@Component
public class UserMapper {

    public UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName()
        );
    }

    public User toUserModel(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getName()
        );
    }
}
