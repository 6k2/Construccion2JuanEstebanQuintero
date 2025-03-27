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

    @Override
    public void saveSeller(Seller seller) {
        SellerEntity sellerEntity = this.sellerAdapter(seller);
        sellerRepository.save(sellerEntity);

        seller.setSellerId(sellerEntity.getSellerId());
    }

    @Override
    public Seller findBySellerId(Seller seller) {
        SellerEntity adaptedSellerEntity = this.sellerAdapter(seller);
        SellerEntity sellerEntity = sellerRepository.findById(adaptedSellerEntity);

        return sellerAdapter(sellerEntity);
    }


    public Seller sellerAdapter(SellerEntity sellerEntity) {
        if (sellerEntity == null) {
            return null;
        }

        Seller seller = new Seller();
        seller.setSellerId(sellerEntity.getSellerId());
        seller.setUserId(sellerEntity.getUser().getUserId());
        seller.setPersonId(sellerEntity.getUser().getPerson().getId());
        seller.setDocument(sellerEntity.getUser().getPerson().getDocument());
        seller.setName(sellerEntity.getUser().getPerson().getName());
        seller.setAge(sellerEntity.getUser().getPerson().getAge());
        seller.setRole(sellerEntity.getUser().getRole());
        seller.setUserName(sellerEntity.getUser().getUserName());
        seller.setPassword(sellerEntity.getUser().getPassword());
        return seller;
    }

    private SellerEntity sellerAdapter(Seller user) {
        PersonEntity personEntity = personAdapter.personAdapter(user);
        UserEntity userEntity = new UserEntity();
        SellerEntity sellerEntity = new SellerEntity();
        personEntity.setId(user.getPersonId());
        sellerEntity.setSellerId(user.getSellerId());
        userEntity.setUserId(user.getUserId());
        userEntity.setPerson(personEntity);
        userEntity.setUserName(user.getUserName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole().name());

        sellerEntity.setUser(userEntity);
        return sellerEntity;
    }
}