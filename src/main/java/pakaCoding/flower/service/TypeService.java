package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.repository.TypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    public List<Type> allType(){
        return typeRepository.findAll();
    }
}
