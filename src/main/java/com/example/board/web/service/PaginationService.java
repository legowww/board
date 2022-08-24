package com.example.board.web.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_SIZE = 5;





    public List<Integer> getPaginationBar(int currPageNumber, int totPageNumber) {
        int startNumber = Math.max(currPageNumber - (BAR_SIZE / 2), 0);
        int endNumber = Math.min(startNumber + BAR_SIZE, totPageNumber);
        List<Integer> bar = IntStream.range(startNumber, endNumber).boxed().collect(Collectors.toList());

        return bar;
    }

}
