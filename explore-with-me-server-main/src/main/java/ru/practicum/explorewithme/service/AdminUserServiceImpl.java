package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.UserDto;
import ru.practicum.explorewithme.mapper.UserMapper;
import ru.practicum.explorewithme.entity.User;
import ru.practicum.explorewithme.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers(List<Long> users, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id"));
        List<User> foundUsers;
        if (users == null) {
            foundUsers = userRepository.findAll(pageRequest).getContent();
        } else {
            foundUsers = userRepository.findAll(users, pageRequest);
        }

        return foundUsers.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto postUser(UserDto userDto) {
        User savedUser = userRepository.save(userMapper.toUserModel(userDto));
        return userMapper.toUserDto(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
