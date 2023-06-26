package com.polog.polog.domain.post.dto;

import com.polog.polog.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DeletePostRequest {

    private Long uid;


    public static Post toEntity(DeletePostRequest request){
        return Post.builder()
                .uid(request.getUid())
                .build();
    }

}
