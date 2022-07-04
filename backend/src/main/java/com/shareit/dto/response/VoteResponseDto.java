package com.shareit.dto.response;

import com.shareit.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteResponseDto {

    private VoteType voteType;
    private Integer voteCount;

    public static VoteResponseDto buildVoteResponseDto(Integer voteCount, VoteType voteType){
        return VoteResponseDto.builder()
                .voteCount(voteCount)
                .voteType(voteType)
                .build();
    }
}
