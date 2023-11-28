package com.groupfour.sps.repositories;

import com.groupfour.sps.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, String> { }
