package pakaCoding.flower.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface FlowerRepository extends JpaRepository<Flower, Long> {

//    private final EntityManager em;
//
//    public Flower save(Flower flower){
//        if (flower.getId() == null){
//            em.persist(flower);
//        }else{
//            em.merge(flower);
//        }
//        return flower;
//    }
//
//
//
//    public Optional<Flower> findOne(Long id){
//        Flower flower = em.find(Flower.class, id);
//        return Optional.ofNullable(flower);
//    }
//
//    public List<Flower> findAll(){
//        return em.createQuery("select i from Flower i", Flower.class).getResultList();
//    }
}
