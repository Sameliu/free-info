package com.sijin.free.strategy;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;

import java.util.List;

/**
 * Created by sijinzhang on 16/11/15.
 */
public interface Report {

    public List<DockMA> yunsuan(List<DockMA> list,Strategy strategy);

}
