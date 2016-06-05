package demo.link;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Link {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String token;

    @Column
    private String shortUrl;

    @Column
    private String fullUrl;

}
