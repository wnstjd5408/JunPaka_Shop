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
public interface FlowerRepository extends JpaRepository<Flower, Long> {


//     @Query(value = "SELECT f FROM Flower f left join  f.fileImages fi where f.type.id = :typeId and  fi.repimgYn = 'Y' order by f.createDate DESC ")
//     @EntityGraph(attributePaths = {"fileImages"})
//     Page<Flower> findAllByTypeIdQuery(@Param("typeId") int typeId, Pageable pageable);

     @Query(value = "Select new pakaCoding.flower.dto.MainFlowerDto(f.id, f.name, fi.savedFileImgName, f.price) " +
             "from Flower  f join f.fileImages fi " +
             "where f.type.id = :typeId " +
             "and fi.repImgYn = 'Y' " +
             "and f.id = fi.flower.id " +
             "order by f.createDate desc")
     Page<MainFlowerDto> findAllByTypeIdListDtos(@Param("typeId") int typeId, Pageable pageable);


//     @Query(value = "SELECT f FROM Flower f join FileImage fi on f.id = fi.flower.id where fi.repimgYn = 'Y' order by f.createDate DESC ")
//     @EntityGraph(attributePaths = {"fileImages"})
//     Page<Flower> findFlower(Pageable pageable);


     @Query ("select new pakaCoding.flower.dto.MainFlowerDto(f.id, f.name, fi.savedFileImgName, f.price) " +
             "from Flower f join f.fileImages fi " +
             "where fi.repImgYn= 'Y' " +
             "and f.id = fi.flower.id " +
             "order by  f.createDate DESC")
     Page<MainFlowerDto> findFlowerListDto(Pageable pageable);


     @Query("select f from Flower f" +
             " order by f.createDate DESC")
     Page<Flower> findAdminFlowers(Pageable pageable);

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
