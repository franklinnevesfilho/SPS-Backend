
package com.groupfour.snb.repositories.listing;

import com.groupfour.snb.models.listing.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {
    Optional<Listing> findListingById(String id);
    Iterable<Listing> findListingsBySellerId(String userId);
}
