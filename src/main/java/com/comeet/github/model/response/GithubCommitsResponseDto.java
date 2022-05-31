package com.comeet.github.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GithubCommitsResponseDto {

    Long total_count;
    String incomplete_results;
    Object items;
}
