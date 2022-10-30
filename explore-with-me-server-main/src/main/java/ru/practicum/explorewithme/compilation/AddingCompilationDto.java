package ru.practicum.explorewithme.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddingCompilationDto {
    private List<Long> events;
    private boolean pinned;
    @NotBlank
    private String title;
}
