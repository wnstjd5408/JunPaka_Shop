package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.repository.TypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;

    public List<Type> allType(){
        log.info("typeRepository 의 개수 = {}", typeRepository.findAll().stream().count());
        return typeRepository.findAll();
    }
}
