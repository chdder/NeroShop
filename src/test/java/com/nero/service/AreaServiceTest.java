package com.nero.service;

import static org.junit.Assert.assertEquals;

import com.nero.BaseTest;
import com.nero.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/4/30 0030
 * Time: 11:11
 */
public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        assertEquals("广州", areaList.get(0).getAreaName());
    }
}
