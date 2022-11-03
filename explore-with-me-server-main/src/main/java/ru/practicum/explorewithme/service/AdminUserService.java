package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.UserDto;

import java.util.List;

public interface AdminUserService {
    List<UserDto> getUsers(List<Long> users, int from, int size);

    UserDto postUser(UserDto userDto);

    void deleteUser(long userId);
}
