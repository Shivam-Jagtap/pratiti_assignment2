package demo.CURD.Exception;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String msg){
        super(msg);
    }
}
