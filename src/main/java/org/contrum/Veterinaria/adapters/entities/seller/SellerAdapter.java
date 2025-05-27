package org.contrum.Veterinaria.adapters.entities.seller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.contrum.Veterinaria.adapters.entities.person.PersonAdapter;
import org.contrum.Veterinaria.adapters.entities.person.entity.PersonEntity;
import org.contrum.Veterinaria.adapters.entities.seller.entity.SellerEntity;
import org.contrum.Veterinaria.adapters.entities.seller.repository.SellerRepository;
import org.contrum.Veterinaria.adapters.entities.users.entity.UserEntity;
import org.contrum.Veterinaria.domain.models.Seller;
import org.contrum.Veterinaria.ports.SellerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@NoArgsConstructor
@Service
public class SellerAdapter implements SellerPort {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PersonAdapter personAdapter;

    /**
     * Saves a seller in the database.
     *
     * @param seller the seller to be saved. After calling this method, the id of the
     *               seller will be set with the id of the saved entity.
     */
    @Override
    public void saveSeller(Seller seller) {
        SellerEntity sellerEntity = this.sellerAdapter(seller);
        sellerRepository.save(sellerEntity);

        seller.setId(sellerEntity.getSellerId());
    }

    /**
     * Finds a seller by their seller ID.
     *
     * @param seller the seller whose ID will be used for the search.
     * @return the Seller object corresponding to the found seller entity, or null if no seller is found.
     */
    @Override
    public Seller findBySellerId(Seller seller) {
        SellerEntity sellerEntity = sellerRepository.findBySellerId(seller.getId());
        return sellerAdapter(sellerEntity);
    }

    @Override
    public Seller findById(long id) {
        return sellerRepository.findById(id)
                .map(this::sellerAdapter)
                .orElse(null);
    }

    /**
     * Adapts a {@link SellerEntity} to a {@link Seller}.
     *
     * @param sellerEntity the entity to be adapted.
     * @return the adapted seller, or null if the given entity is null.
     */
    public Seller sellerAdapter(SellerEntity sellerEntity) {
        if (sellerEntity == null) {
            return null;
        }

        Seller seller = new Seller();
        seller.setId(sellerEntity.getSellerId());
        seller.setId(sellerEntity.getUser().getUserId());
        seller.setId(sellerEntity.getUser().getPerson().getId());
        seller.setDocument(sellerEntity.getUser().getPerson().getDocument());
        seller.setName(sellerEntity.getUser().getPerson().getName());
        seller.setAge(sellerEntity.getUser().getPerson().getAge());
        seller.setRole(sellerEntity.getUser().getRole());
        seller.setUserName(sellerEntity.getUser().getUserName());
        seller.setPassword(sellerEntity.getUser().getPassword());
        return seller;
    }

    /**
     * Converts a {@link Seller} object to a {@link SellerEntity}.
     *
     * @param user the Seller object to be converted.
     * @return the corresponding SellerEntity.
     */
    private SellerEntity sellerAdapter(Seller user) {
        PersonEntity personEntity = personAdapter.personAdapter(user);
        UserEntity userEntity = new UserEntity();
        SellerEntity sellerEntity = new SellerEntity();
        personEntity.setId(user.getId());
        sellerEntity.setSellerId(user.getId());
        userEntity.setUserId(user.getId());
        userEntity.setPerson(personEntity);
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().name());

        sellerEntity.setUser(userEntity);
        return sellerEntity;
    }
}