package com.blog.jpa.start;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BOARD")
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ",
        initialValue = 1, allocationSize = 1
)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;

    @Column(columnDefinition = "varchar(100) default 'empty'")
    private String data;

    @Column(precision = 10, scale = 2)
    private BigDecimal cal;
}
