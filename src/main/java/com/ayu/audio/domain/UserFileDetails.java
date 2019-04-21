package com.ayu.audio.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER_FILE_DETAILS")
@Data
public class UserFileDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    @Lob
    private byte[] fileContent;

    @OneToOne(mappedBy = "userFileDetails", cascade = CascadeType.ALL)
    @JoinColumn(name = "ID")
    private FileMetadata fileMetadata;
}
