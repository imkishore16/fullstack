package com.example.demo.dto;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicResponse<T> {
    private String message;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
