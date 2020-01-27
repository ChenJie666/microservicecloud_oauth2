package com.hxr.springcloud.entities.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int total;  //TODO 符合条件的总用户数
    private List<T> data;  //TODO 当前页的所有数据

}
