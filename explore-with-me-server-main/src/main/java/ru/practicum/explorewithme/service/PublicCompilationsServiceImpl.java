package ru.practicum.explorewithme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.CompilationDto;
import ru.practicum.explorewithme.entity.Compilation;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.repository.CompilationsRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCompilationsServiceImpl implements PublicCompilationsService {
    private final CompilationsRepository publicCompilationsRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by(Sort.Direction.ASC, "id"));
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = publicCompilationsRepository.findAll(pageRequest).getContent();
        } else {
            compilations = publicCompilationsRepository.findAllByPinned(pinned, pageRequest);
        }
        return compilations.stream()
                .map(compilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation foundCompilation = publicCompilationsRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find Compilation id " + compId));
        return compilationMapper.toCompilationDto(foundCompilation);
    }
}
