package pakaCoding.flower.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pakaCoding.flower.domain.entity.Type;
import pakaCoding.flower.repository.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;

    //전체 Type 조회
    public List<Type> allType(){
        return typeRepository.findAll();
    }

    //Type에 이름 가졍기
    public String findTypeName(int typeId){
        Optional<Type> type = typeRepository.findById(typeId);
        return type.get().getTypename();
    }

}
