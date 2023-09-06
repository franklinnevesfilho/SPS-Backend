
package com.groupfour.snb.repositories;

import com.groupfour.snb.models.Listing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*

 CRUD stands for:
    C - create
    R - read
    U - update
    D - delete

 */
@Repository
public interface ListingRepository extends CrudRepository<Listing, UUID> {}
