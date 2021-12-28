package com.tdidok.literature.commentservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long sender;
    private Long book;
    private Long receiver;
    private String type;
    private String text;
}
