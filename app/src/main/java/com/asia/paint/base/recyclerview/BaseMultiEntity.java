package com.asia.paint.base.recyclerview;

import com.chad.library.adapter.base.entity.SectionMultiEntity;

/**
 * @author by chenhong14 on 2019-11-27.
 */
public class BaseMultiEntity extends SectionMultiEntity<Object> {
    public BaseMultiEntity(Object o) {
        super(o);
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
