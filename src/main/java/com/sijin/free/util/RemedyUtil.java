package com.sijin.free.util;

import com.sijin.free.po.DockInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by sijinzhang on 16/12/23.
 */
public class RemedyUtil {

    public static Vector<DockInfo> vector = new Vector<>(1000);

    public static  void  add(DockInfo dockInfo){
        vector.add(dockInfo);
    }

    public static ArrayList toArrayList() {
        ArrayList<DockInfo>  list = new ArrayList<>();
        list.addAll(vector);
        return list;
    }

    public static void clear() {
        vector.clear();
    }

    public static boolean empty() {
        return vector.isEmpty();
    }
}
