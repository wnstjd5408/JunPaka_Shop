package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.dto.MainItemDto;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


//     @Query(value = "SELECT f FROM Flower f left join  f.fileImages fi where f.type.id = :typeId and  fi.repimgYn = 'Y' order by f.createDate DESC ")
//     @EntityGraph(attributePaths = {"fileImages"})
//     Page<Flower> findAllByTypeIdQuery(@Param("typeId") int typeId, Pageable pageable);

     @Query(value = "Select new pakaCoding.flower.dto.MainItemDto(i.id, i.name, ii.savedFileImgName, i.price) " +
             "from Item i join i.itemImages ii " +
             "where i.type.id = :typeId " +
             "and ii.repImgYn = 'Y' " +
             "and i.delYn != 'Y'" +
             "and i.id = ii.item.id " +
             "order by i.createDate desc")
     Page<MainItemDto> findAllByTypeIdListDtos(@Param("typeId") int typeId, Pageable pageable);


     @Query(value = "select i from Item i" +
             " join i.itemImages ii" +
             " where i.type.id = :typeId" +
             " and i.delYn != 'Y'" +
             " order by i.createDate desc ",
             countQuery = "select count(i) from Item i where i.type.id = :typeId and i.delYn != 'Y'")
     Page<Item> findAllByTypeId(@Param("typeId") int typeId, Pageable pageable);


//     @Query(value = "SELECT f FROM Flower f join FileImage fi on f.id = fi.flower.id where fi.repimgYn = 'Y' order by f.createDate DESC ")
//     @EntityGraph(attributePaths = {"fileImages"})
//     Page<Flower> findFlower(Pageable pageable);


     @Query ("select new pakaCoding.flower.dto.MainItemDto(i.id, i.name, ii.savedFileImgName, i.price) " +
             "from Item i join i.itemImages ii " +
             "where ii.repImgYn= 'Y'" +
             "and i.delYn != 'Y' " +
             "and i.id = ii.item.id " +
             "order by  i.createDate DESC")
     Page<MainItemDto> findItemListDto(Pageable pageable);


     @Query("select i from Item i " +
             "where i.delYn != 'Y'" +
             " order by i.createDate DESC")
     Page<Item> findAdminItems(Pageable pageable);




     @Query("select i from Item i" +
             " join fetch i.type t " +
             " join fetch i.itemImages ii " +
             " join fetch i.brand b" +
             " where i.id = :itemId")
     Item findAllByItemImagesAndTypeAndBrand(Long itemId);



     @Query(value = "select distinct i from Item i " +
             " join fetch i.brand b" +
             " join i.itemImages ii" +
             " where b.id = :brandId" +
             " and ii.repImgYn='Y' " +
             " and i.delYn != 'Y' ",
     countQuery = "select count(i) from Item i where i.brand.id =: brandId")
     Page<Item> findBrandItems(Long brandId, Pageable pageable);

}
