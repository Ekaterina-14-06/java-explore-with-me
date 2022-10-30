package ru.practicum.explorewithme.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String status;
    private String message;
    private String reason;
    private LocalDateTime timestamp;
}
