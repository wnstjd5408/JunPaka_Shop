package pakaCoding.flower.service.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Flower;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FlowerRepository {

    private final EntityManager em;

    public Flower save(Flower flower){
        if (flower.getId() == null){
            em.persist(flower);
        }else{
            em.merge(flower);
        }
        return flower;
    }



    public Optional<Flower> findOne(Long id){
        Flower flower = em.find(Flower.class, id);
        return Optional.ofNullable(flower);
    }

    public List<Flower> findAll(){
        return em.createQuery("select i from Flower i", Flower.class).getResultList();
    }
}
