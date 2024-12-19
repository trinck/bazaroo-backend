package org.mts.announcesservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import org.mts.announcesservice.enums.FieldType;
import org.springframework.data.elasticsearch.annotations.Field;

public abstract class FieldSubDocument {
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Keyword)
    protected String id;
    @NotEmpty
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    protected String name;
    @Field(type = org.springframework.data.elasticsearch.annotations.FieldType.Text)
    private FieldType type;
}
