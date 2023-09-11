
package com.groupfour.snb.repositories.listing;

import com.groupfour.snb.models.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {
    Listing findListingById(String id);
    Set<Listing> findListingsByUserId(String userId);
}
