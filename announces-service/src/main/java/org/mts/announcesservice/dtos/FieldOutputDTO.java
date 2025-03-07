package org.mts.announcesservice.dtos;


import jakarta.persistence.Index;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.announcesservice.enums.FieldType;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;
import org.springframework.data.elasticsearch.annotations.Field;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldOutputDTO {
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Keyword)
    protected String id;
    @NotEmpty
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    protected String name;
    @Field( dynamic = Dynamic.TRUE)
    protected Object dataValue;
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private  FieldType type;

}
