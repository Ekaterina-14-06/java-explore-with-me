package ru.practicum.explorewithme.user.service;

import ru.practicum.explorewithme.user.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    
    UserDto postUser(UserDto userDto);

    void deleteUser(long userId);

    List<UserDto> getUsers(List<Long> users, int from, int size);
}
