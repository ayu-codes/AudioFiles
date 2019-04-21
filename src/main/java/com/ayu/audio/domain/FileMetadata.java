package com.ayu.audio.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "FILE_METADATA")
@Data
public class FileMetadata {
    @Id
    Long id;

    @Column(name = "UPLOAD_TIMESTAMP")
    Timestamp timeStamp;

    @Column(name = "SPEAKER_NAME", nullable = false)
    private String speakerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @MapsId
    private UserFileDetails userFileDetails;
}
