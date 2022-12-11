package ru.practicum.explorewithme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewsStats {
    private String app;
    private String uri;
    private long hits;
}
