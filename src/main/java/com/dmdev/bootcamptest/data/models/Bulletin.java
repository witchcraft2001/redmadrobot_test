package com.dmdev.bootcamptest.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BULLETINS")
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Size(max = 128)
    private String title;

    @Column
    private String description;

    @Column
    private String contacts;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Column
    private boolean isActive;

    @Column
    private boolean isPublished;

    @OneToMany(mappedBy = "bulletin", targetEntity = Message.class)
    private Collection<Message> messages;

    @OneToMany(mappedBy = "bulletin", targetEntity = Image.class)
    private Collection<Image> images;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "BULLETIN_TAGS",
            joinColumns = {@JoinColumn(name = "BULLETIN_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TAG_ID")}
    )
    private Set<Tag> tags;

    @DateTimeFormat
    private Date createdAt = Date.from(Instant.now());
}
