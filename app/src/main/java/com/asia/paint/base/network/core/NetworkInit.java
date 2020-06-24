package com.asia.paint.base.network.core;

import com.asia.paint.network.NetworkFactory;

/**
 * @author by chenhong14 on 2019-12-16.
 */
public final class NetworkInit {

    public static void init() {
        NetworkFactory.setOptions(new AsiaNetworkOptions());
    }
}
