package ru.practicum.explorewithme.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Email
    @NotBlank
    private String email;
    private long id;
    @NotBlank
    private String name;
}
