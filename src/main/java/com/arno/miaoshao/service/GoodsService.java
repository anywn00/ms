package com.arno.miaoshao.service;

import com.arno.miaoshao.dao.GoodsDao;
import com.arno.miaoshao.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author arno
 * @date 2019-08-15 21:37
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getById(long goodsId) {
        return goodsDao.getById(goodsId);
    }
}
