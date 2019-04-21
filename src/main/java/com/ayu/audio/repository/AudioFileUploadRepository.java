package com.ayu.audio.repository;

import com.ayu.audio.domain.UserFileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AudioFileUploadRepository extends JpaRepository<UserFileDetails, Long> {
    List<UserFileDetails> findAllByFileName(String fileName);

    Long deleteByFileName(String fileName);
}
