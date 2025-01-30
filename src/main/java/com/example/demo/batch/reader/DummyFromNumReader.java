package com.example.demo.batch.reader;

import com.example.demo.domain.Dummy;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * num을 입력받아 그만큼 생성
 */
public class DummyFromNumReader implements ItemReader {

    private int num;
    private int count = 0;

    public DummyFromNumReader(int num) {
        this.num = num;
    }

    @Override
    public Dummy read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (count < num) {
            count++;
            return Dummy.create();
        } else {
            return null;
        }
    }
}
