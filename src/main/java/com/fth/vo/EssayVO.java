package com.fth.vo;

import com.fth.pojo.Essay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EssayVO extends Essay {

    private String username;

    private String avatar;
}
