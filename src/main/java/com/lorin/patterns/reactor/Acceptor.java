package com.lorin.patterns.reactor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by lorin on 2018/5/22.
 * http://www.blogjava.net/DLevin/archive/2015/09/02/427045.html
 * https://www.cnblogs.com/ivaneye/p/5731432.html
 */
public class Acceptor implements Runnable {

    private Reactor reactor;
    public Acceptor(Reactor reactor){
        this.reactor=reactor;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel=reactor.serverSocketChannel.accept();
            if(socketChannel!=null)//调用Handler来处理channel
                new SocketReadHandler(reactor.selector, socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
