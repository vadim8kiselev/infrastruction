package org.infrastruction.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "weather")
public class Weather {

    @Id
    private Integer id;
}
