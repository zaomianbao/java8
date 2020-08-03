package com.zaomianbao.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @Description 学生
 * @Author zaomianbao
 * @Date 2020/7/7
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /**
     * 年龄
     */
    private Integer age;
    /**
     * 姓名
     */
    private String name;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 数据创建时间
     */
    private Date createTime;

}
