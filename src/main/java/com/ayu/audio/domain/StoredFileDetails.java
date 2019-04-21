package com.ayu.audio.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class StoredFileDetails {
    private Timestamp timestamp;
    private String fileName;
    private String speakerName;
    private Long itemId;
}