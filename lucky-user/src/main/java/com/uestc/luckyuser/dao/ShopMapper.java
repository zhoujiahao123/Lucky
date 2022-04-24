package com.uestc.luckyuser.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uestc.luckyuser.bo.ShopBo;
import com.uestc.luckyuser.model.Shop;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author jacob
 * @date 2022/4/24 14:45
 */
public interface ShopMapper extends BaseMapper<Shop> {

    IPage<ShopBo> selectAll(Page<ShopBo> page);

    @Select("select *,ceil(1 + 1000*(2 * 6378.137* ASIN(SQRT(POW(SIN(PI() * (#{latitude} - latitude) / 360), 2) + COS(PI() * #{latitude} / 180)" +
            "    * COS(latitude* PI() / 180) * POW(SIN(PI() * (#{longitude} - longitude) / 360), 2))))) AS distance" +
            "    from shop order by (0.95*1/log10(distance)+ 0.05*remark_score/5)  DESC")
    List<ShopBo> recommend(@Param("longitude") BigDecimal longitude, @Param("latitude")BigDecimal latitude);

    @Select("<script>"
            +
            "select tags,count(1) as num from shop where name like CONCAT('%',#{keyword},'%')" +
            "    <if test='categoryId != null'>" +
            "      and category_id = #{categoryId}" +
            "    </if>" +
            "    <if test='tags != null'>" +
            "      and tags = #{tags}" +
            "    </if>" +
            "    group by tags"+ "</script>")
    List<Map<String,Object>> searchGroupByTags(
                        @Param("keyword")String keyword,
                        @Param("categoryId")Integer categoryId,
                        @Param("tags")String tags);

    @Select("<script>"+"    select" +
            "    id, create_time, update_time, name, remark_score, price_per_man, latitude, longitude," +
            "    category_id, tags, start_time, end_time, address, seller_id, icon_url" +
            "    ,ceil(1 + 1000*(2 * 6378.137* ASIN(SQRT(POW(SIN(PI() * (#{latitude} - latitude) / 360), 2) + COS(PI() * #{latitude} / 180)" +
            "    * COS(latitude* PI() / 180) * POW(SIN(PI() * (#{longitude} - longitude) / 360), 2))))) AS distance" +
            "    from shop where name like CONCAT('%',#{keyword},'%')" +
            "     <if test='categoryId != null'>" +
            "      and category_id = #{categoryId}" +
            "    </if>" +
            "    <if test='tags != null'>" +
            "      and tags = #{tags}" +
            "    </if>" +
            "    <if test='orderby == null'>" +
            "      order by (0.95*1/log10(distance)+ 0.05*remark_score/5)  DESC" +
            "    </if>" +
            "    <if test='orderby == 1'>" +
            "      order by price_per_man ASC,id ASC" +
            "    </if>"+ "</script>")
    List<ShopBo> search(@Param("longitude")BigDecimal longitude,
                        @Param("latitude")BigDecimal latitude,
                        @Param("keyword")String keyword,
                        @Param("orderby")Integer orderby,
                        @Param("categoryId")Integer categoryId,
                        @Param("tags")String tags);

}
