package com.meoying.ai.ielts.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "writings",
        indexes = {@Index(columnList = "uid")})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WritingEntity implements Serializable {
    /**
     * id
     */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long uid;

    @Column(columnDefinition = "TEXT")
    private String analysisResult;
    @Column(columnDefinition = "TEXT")
    private String scoreResult;
    @Column(columnDefinition = "TEXT")
    private String referenceResult;
    @Column(columnDefinition = "TEXT")
    private String question;
    @Column(columnDefinition = "TEXT")
    private String writing;

    private int status;


    private Long utime;
    private Long ctime;

    public enum Status {
        UNKNOWN, INIT, SUCCESS, FAILED;
    }
}
