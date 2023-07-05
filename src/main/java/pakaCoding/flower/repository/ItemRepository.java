package pakaCoding.flower.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pakaCoding.flower.domain.entity.Item;
import pakaCoding.flower.dto.MainItemDto;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


     @Query(value = "Select new pakaCoding.flower.dto.MainItemDto(i.id, i.name, ii.savedFileImgName, i.price) " +
             "from Item i join i.itemImages ii " +
             "where i.type.id = :typeId " +
             "and ii.repImgYn = 'Y' " +
             "and i.delYn = 'N'" +
             "and i.id = ii.item.id " +
             "order by i.createDate desc")
     Page<MainItemDto> findAllByTypeIdListDtos(int typeId, Pageable pageable);


     @Query(value = "select i from Item i" +
             " join i.itemImages ii" +
             " where i.type.id = :typeId" +
             " and i.delYn = 'N'" +
             " order by i.createDate desc ")
     Page<Item> findAllByTypeId(int typeId, Pageable pageable);




     @Query ("select new pakaCoding.flower.dto.MainItemDto(i.id, i.name, ii.savedFileImgName, i.price) " +
             "from Item i join i.itemImages ii " +
             "where ii.repImgYn= 'Y'" +
             "and i.delYn = 'N' " +
             "and i.id = ii.item.id " +
             "order by  i.createDate DESC")
     Page<MainItemDto> findItemListDto(Pageable pageable);


     @Query(value = "select i from Item i " +
             " join i.itemImages ii " +
             " where ii.repImgYn = 'Y' " +
             " and i.delYn ='N' " +
             " order by i.createDate DESC")
     Page<Item> findItems(Pageable pageable);

     @Query("select i from Item i " +
             "where i.delYn = 'N'" +
             " order by i.createDate DESC")
     Page<Item> findAdminItems(Pageable pageable);




     @Query("select i from Item i" +
             " join fetch i.type t " +
             " join fetch i.itemImages ii " +
             " join fetch i.brand b" +
             " where i.id = :itemId")
     Item findAllByItemImagesAndTypeAndBrand(Long itemId);



     @Query(value = "select i from Item i " +
             " join fetch i.brand b" +
             " join i.itemImages ii" +
             " where b.id = :brandId" +
             " and ii.repImgYn='Y' " +
             " and i.delYn = 'N' ",
     countQuery = "select count(i) from Item i where i.brand.id =: brandId")
     Page<Item> findBrandItems(Long brandId, Pageable pageable);

}
