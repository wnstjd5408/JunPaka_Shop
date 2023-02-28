package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pakaCoding.flower.domain.entity.Flower;
import pakaCoding.flower.dto.MainFlowerDto;

import java.util.List;

@Repository
@Transactional
public interface FlowerRepository extends JpaRepository<Flower, Long> {

     Page<Flower> findAllByOrderByCreateDateDesc(Pageable pageable);

     @Query(value = "SELECT f FROM Flower f where f.type.id = :typeId order by f.createDate")
     @EntityGraph(attributePaths = {"fileImages"})
     Page<Flower> findAllByTypeIdQuery(@Param("typeId") int typeId, Pageable pageable);

     @EntityGraph(attributePaths = {"fileImages"})
     Page<Flower> findAll(Pageable pageable);
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
