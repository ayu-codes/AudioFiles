package com.ayu.audio.repository;

import com.ayu.audio.domain.UserFileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioFileUploadRepository extends JpaRepository<UserFileDetails, Long> {
    UserFileDetails findByFileName(String fileName);

    Long deleteByFileName(String fileName);
}
