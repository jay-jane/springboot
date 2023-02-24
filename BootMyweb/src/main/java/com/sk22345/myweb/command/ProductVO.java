package com.sk22345.myweb.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVO {
   
   private int prod_id;
   private LocalDateTime prod_regdate;
   private String prod_enddate;
   private String prod_category;
   private String prod_writer;
   private String prod_name;
   private int prod_price;
   private int prod_count;
   private int prod_discount;
   private String prod_purchase_yn;
   private String prod_content;
   private String prod_comment;
   private String category_nav;
}