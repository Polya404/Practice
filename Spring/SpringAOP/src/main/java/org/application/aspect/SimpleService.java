package org.application.aspect;

import lombok.RequiredArgsConstructor;
import org.application.aspect.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class SimpleService implements org.application.aspect.Service {
//    @Autowired
//    @Lazy
//    private org.application.aspect.Service simpleService;
    @Autowired
    @Lazy
    private SimpleService simpleServiceImpl;

    @Override
    public void callPrint() {
        simpleServiceImpl.print2();
    }

    private void print() {
        System.out.println("Simple service");
        print2();
    }

    @Log
    private void print2() {
        System.out.println("Simple service");
    }
}
